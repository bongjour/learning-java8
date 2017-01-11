package io.devbong.learning.java8.javainaction.ch5;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Ch5 {

	public static void main(String[] args) {
		Stream.generate(Math::random).limit(5).forEach(System.out::println);

		IntStream.generate(()->1).limit(100).forEach(System.out::println);
	}
}
