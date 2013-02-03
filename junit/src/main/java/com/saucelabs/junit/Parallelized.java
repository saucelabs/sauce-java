package com.saucelabs.junit;


import org.junit.runners.Parameterized;
import org.junit.runners.model.RunnerScheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/**
 * Creates a dynamically sized thread pool when running parallel tests.
 * <p/>
 * Inspired by http://hwellmann.blogspot.com/2009/12/running-parameterized-junit-tests-in.html
 *
 * @author Ross Rowe
 */
public class Parallelized extends Parameterized {

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
                    /*if (suite != null) {
                        String threadName = suite.getClass().getName();
                        if (threadName != null)
                            Thread.currentThread().setName(threadName);
                    } */
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

    private static class ThreadPoolScheduler implements RunnerScheduler {
        private ExecutorService executor;

        public ThreadPoolScheduler() {
            String threads = System.getProperty("junit.parallel.threads", "16");
            int numThreads = Integer.parseInt(threads);
            executor = Executors.newFixedThreadPool(numThreads);
        }

        @Override
        public void finished() {
            executor.shutdown();
            try {
                executor.awaitTermination(10, TimeUnit.MINUTES);
            } catch (InterruptedException exc) {
                throw new RuntimeException(exc);
            }
        }

        @Override
        public void schedule(Runnable childStatement) {
            executor.submit(childStatement);
        }
    }

    public Parallelized(Class klass) throws Throwable {
        super(klass);
//        setScheduler(new ThreadPoolScheduler());
        setScheduler(new NonBlockingAsynchronousRunner());
    }
}
