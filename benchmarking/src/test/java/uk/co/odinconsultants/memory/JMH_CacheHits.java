package uk.co.odinconsultants.memory;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;


@State(Scope.Group)
public class JMH_CacheHits {

    /**
     * Run
     * sudo dmidecode -t cache
     * to show the caches in your system.
     * These settings are for my L1, L2 and L3 (divided by 8 as the array is of 64-bit longs)
     */
    @Param({"64000", "256000", "2048000",})
    volatile public int n;
    private static Random random = new Random();
    public static long[] createRandomVector(int n) {
        System.out.println("Creating vector of size "+ n);
        var vec = new long[n];
        for (int i = 0 ; i < n ; i++) {
            vec[i] = random.nextLong();
        }
        return vec;
    }

    long[] x = createRandomVector(n);

    @Setup(Level.Trial)
    public void doSetUp() {
        x = createRandomVector(n);
    }


    long acc = 0L;

    @Benchmark
    @Group
    public void usingPlainOldJava() {
        acc = acc ^ x[java.lang.Math.abs(random.nextInt()) % n];
    }

    long doNotOptimizeAway() {
        return acc;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JMH_CacheHits.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
