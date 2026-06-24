package com.ericzzz.concurrent.juc.automic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.GroupThreads;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@Measurement(iterations = 10)
@Warmup(iterations = 10)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class SynchronizedVsLockVsAtomicInteger {

    @State(Scope.Group)
    public static class IntMonitor {
        private int x;
        private final Lock lock = new ReentrantLock();

        public void lockInc() {
            lock.lock();
            try {
                x++;
            } finally {
                lock.unlock();
            }
        }

        public void sycInc() {
            synchronized (this) {
                x++;
            }
        }
    }

    @State(Scope.Group)
    public static class AtomicIntegerMonitor {
        private AtomicInteger x = new AtomicInteger(0);

        public void inc() {
            x.incrementAndGet();
        }
    }

    @GroupThreads(10)
    @Group("sync")
    @Benchmark
    public void syncInc(IntMonitor monitor) {
        monitor.sycInc();
    }

    @GroupThreads(10)
    @Group("lock")
    @Benchmark
    public void lockInc(IntMonitor monitor) {
        monitor.lockInc();
    }

    @GroupThreads(10)
    @Group("atomic")
    @Benchmark
    public void atomicInc(AtomicIntegerMonitor monitor) {
        monitor.inc();
    }
 
    public static void main(String[] args) throws RunnerException {
        Options opts = new OptionsBuilder()
                .include(SynchronizedVsLockVsAtomicInteger.class.getSimpleName())
                .forks(1)
                .timeout(TimeValue.seconds(10))
                .addProfiler(StackProfiler.class)
                .build();
        new Runner(opts).run();
    }

}
