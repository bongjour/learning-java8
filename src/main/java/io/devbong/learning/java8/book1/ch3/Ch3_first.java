package io.devbong.learning.java8.book1.ch3;

import io.devbong.learning.java8.book1.ch3.Employee.Name;

import java.util.Arrays;
import java.util.stream.DoubleStream;

public class Ch3_first {

	public static void main(String[] args) {

		Measurable employee1 = Employee.of(Name.of("James", "Dean"), Double.valueOf(3000000));
		Measurable employee2 = Employee.of(Name.of("Dark", "Knight"), Double.valueOf(4000000));
		Measurable employee3 = Employee.of(Name.of("Luis", "Bark"), Double.valueOf(5000000));

		Measurable[] objects = { employee1, employee2, employee3 };

		double average = average(objects);

		System.out.println(average);
	}

	public static double average(Measurable[] objects) {
		DoubleStream doubleStream = Arrays.asList(objects).stream().mapToDouble(each -> each.getMeasure());
		return doubleStream.average().getAsDouble();
	}
}