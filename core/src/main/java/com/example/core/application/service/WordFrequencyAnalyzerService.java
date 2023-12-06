/* (C)2023 */
package com.example.core.application.service;

import com.example.core.application.exception.WordFrequencyAnalyzerException;
import com.example.core.application.port.WordFrequencyAnalyzer;
import com.example.core.domain.WordFrequency;
import com.example.core.domain.WordFrequencyImpl;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordFrequencyAnalyzerService implements WordFrequencyAnalyzer {

  /**
   * The function will return the highest frequency in the text (several words might actually have
   * this frequency).
   *
   * @param text The text to analyze.
   * @throws WordFrequencyAnalyzerException when a text unexpectedly null
   * @return The highest frequency in the text.
   */
  @Override
  public int calculateHighestFrequency(final String text) {

    validateText(text);

    return groupWordsAndFrequencies(text).entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .map(entrySet -> entrySet.getValue().intValue())
        .orElse(0);
  }

  /**
   * The function will return the frequency of the specified word.
   *
   * @param text The text to analyze.
   * @param word The word to calculate the frequency for.
   * @throws WordFrequencyAnalyzerException when a text or word is unexpectedly null
   * @return The frequency of the specified word.
   */
  @Override
  public int calculateFrequencyForWord(String text, String word) {

    validateTextAndWord(text, word);

    return groupWordsAndFrequencies(text).entrySet().stream()
        .filter(entrySet -> entrySet.getKey().equals(word))
        .map(entrySet -> entrySet.getValue().intValue())
        .findFirst()
        .orElse(0);
  }

  /**
   * The function will return a list of the most frequent N words in the input text, all the words.
   * In addition it sorts the words in a descending order by their frequency and alphabetic order.
   *
   * @param text The text to analyze.
   * @param n The number of words to return.
   * @return A list of the most frequent N words in the input text.
   */
  @Override
  public List<WordFrequency> calculateMostFrequentNWords(String text, int n) {

    validateText(text);

    return groupWordsAndFrequencies(text).entrySet().stream()
        .map(entrySet -> new WordFrequencyImpl(entrySet.getKey(), entrySet.getValue().intValue()))
        .sorted(
            Comparator.comparing(WordFrequency::frequency)
                .reversed()
                .thenComparing(WordFrequency::word))
        .limit(n)
        .collect(Collectors.toList());
  }

  private Map<String, Long> groupWordsAndFrequencies(final String text) {

    String[] words = text.split("[\\s\\p{Punct}]+");

    return Stream.of(words)
        .map(String::toLowerCase)
        .collect(Collectors.groupingBy(word -> word, Collectors.counting()));
  }

  private void validateText(String text) {
    if (text == null) {
      throw new WordFrequencyAnalyzerException(
          "Whoops something went wrong! Please provide a text to analyze.");
    }
  }

  private void validateTextAndWord(String text, String word) {
    validateText(text);

    if (word == null) {
      throw new WordFrequencyAnalyzerException(
          "Whoops something went wrong! Please provide a word to analyze.");
    }
  }
}
