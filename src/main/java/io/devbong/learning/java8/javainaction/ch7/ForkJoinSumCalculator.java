package io.devbong.learning.java8.javainaction.ch7;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class ForkJoinSumCalculator extends RecursiveTask<Long> {

	private final long[] numbers;
	private final int start;
	private final int end;

	private static final long THRESHOLD = 10_000;

	public static void main(String[] args) {

		System.out.println(forkJoinSum(1000));
		System.out.println(forkJoinSum(10001));
	}

	public static long forkJoinSum(long n) {
		long[] numbers = LongStream.rangeClosed(1, n).toArray();
		ForkJoinSumCalculator task = new ForkJoinSumCalculator(numbers);
		return new ForkJoinPool().invoke(task);
	}

	public ForkJoinSumCalculator(long[] numbers) {
		this(numbers, 0, numbers.length);
	}

	private ForkJoinSumCalculator(long[] numbers, int start, int end) {
		this.numbers = numbers;
		this.start = start;
		this.end = end;
	}

	@Override
	protected Long compute() {
		int length = end - start;

		if (length <= THRESHOLD) {
			return computeSequentially();
		}

		int leftEnd = start + length / 2;
		ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, leftEnd);
		System.out.println(leftEnd);

		leftTask.fork();

		int rightStart = start + length / 2;
		ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, rightStart, end);
		System.out.println(rightStart);

		Long rightResult = rightTask.compute();

		Long leftResult = leftTask.join();

		return leftResult + rightResult;

	}

	private long computeSequentially() {
		long sum = 0;
		for (int i = start; i < end; i++) {
			sum += numbers[i];
		}
		return sum;
	}
}
