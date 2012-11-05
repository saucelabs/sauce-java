package com.saucelabs.junit;


import org.junit.runners.model.RunnerScheduler;
import org.junit.runners.Parameterized;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Creates a dynamically sized thread pool when running parallel tests.
 *
 * Inspired by http://hwellmann.blogspot.com/2009/12/running-parameterized-junit-tests-in.html
 * @author Ross Rowe
 */
public class Parallelized extends Parameterized {

    private static class ThreadPoolScheduler implements RunnerScheduler {
        private ExecutorService executor;

        public ThreadPoolScheduler() {
            executor = Executors.newCachedThreadPool();
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
        setScheduler(new ThreadPoolScheduler());
    }
}
