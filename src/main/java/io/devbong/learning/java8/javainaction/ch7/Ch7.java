package io.devbong.learning.java8.javainaction.ch7;

import java.util.function.Function;

public class Ch7 {

	public static void main(String[] args) {

		System.out.println("Sequential sum done in:" + measureSumPerf(ParallelStreams::sequentialSum, 10_000_000) + "msecs");
		System.out.println("Sequential2 sum done in:" + measureSumPerf(ParallelStreams::sequentialSum2, 10_000_000) + "msecs");
		System.out.println("iterative sum done in:" + measureSumPerf(ParallelStreams::iterativeSum, 10_000_000) + "msecs");
		System.out.println("parallel sum done in:" + measureSumPerf(ParallelStreams::parallelSum, 10_000_000) + "msecs");

	}

	public static long measureSumPerf(Function<Long, Long> adder, long n) {

		long fastest = Long.MAX_VALUE;

		for (int i = 0; i < 10; i++) {

			long start = System.nanoTime();
			long sum = adder.apply(n);
			long duration = (System.nanoTime() - start) / 1_000_000;
			System.out.println("Result : " + sum);
			if (duration < fastest) {
				fastest = duration;
			}
		}

		return fastest;
	}
}
