package io.devbong.learning.java8.javainaction.ch3;

import java.util.Arrays;
import java.util.List;

import static java.util.Comparator.comparing;

public class LearningLambda {

	public static void main(String[] args) {

		Apple apple1 = Apple.of("apple1", 100);
		Apple apple2 = Apple.of("apple2", 200);
		Apple apple3 = Apple.of("apple3", 300);
		Apple apple4 = Apple.of("apple4", 400);
		Apple apple5 = Apple.of("apple5", 300);

		List<Apple> apples = Arrays.asList(apple1, apple2, apple3, apple4, apple5);

		System.out.println(apples);
		apples.sort(comparing(Apple::getWeight));
		System.out.println(apples);
	}

	private static class Apple {
		private String name;
		private int weight;

		public static Apple of(String name, int weight) {
			Apple apple = new Apple();
			apple.name = name;
			apple.weight = weight;
			return apple;
		}

		public String getName() {
			return this.name;
		}

		public int getWeight() {
			return this.weight;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(this.name);
			sb.append(":");
			sb.append(this.weight);
			return sb.toString();
		}
	}
}
