package io.devbong.learning.java8.cheatset;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

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
		inventories.sort(comparing(apple -> apple.getWeight()));
		inventories.sort(comparing(Apple::getWeight));

		// sort reverse
		inventories.sort(comparing(Apple::getWeight).reversed());

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
		inventories.sort(comparing(Apple::getWeight)
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
			.collect(groupingBy(Apple::getColor));

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
			.collect(toList())
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

		// reduce 초기값, BinaryOperator
		Integer reduce1 = numbers.stream().reduce(0, (a, b) -> a + b);

		Optional<Integer> reduce2 = numbers.stream().reduce((a, b) -> a + b);
		System.out.println(reduce2.get());

		// 최대값, 최솟값
		Optional<Integer> reduce3 = numbers.stream().reduce(Integer::max);
		System.out.println(reduce3.get());
		// or
		Optional<Integer> reduce4 = numbers.stream().reduce((a, b) -> a < b ? b : a);
		System.out.println(reduce4.get());

		// min with min & comparing
		Optional<Apple> min = inventories.stream().min(comparing(Apple::getWeight));

		// reduce sum
		Integer totalWeight = inventories.stream()
			.map(Apple::getWeight)
			.reduce(0, (a, b) -> a + b);

		System.out.println(totalWeight);

		// mapToInt
		int sum = inventories.stream()
			.mapToInt(Apple::getWeight)
			.sum();

		// mapToInt boxed
		Stream<Integer> boxed = inventories.stream()
			.mapToInt(Apple::getWeight)
			.boxed();

		// OptionalInt
		OptionalInt maxOpt = inventories.stream()
			.mapToInt(Apple::getWeight)
			.max();

		maxOpt.orElse(1);
		maxOpt.orElseGet(() -> 1);

		// IntStream range, rangeClosed
		IntStream intStream = IntStream.rangeClosed(1, 100).filter(n -> n % 2 == 0);
		intStream.forEach(System.out::println);

		// map to object
		Stream<Integer> integerStream = IntStream.rangeClosed(1, 100)
			.mapToObj(n -> n);

		// make stream
		Stream.of(1, 2, 3);
		Stream.empty();
		int[] ints = { 1, 2, 3 };
		Arrays.stream(ints);

		// file stream
		try (Stream<String> lines = Files.lines(Paths.get(""))) {

		} catch (IOException e) {
			// ignore
		}

		// 무한 스트림 만들기
		// iterator, generator
		Stream.iterate(0, n -> n + 2)
			.limit(10)
			.forEach(System.out::println);

		// 피보나치 수열
		System.out.println("피보나치");
		Stream.iterate(new long[] { 0, 1 }, n -> new long[] { n[1], n[0] + n[1] })
			.limit(10)
			.map(t -> t[0])
			.forEach(System.out::println);

		// generator
		// supplier를 받기 때문에 잘 쓰면 활용도가 높을 수도...
		Stream.generate(Math::random)
			.limit(5)
			.forEach(System.out::println);

		// IntStream
		IntStream.generate(() -> 1).limit(100).forEach(System.out::println);

		/**
		 * collector?
		 */

		// counting
		numbers.stream().collect(counting());
		numbers.stream().count();

		// maxBy
		Optional<Apple> maxBy = inventories.stream().collect(maxBy(comparing(Apple::getWeight)));

		//averagingInt, averatingLong...
		// summingInt
		Integer summingInt = inventories.stream().collect(summingInt(Apple::getWeight));

		// with reducing
		Integer reducingSum = inventories.stream().collect(reducing(0, Apple::getWeight, Integer::sum));
		Integer reduceSum = inventories.stream().map(Apple::getWeight).reduce(Integer::sum).get();

		// intSummaryStatistics
		IntSummaryStatistics intSummaryStatistics1 = inventories.stream().collect(summarizingInt(Apple::getWeight));
		IntSummaryStatistics intSummaryStatistics2 = inventories.stream().mapToInt(Apple::getWeight).summaryStatistics();

		// joining
		String joining = inventories.stream().map(Apple::getName).collect(joining(",", "_", "-"));
		System.out.println(joining);

		// Collectors.reducing
		Integer totalWeight1 = inventories.stream().collect(reducing(
			0, Apple::getWeight, (oldOne, newOne) -> oldOne + newOne));

		// maxBy with Collectors.reducing
		Optional<Apple> maxBy1 = inventories.stream()
			.collect(reducing(
				(oldOne, newOne) -> oldOne.getWeight() > newOne.getWeight() ? oldOne : newOne));

		/**
		 * grouping
		 */
		Map<String, List<Apple>> group1 = inventories.stream().collect(groupingBy(Apple::getCountry));

		Map<String, List<Apple>> group2 = inventories.stream().collect(
			groupingBy(apple -> {
				if (apple.getWeight() > 100) {
					return "HEAVY";
				} else {
					return "NOT_HEAVY";
				}
			}));

		// 다수준 그룹화
		Map<String, Map<Integer, List<Apple>>> group3 = inventories.stream().collect(
			groupingBy(Apple::getCountry,
				groupingBy(Apple::getWeight)));

		// 서브그룹으로 수집
		Map<String, Long> group4 = inventories.stream().collect(groupingBy(Apple::getCountry, counting()));

		Map<String, Optional<Apple>> group5 = inventories.stream().collect(
			groupingBy(Apple::getCountry,
				maxBy(comparingInt(Apple::getWeight))));

		// collectAndThen 위 예제의 Optional을 없애는 방법
		Map<String, Apple> group6 = inventories.stream()
			.collect(groupingBy(Apple::getCountry,
				collectingAndThen(
					maxBy(comparingInt(Apple::getWeight)), Optional::get)));

		// groupingBy와 함께 사용하는 다른 컬렉터 예제
		Map<String, Integer> group7 = inventories.stream().collect(groupingBy(Apple::getCountry, summingInt(Apple::getWeight)));

		Map<String, HashSet<String>> group8 = inventories.stream()
			.collect(groupingBy(Apple::getCountry, mapping(apple -> {
				if (apple.getWeight() > 150) {
					return "BIG";
				} else {
					return "SMALL";
				}
			}, toCollection(HashSet::new)))); // or toSet()

		/**
		 * partitioning
		 */

		Map<Boolean, List<Apple>> partition1 = inventories.stream().collect(partitioningBy(Apple::isMadeByKorea));

		Map<Boolean, Map<String, List<Apple>>> partition2 = inventories.stream()
			.collect(partitioningBy(Apple::isMadeByKorea, groupingBy(Apple::getCountry)));

		Map<Boolean, Apple> partition3 = inventories.stream()
			.collect(partitioningBy(Apple::isMadeByKorea,
				collectingAndThen(maxBy(comparing(Apple::getWeight)), Optional::get)));

		/**
		 * 숫자를 소수와 비소수로 분할하기
		 */

		Map<Boolean, List<Integer>> collect = IntStream.rangeClosed(2, 100).boxed()
			.collect(partitioningBy(candidate -> isPrime(candidate)));

		// toList
		// toSet
		// toCollection
		// counting
		// summingInt
		// averagingInt
		// summarizingInt
		// joining
		// maxBy
		// minBy
		// reducing
		// collectingAndThen
		// groupingBy
		// partitionBy

		/**
		 * collector interface
		 */
	}

	public static boolean isPrime(int candidate) {
		int candidateRoot = (int) Math.sqrt((double) candidate);
		return IntStream.range(2, candidateRoot).noneMatch(i -> candidate % 2 == 0);
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
