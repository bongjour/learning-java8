package io.devbong.learning.java8.javainaction.ch5;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class Ch5Exercise {

	public static void main(String[] args) {

		Trader raoul = new Trader("Raoul", "Cambridge");
		Trader mario = new Trader("Mario", "Milan");
		Trader alan = new Trader("Alan", "Cambridge");
		Trader brian = new Trader("Brian", "Cambridge");

		List<Transaction> transactions = Arrays.asList(
			new Transaction(brian, 2011, 300),
			new Transaction(raoul, 2012, 1000),
			new Transaction(raoul, 2011, 400),
			new Transaction(mario, 2012, 710),
			new Transaction(mario, 2012, 700),
			new Transaction(alan, 2012, 950)
		);

		// 1. 2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정리하시오.
		List<Transaction> collect1 = transactions.stream()
			.filter(each -> each.getYear() == 2011)
			.sorted(comparing(Transaction::getValue))
			.collect(toList());

		System.out.println("1 : " + collect1);

		// 2. 거래자가 근무하는 모든 도시를 중복 없이 나열 하시오.
		List<String> collect2 = transactions.stream()
			.map(transaction -> transaction.getTrader().getName())
			.distinct()
			.collect(toList());

		System.out.println("2 : " + collect2);

		// 3. Cambridge 에서 금무하는 모든 거래자를 찾아서 이름순으로 정렬하시오

		List<Trader> collect3 = transactions.stream()
			.map(Transaction::getTrader)
			.filter(trader -> trader.getCity().equals("Cambridge"))
			.distinct()
			.sorted(comparing(Trader::getName))
			.collect(toList());

		System.out.println("3 : " + collect3);

		// 4. 모든 거래자의 이름을 알파벳순으로 정렬해서 반환 하시오

		String tradeStr1 = transactions.stream()
			.map(transaction -> transaction.getTrader().getName())
			.distinct()
			.sorted()
			.reduce("", (n1, n2) -> n1 + n2);

		// or
		String tradeStr2 = transactions.stream()
			.map(transaction -> transaction.getTrader().getName())
			.distinct()
			.sorted()
			.collect(Collectors.joining(","));

		System.out.println("4 - 1 : " + tradeStr1);
		System.out.println("4 - 2 : " + tradeStr2);

		// 5. 밀라노에 거래자가 있는가?
		boolean isWorkingAtMilan = transactions.stream()
			.anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));

		System.out.println("5 : " + isWorkingAtMilan);

		// 6. 케임브리지에 거주하는 거래자의 모든 트랜잭션값을 출력하시오.
		transactions.stream()
			.filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
			.map(Transaction::getValue)
			.forEach(System.out::println);

		// 7. 전체 트랜잭션 중 최댓값은 얼마인가?
		// 8. 전체 트랜잭션 중 최솟값은 얼마인가?
		IntSummaryStatistics statistics = transactions.stream()
			.mapToInt(Transaction::getValue)
			.summaryStatistics();

		// or
		Optional<Integer> maxOpt = transactions.stream()
			.map(Transaction::getValue)
			.reduce(Integer::max);

		Optional<Transaction> minOpt = transactions.stream()
			.reduce((t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2);

		System.out.println("7 : " + statistics.getMax());
		System.out.println("8 : " + statistics.getMin());

	}
}
