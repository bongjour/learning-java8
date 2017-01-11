package io.devbong.learning.java8.cheatset;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;

public class ReferenceJava8 {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		List<Apple> inventories = Arrays.asList(
			Apple.of("사과1", 100, "red", "korea"),
			Apple.of("사과2", 200, "blue", "japan"),
			Apple.of("사과3", 300, "yellow", "usa"),
			Apple.of("사과4", 50, "black", "taiwan"),
			Apple.of("사과5", 400, "gray", "china"),
			Apple.of("사과6", 20, "red", "uk")
		);

		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 8, 9);

		// sort with lambda
		inventories.sort((apple1, apple2) -> apple1.getWeight().compareTo(apple2.getWeight()));
		inventories.sort(Comparator.comparing(apple -> apple.getWeight()));
		inventories.sort(Comparator.comparing(Apple::getWeight));

		// sort reverse
		inventories.sort(Comparator.comparing(Apple::getWeight).reversed());

		// consumer
		inventories.forEach(System.out::println);

		// IntPredicate
		IntPredicate intPredicate = (int i) -> i % 2 == 0;
		intPredicate.test(100);

		// supplier
		Supplier<Apple> appleSupplier = () -> inventories.stream()
			.filter(Apple::isMadeByKorea)
			.findAny()
			.orElse(new Apple());

		// comparator combine
		inventories.sort(Comparator.comparing(Apple::getWeight)
			.reversed()
			.thenComparing(Apple::getName)
		);

		// predicate combine
		Predicate<Apple> koreaApple = apple -> apple.getCountry().equals("korea");
		inventories.stream().filter(koreaApple);
		inventories.stream().filter(koreaApple.negate());
		inventories.stream().filter(koreaApple.and(apple -> apple.getWeight() > 100).or(apple -> apple.getName().equals("사과1")));

		// function 조합
		// there're two default method andThen, compose
		Function<Integer, Integer> f = x -> x + 1;
		Function<Integer, Integer> g = x -> x * 2;
		Function<Integer, Integer> h = f.andThen(g);
		h.apply(1); // 4 bcuz 1 + 1 andThen 2 * 2

		Function<Integer, Integer> i = f.compose(g);
		i.apply(1); // 3 bcuz 1 * 2 andThen 3 + 1

		integrate((double x) -> x + 10, 1, 2);

		// custom constructor reference
		TriFunction<String, Integer, String, String, Apple> appleFactory = Apple::new;
		Apple newApple = appleFactory.apply("사과 100", 1000, "레드", "한국");

		/**
		 * stream
		 */

		inventories.stream()
			.collect(Collectors.groupingBy(Apple::getColor));

		// parallelStream
		numbers.parallelStream().forEach(each -> {
			System.out.println(Thread.currentThread().getName() + " : " + each);
		});

		// filter & distinct
		inventories.stream()
			.filter(Apple::isMadeByKorea)
			.distinct()
			.forEach(System.out::println);

		// limit
		inventories.stream()
			.limit(3)
			.collect(Collectors.toList())
			.forEach(System.out::println);

		System.out.println("----");
		inventories.stream()
			.filter(apple -> !apple.isMadeByKorea())
			.forEach(System.out::println);
		System.out.println("----");

		// skip
		inventories.stream()
			.filter(apple -> !apple.isMadeByKorea())
			.skip(1)
			.forEach(System.out::println);

		String[] words = { "Hello", "World" };

		// flatMap
		Arrays.stream(words)
			.map(word -> word.split(""))
			.flatMap(Arrays::stream)
			.distinct()
			.forEach(System.out::println);

		// 연습문제
		List<Integer> numbers1 = Arrays.asList(1, 2, 3);
		List<Integer> numbers2 = Arrays.asList(3, 4);

		numbers1.stream()
			.flatMap(num1 ->
				numbers2.stream()
					.map(num2 -> new int[] { num1, num2 }))
			.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));

		// anyMatch
		System.out.println(inventories.stream().anyMatch(Apple::isMadeByKorea));

		// allMatch
		System.out.println(inventories.stream().allMatch(Apple::isMadeByKorea));

		// nonMatch
		System.out.println(inventories.stream().noneMatch(Apple::isMadeByKorea));

		// findAny
		inventories.stream().filter(Apple::isMadeByKorea).findAny().ifPresent(System.out::println);

		// findFirst <-- 병렬성에서는 사용하지 말자.
		inventories.stream().filter(Apple::isMadeByKorea).findFirst().ifPresent(System.out::println);

	}

	public static double integrate(DoubleFunction<Double> f, double a, double b) {
		return (f.apply(a) + f.apply(b)) * (b - a) / 2.0;
	}

	public interface TriFunction<T, U, V, Z, R> {
		R apply(T t, U u, V v, Z z);
	}

	private static class Apple {
		private String name;
		private Integer weight;
		private String color;
		private String country;

		public Apple(String name, Integer weight, String color, String country) {
			this.name = name;
			this.weight = weight;
			this.color = color;
			this.country = country;
		}

		public Apple() {

		}

		public static Apple of(String name, Integer weight, String color, String country) {
			Apple apple = new Apple();
			apple.name = name;
			apple.weight = weight;
			apple.color = color;
			apple.country = country;
			return apple;
		}

		public String getName() {
			return this.name;
		}

		public Integer getWeight() {
			return this.weight;
		}

		public String getColor() {
			return this.color;
		}

		public String getCountry() {
			return this.getCountry();
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(this.name);
			sb.append(":");
			sb.append(this.weight);
			return sb.toString();
		}

		public boolean isMadeByKorea() {
			return this.country.equals("korea");
		}
	}
}
