package uk.co.odinconsultants.memory;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;


@State(Scope.Group)
@Threads(1)
public class JMH_CacheHits {

    /**
     * Run
     * sudo dmidecode -t cache
     * to show the caches in your system. On my machine, it says <pre>
 Cache Information
	Socket Designation: L1 Cache
	Configuration: Enabled, Not Socketed, Level 1
	Operational Mode: Write Back
	Location: Internal
	Installed Size: 512 kB
	Maximum Size: 512 kB
	Supported SRAM Types:
		Synchronous
	Installed SRAM Type: Synchronous
	Speed: Unknown
	Error Correction Type: Parity
	System Type: Unified
	Associativity: 8-way Set-associative

Handle 0x0028, DMI type 7, 27 bytes
Cache Information
	Socket Designation: L2 Cache
	Configuration: Enabled, Not Socketed, Level 2
	Operational Mode: Write Back
	Location: Internal
	Installed Size: 2048 kB
	Maximum Size: 2048 kB
	Supported SRAM Types:
		Synchronous
	Installed SRAM Type: Synchronous
	Speed: Unknown
	Error Correction Type: Single-bit ECC
	System Type: Unified
	Associativity: 4-way Set-associative

Handle 0x0029, DMI type 7, 27 bytes
Cache Information
	Socket Designation: L3 Cache
	Configuration: Enabled, Not Socketed, Level 3
	Operational Mode: Write Back
	Location: Internal
	Installed Size: 16384 kB
	Maximum Size: 16384 kB
	Supported SRAM Types:
		Synchronous
	Installed SRAM Type: Synchronous
	Speed: Unknown
	Error Correction Type: Multi-bit ECC
	System Type: Unified
	Associativity: 16-way Set-associative</pre>
     * These settings are for my L1, L2 and L3 (divided by 8 as the array is of 64-bit longs)
     */
    @Param({"16", "128"})
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
    public void randomAccess() {
        acc = acc ^ x[random.nextInt(n)];
    }

    long doNotOptimizeAway() {
        return acc;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JMH_CacheHits.class.getSimpleName()).warmupIterations(3).measurementIterations(3)
                .forks(1).threads(1)
                .build();

        new Runner(opt).run();
    }

}
