package io.devbong.learning.java8.horstmann.ch3;

public class Employee implements Measurable {

	private Name name;
	// etc 생략
	private double salary;

	@Override
	public double getMeasure() {
		return salary;
	}

	public static Employee of(Name name, double salary) {
		Employee employee = new Employee();
		employee.name = name;
		employee.salary = salary;
		return employee;
	}

	public Name getName() {
		return this.name;
	}

	public double getSalary() {
		return this.salary;
	}

	public static class Name {

		private String firstName;
		private String lastName;

		public static Name of(String firstName, String lastName) {
			Name name = new Name();
			name.firstName = firstName;
			name.lastName = lastName;
			return name;
		}

	}
}