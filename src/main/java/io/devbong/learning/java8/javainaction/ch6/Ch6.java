package io.devbong.learning.java8.javainaction.ch6;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.summingInt;

public class Ch6 {

	public static void main(String[] args) throws InterruptedException {

		Dish pasta = Dish.of("파스타", 100);
		Dish jjajang = Dish.of("짜장면", 200);
		Dish bob = Dish.of("쌀밥", 50);

		List<Dish> menu = Arrays.asList(pasta, jjajang, bob);

		long menuCount = menu.stream().count();
		System.out.println("menuCount : " + menuCount);

		Optional<Dish> maxCaloriesOpt = menu.stream().collect(maxBy(comparing(Dish::getCalories)));
		maxCaloriesOpt.ifPresent(System.out::println);

		Integer caloriesSum = menu.stream().collect(summingInt(Dish::getCalories));

		IntSummaryStatistics summaryStatistics = menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));

		Integer calroiesSum = menu.stream().collect(Collectors.reducing(0, Dish::getCalories, (a1, a2) -> a1 + a2));

		System.out.println(calroiesSum);

		Supplier<Date> dateSupplier = new Supplier<Date>() {
			@Override
			public Date get() {
				return new Date();
			}
		};

		System.out.println(dateSupplier.get());
		Thread.sleep(1000);
		System.out.println(dateSupplier.get());

		System.out.println(transformLong(1).get());

	}

	public static Supplier<Long> transformLong(final int num) {
		return () -> Long.valueOf(num);
	}
}