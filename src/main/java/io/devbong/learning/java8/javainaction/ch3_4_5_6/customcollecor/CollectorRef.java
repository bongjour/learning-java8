package io.devbong.learning.java8.javainaction.ch3_4_5_6.customcollecor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.devbong.learning.java8.javainaction.ch3_4_5_6.customcollecor.CollectorRef.Dish.Country.*;

public class CollectorRef {

	public static void main(String[] args) {

		List<Dish> dishes = Arrays.asList(
			Dish.of("김치찌게", 100, KOREA),
			Dish.of("된장찌게", 50, KOREA),
			Dish.of("라면", 200, KOREA),
			Dish.of("돈까스", 300, KOREA),
			Dish.of("초밥", 150, JAPAN),
			Dish.of("스시", 200, JAPAN),
			Dish.of("스테이크", 300, US),
			Dish.of("햄버거", 250, US),
			Dish.of("피쉬앤칩스", 120, UK),
			Dish.of("홍합스파게티", 190, UK),
			Dish.of("달팽이요리", 400, FRANCE),
			Dish.of("알리올리오", 140, FRANCE)
		);

		List<Dish> collect1 = dishes.stream().collect(new ToListCollector<Dish>());

		// custom 없이 collector 적용
		ArrayList<Dish> collect2 = dishes.stream().collect(ArrayList::new, List::add, List::addAll);

	}

	public static class Dish {

		private String name;
		private int calories;
		private Country country;

		public static Dish of(String name, int calories, Country country) {
			Dish dish = new Dish();
			dish.name = name;
			dish.calories = calories;
			dish.country = country;
			return dish;
		}

		public String getName() {
			return this.name;
		}

		public int getCalories() {
			return this.calories;
		}

		public Country getCountry() {
			return this.getCountry();
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setCalories(int calories) {
			this.calories = calories;
		}

		public void setCountry(Country country) {
			this.country = country;
		}

		public enum Country {
			KOREA, US, JAPAN, UK, FRANCE
		}
	}

}