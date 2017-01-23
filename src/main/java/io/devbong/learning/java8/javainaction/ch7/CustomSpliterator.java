package io.devbong.learning.java8.javainaction.ch7;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CustomSpliterator {

	public static void main(String[] args) {

		final String sentence = "Java8 is very good";

		System.out.println(countWordsIteratively(sentence));


		WordCounter reduce = getCharStream(sentence)
			.reduce(new WordCounter(0, true),
				WordCounter::accumulate,
				WordCounter::combine);

		System.out.println(reduce.getCounter());

		WordCounter reduce1 = getCharStream(sentence).
			parallel()
			.reduce(new WordCounter(0, true),
				WordCounter::accumulate,
				WordCounter::combine);
		System.out.println(reduce1.getCounter());

		WordCounterSpliterator wordCounterSpliterator = new WordCounterSpliterator(sentence);
		Stream<Character> stream1 = StreamSupport.stream(wordCounterSpliterator, true);
		WordCounter reduce2 = stream1.parallel().reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
		System.out.println(reduce2.getCounter());

	}

	public static Stream<Character> getCharStream(String sentence) {
		return IntStream.range(0, sentence.length()).mapToObj(sentence::charAt);
	}

	public static int countWordsIteratively(String s) {

		int counter = 0;
		boolean lastSpace = true;

		for (char c : s.toCharArray()) {
			if (Character.isWhitespace(c)) {
				lastSpace = true;
			} else {
				if (lastSpace) {
					counter++;
				}

				lastSpace = false;
			}
		}

		return counter;
	}

	public static class WordCounter {

		private final int counter;
		private final boolean lastSpace;

		public WordCounter(int counter, boolean lastSpace) {
			this.counter = counter;
			this.lastSpace = lastSpace;
		}

		public WordCounter accumulate(Character c) {
			if (Character.isWhitespace(c)) {
				return lastSpace ? this : new WordCounter(counter, true);
			} else {
				return lastSpace ? new WordCounter(counter + 1, false) : this;
			}
		}

		public WordCounter combine(WordCounter wordCounter) {
			return new WordCounter(counter + wordCounter.counter, wordCounter.lastSpace);
		}

		public int getCounter() {
			return counter;
		}
	}

	public static class WordCounterSpliterator implements Spliterator<Character> {

		private final String string;
		private int currentChar = 0;

		public WordCounterSpliterator(String string) {
			this.string = string;
		}

		@Override
		public boolean tryAdvance(Consumer<? super Character> action) {
			action.accept(string.charAt(currentChar++));
			return currentChar < string.length();
		}

		@Override
		public Spliterator<Character> trySplit() {
			int currentSize = string.length() - currentChar;

			if (currentSize < 10) {
				return null;
			}

			for (int splitPos = currentSize / 2 + currentChar; splitPos < string.length(); splitPos++) {
				if (Character.isWhitespace(string.charAt(splitPos))) {
					Spliterator<Character> spliterator = new WordCounterSpliterator(string.substring(currentChar, splitPos));
					currentChar = splitPos;
					return spliterator;
				}
			}

			return null;
		}

		@Override
		public long estimateSize() {
			return string.length() - currentChar;
		}

		@Override
		public int characteristics() {
			return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
		}
	}

}
