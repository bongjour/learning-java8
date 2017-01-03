package io.devbong.learning.java8.horstmann.ch8;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
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
	}
}
