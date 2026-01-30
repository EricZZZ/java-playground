package com.ericzzz.j8;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallelStreamsTest {

    private static long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        return new ForkJoinPool().invoke(task);
    }

    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).reduce(0L, Long::sum);
    }

    public static long iterativeSum(long n) {
        long result = 0;
        for (long i = 1L; i <= n; i++) {
            result += i;
        }
        return result;
    }

    public static long parallelSum(long n) {
        return LongStream.rangeClosed(1, n).parallel().reduce(0L, Long::sum);
    }

    public static Long measureSumPerf(Function<Long, Long> adder, long n) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            long sum = adder.apply(n);
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Result: " + sum);
            if (duration < fastest)
                fastest = duration;
        }
        return fastest;
    }

    public static void main(String[] args) {
        System.out.println(
                "Sequential sum done in:" + measureSumPerf(ParallelStreamsTest::sequentialSum, 10_000_000) + " msecs");
        System.out.println(
                "Iterative sum done in:" + measureSumPerf(ParallelStreamsTest::iterativeSum, 10_000_000) + " msecs");
        System.out.println(
                "Parallel sum done in:" + measureSumPerf(ParallelStreamsTest::parallelSum, 10_000_000) + " msecs");
        System.out.println(
                "Fork/Join sum done in:" + measureSumPerf(ParallelStreamsTest::forkJoinSum, 10_000_000) + " msecs");
    }
}
