package io.devbong.learning.java8.javainaction.ch7;

import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallelStreams {

	public static long sequentialSum(long n) {
		return Stream
			.iterate(1L, i -> i + 1)
			.limit(n)
			.reduce(0L, Long::sum);
	}

	public static long sequentialSum2(long n) {
		return LongStream
			.rangeClosed(1, n)
			.parallel()
			.reduce(0L, Long::sum);
	}

	public static long iterativeSum(long n) {
		long result = 0L;

		for (long i = 1L; i <= n; i++) {
			result += i;
		}

		return result;
	}

	public static long parallelSum(long n) {
		return Stream
			.iterate(1L, i -> i + 1)
			.limit(n)
			.parallel()
			.reduce(0L, Long::sum);
	}
}
