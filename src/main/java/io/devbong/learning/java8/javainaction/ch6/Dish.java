package io.devbong.learning.java8.javainaction.ch6;

public class Dish {
	private String name;
	private Integer calories;

	public static Dish of(String name, int calories) {
		Dish dish = new Dish();
		dish.name = name;
		dish.calories = calories;
		return dish;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCalories() {
		return this.calories;
	}

	public void setCalories(Integer calories) {
		this.calories = calories;
	}

	@Override
	public String toString() {
		return "{" + this.name + " : " + this.calories + "}";
	}
}
