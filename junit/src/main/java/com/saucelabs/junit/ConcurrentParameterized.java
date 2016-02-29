package com.saucelabs.junit;

import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.Suite;
import org.junit.runners.model.*;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/**
 * Reimplementation of {@link Parallelized} to support parameterized tests and concurrent execution of test methods.
 *
 * The {@link Parallelized} class follows a similar pattern, however in that class, the underlying runner scheduler
 * only allows single tests to be executed.  The surefire/failsafe plugins facilitate test methods to be run in parallel,
 * but this doesn't seem to work with parameterized tests.
 *
 * @author Ross Rowe
 */
public class ConcurrentParameterized extends Suite {

    /**
     * Annotation for a method which provides parameters to be injected into the
     * test class constructor by <code>SauceParameterized</code>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Parameters {
        /**
         * Optional pattern to derive the test's name from the parameters. Use
         * numbers in braces to refer to the parameters or the additional data
         * as follows:
         * <pre>
         * {index} - the current parameter index
         * {0} - the first parameter value
         * {1} - the second parameter value
         * etc...
         * </pre>
         * Default value is "{index}" for compatibility with previous JUnit
         * versions.
         *
         * @return {@link java.text.MessageFormat} pattern string, except the index
         *         placeholder.
         * @see java.text.MessageFormat
         */
        String name() default "{index}";
    }

    /**
     * Annotation for fields of the test class which will be initialized by the
     * method annotated by <code>Parameters</code>
     * By using directly this annotation, the test class constructor isn't needed.
     * Index range must start at 0.
     * Default value is 0.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Parameter {
        /**
         * Method that returns the index of the parameter in the array
         * returned by the method annotated by <code>Parameters</code>.
         * Index range must start at 0.
         * Default value is 0.
         *
         * @return the index of the parameter.
         */
        int value() default 0;
    }

    private static final List<Runner> NO_RUNNERS = Collections.emptyList();

    private final ArrayList<Runner> runners = new ArrayList<Runner>();

    /**
     * Only called reflectively. Do not use programmatically.
     * @param klass Sets up class with data provider and runners for parallel runs
     * @throws Throwable Throwable propagating from {link#createRunnersForParameters}
     */
    public ConcurrentParameterized(Class<?> klass) throws Throwable {
        super(klass, NO_RUNNERS);
        Parameters parameters = getParametersMethod().getAnnotation(
                Parameters.class);
        createRunnersForParameters(allParameters(), parameters.name());
        setScheduler(new NonBlockingAsynchronousRunner());
    }

    @Override
    protected List<Runner> getChildren() {
        return runners;
    }

    @SuppressWarnings("unchecked")
    private Iterable<Object[]> allParameters() throws Throwable {
        Object parameters = getParametersMethod().invokeExplosively(null);
        if (parameters instanceof Iterable) {
            return (Iterable<Object[]>) parameters;
        } else {
            throw parametersMethodReturnedWrongType();
        }
    }

    private FrameworkMethod getParametersMethod() throws Exception {
        List<FrameworkMethod> methods = getTestClass().getAnnotatedMethods(
                Parameters.class);
        for (FrameworkMethod each : methods) {
            if (each.isStatic() && each.isPublic()) {
                return each;
            }
        }

        throw new Exception("No public static parameters method on class "
                + getTestClass().getName());
    }

    private void createRunnersForParameters(Iterable<Object[]> allParameters,
                                            String namePattern) throws Exception {
        try {
            int i = 0;
            for (Object[] parametersOfSingleTest : allParameters) {
                String name = nameFor(namePattern, i, parametersOfSingleTest);
                SauceClassRunnerForParameters runner = new SauceClassRunnerForParameters(
                        getTestClass().getJavaClass(), parametersOfSingleTest,
                        name);
                runners.add(runner);
                ++i;
            }
        } catch (ClassCastException e) {
            throw parametersMethodReturnedWrongType();
        }
    }

    private String nameFor(String namePattern, int index, Object[] parameters) {
        String finalPattern = namePattern.replaceAll("\\{index\\}",
                Integer.toString(index));
        String name = MessageFormat.format(finalPattern, parameters);
        return "[" + name + "]";
    }

    private Exception parametersMethodReturnedWrongType() throws Exception {
        String className = getTestClass().getName();
        String methodName = getParametersMethod().getName();
        String message = MessageFormat.format(
                "{0}.{1}() must return an Iterable of arrays.",
                className, methodName);
        return new Exception(message);
    }

    private List<FrameworkField> getAnnotatedFieldsByParameter() {
        return getTestClass().getAnnotatedFields(Parameter.class);
    }

    private boolean fieldsAreAnnotated() {
        return !getAnnotatedFieldsByParameter().isEmpty();
    }

    /**
     * Reimplementation of {@link org.junit.runners.Parameterized.TestClassRunnerForParameters} that
     * uses a {@link NonBlockingAsynchronousRunner} to schedule test execution.
     */
    private class SauceClassRunnerForParameters extends BlockJUnit4ClassRunner {
        private final Object[] fParameters;

        private final String fName;

        private final RunnerScheduler scheduler;

        SauceClassRunnerForParameters(Class<?> type, Object[] parameters,
                                      String name) throws InitializationError {
            super(type);
            fParameters = parameters;
            fName = name;
            scheduler = new NonBlockingAsynchronousRunner();
        }

        @Override
        public Object createTest() throws Exception {
            if (fieldsAreAnnotated()) {
                return createTestUsingFieldInjection();
            } else {
                return createTestUsingConstructorInjection();
            }
        }

        private Object createTestUsingConstructorInjection() throws Exception {
            return getTestClass().getOnlyConstructor().newInstance(fParameters);
        }

        private Object createTestUsingFieldInjection() throws Exception {
            List<FrameworkField> annotatedFieldsByParameter = getAnnotatedFieldsByParameter();
            if (annotatedFieldsByParameter.size() != fParameters.length) {
                throw new Exception("Wrong number of parameters and @Parameter fields." +
                        " @Parameter fields counted: " + annotatedFieldsByParameter.size() + ", available parameters: " + fParameters.length + ".");
            }
            Object testClassInstance = getTestClass().getJavaClass().newInstance();
            for (FrameworkField each : annotatedFieldsByParameter) {
                Field field = each.getField();
                Parameter annotation = field.getAnnotation(Parameter.class);
                int index = annotation.value();
                try {
                    field.set(testClassInstance, fParameters[index]);
                } catch (IllegalArgumentException iare) {
                    throw new Exception(getTestClass().getName() + ": Trying to set " + field.getName() +
                            " with the value " + fParameters[index] +
                            " that is not the right type (" + fParameters[index].getClass().getSimpleName() + " instead of " +
                            field.getType().getSimpleName() + ").", iare);
                }
            }
            return testClassInstance;
        }

        @Override
        protected String getName() {
            return fName;
        }

        @Override
        protected String testName(FrameworkMethod method) {
            return method.getName() + getName();
        }

        @Override
        protected void validateConstructor(List<Throwable> errors) {
            validateOnlyOneConstructor(errors);
            if (fieldsAreAnnotated()) {
                validateZeroArgConstructor(errors);
            }
        }

        @Override
        protected void validateFields(List<Throwable> errors) {
            super.validateFields(errors);
            if (fieldsAreAnnotated()) {
                List<FrameworkField> annotatedFieldsByParameter = getAnnotatedFieldsByParameter();
                int[] usedIndices = new int[annotatedFieldsByParameter.size()];
                for (FrameworkField each : annotatedFieldsByParameter) {
                    int index = each.getField().getAnnotation(Parameter.class).value();
                    if (index < 0 || index > annotatedFieldsByParameter.size() - 1) {
                        errors.add(
                                new Exception("Invalid @Parameter value: " + index + ". @Parameter fields counted: " +
                                        annotatedFieldsByParameter.size() + ". Please use an index between 0 and " +
                                        (annotatedFieldsByParameter.size() - 1) + ".")
                        );
                    } else {
                        usedIndices[index]++;
                    }
                }
                for (int index = 0; index < usedIndices.length; index++) {
                    int numberOfUse = usedIndices[index];
                    if (numberOfUse == 0) {
                        errors.add(new Exception("@Parameter(" + index + ") is never used."));
                    } else if (numberOfUse > 1) {
                        errors.add(new Exception("@Parameter(" + index + ") is used more than once (" + numberOfUse + ")."));
                    }
                }
            }
        }

        @Override
        protected Statement classBlock(final RunNotifier notifier) {
            return new Statement() {
                @Override
                public void evaluate() {
                    runChildren(notifier);
                }
            };
        }

        @Override
        protected Annotation[] getRunnerAnnotations() {
            return new Annotation[0];
        }

        private void runChildren(final RunNotifier notifier) {
            for (final FrameworkMethod each : getChildren()) {
                scheduler.schedule(new Runnable() {
                    public void run() {
                        SauceClassRunnerForParameters.this.runChild(each, notifier);
                    }
                });
            }
            scheduler.finished();
        }
    }

    /**
     * {@link RunnerScheduler} which allows tests to run concurrently.  A fixed thread pool is used to invoke the tests,
     * which are added to a list of {@link Future}s.
     */
    private static class NonBlockingAsynchronousRunner implements RunnerScheduler {
        private final List<Future<Object>> futures = Collections.synchronizedList(new ArrayList<Future<Object>>());
        private final ExecutorService fService;

        public NonBlockingAsynchronousRunner() {
            String threads = System.getProperty("junit.parallel.threads", "16");
            int numThreads = Integer.parseInt(threads);
            fService = Executors.newFixedThreadPool(numThreads);
        }

        public void schedule(final Runnable childStatement) {
            final Callable<Object> objectCallable = new Callable<Object>() {
                public Object call() throws Exception {
                    childStatement.run();
                    return null;
                }
            };
            futures.add(fService.submit(objectCallable));
        }

        public void finished() {
            waitForCompletion();
        }

        public void waitForCompletion() {
            for (Future<Object> each : futures)
                try {
                    each.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
        }

    }
}
