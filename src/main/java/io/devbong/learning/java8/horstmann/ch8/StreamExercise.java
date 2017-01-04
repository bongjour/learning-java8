package io.devbong.learning.java8.horstmann.ch8;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamExercise {

	public static void main(String[] args) {

		List<String> contents = Arrays.asList("1111", "222333", "3332222", "444", "555", "666");

		long count1 = contents.stream().filter(each -> each.length() > 3).count();
		long count2 = contents.parallelStream().filter(each -> each.length() > 3).count();
		long count3 = Stream.of("1111", "222333", "3332222", "444", "555", "666").filter(each -> each.length() > 3).count();

		System.out.println("Count of bigger than 3 is " + count2);

		// 무한 스트림
		Stream<Double> generate = Stream.generate(Math::random).limit(100);
		generate.forEach(System.out::println);

		Stream<BigInteger> iterate = Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.ONE)).limit(100);
		iterate.forEach(System.out::println);

		Stream<Double> peekTest1 = Stream.iterate(1.0, p -> p * 2)
			.peek(e -> System.out.println("Fetching" + e))
			.limit(20);

		peekTest1.forEach(System.out::println);

		// reverse, distinct
		Stream<String> distinct = contents.stream()
			.sorted(Comparator.comparing(String::length).reversed())
			.distinct();

		distinct.forEach(System.out::println);

		// filter, findFirst, ifPresent
		contents.stream()
			.filter(each -> each.startsWith("1"))
			.findFirst()
			.ifPresent(System.out::println);

		// orElseThrow
		contents.stream()
			.filter(each -> each.startsWith("A"))
			.findAny();
		//.orElseThrow(IllegalStateException::new);

		System.out.println(Optional.ofNullable(null).orElse("null"));

		// compose Optional value with flatMap
		inverse(10.0)
			.flatMap(StreamExercise::squareRoot)
			.ifPresent(System.out::println);

		Optional.of(4.0)
			.flatMap(StreamExercise::inverse)
			.flatMap(StreamExercise::squareRoot)
			.ifPresent(System.out::println);


		contents.stream().toArray(String[]::new);

		HashSet<String> sets = contents.stream().collect(Collectors.toCollection(HashSet::new));
		String joiningString = contents.stream().collect(Collectors.joining(","));
		System.out.println(joiningString);

		IntSummaryStatistics statistics = contents.stream().collect(Collectors.summarizingInt(String::length));
		System.out.println(statistics.getAverage());
		System.out.println(statistics.getMax());
	}

	private static Optional<Double> inverse(Double x) {
		return x == 0 ? Optional.empty() : Optional.of(1 / x);
	}

	private static Optional<Double> squareRoot(Double x) {
		return x < 0 ? Optional.empty() : Optional.of(Math.sqrt(x));
	}
}
