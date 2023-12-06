/* (C)2023 */
package com.example.core.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.core.application.exception.WordFrequencyAnalyzerException;
import com.example.core.application.port.WordFrequencyAnalyzer;
import com.example.core.application.service.WordFrequencyAnalyzerService;
import com.example.core.domain.WordFrequency;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WordFrequencyAnalyzerServiceTest {

  private WordFrequencyAnalyzer testable;

  @BeforeEach
  void setUp() {

    testable = new WordFrequencyAnalyzerService();
  }

  @Test
  void testCalculateHighestFrequency() {

    String text = "The sun shines over the lake";

    int highestFrequency = testable.calculateHighestFrequency(text);

    assertEquals(2, highestFrequency);
  }

  @Test
  void testCalculateFrequencyForWord() {

    String text = "The sun shines over the lake sun sun";
    String word = "sun";

    int frequencyForWord = testable.calculateFrequencyForWord(text, word);

    assertEquals(3, frequencyForWord);
  }

  @Test
  void testCalculateMostFrequentNWords() {

    String text = "The sun shines over the lake sun sun";
    int n = 2;

    List<WordFrequency> mostFrequentNWords = testable.calculateMostFrequentNWords(text, n);

    assertEquals(2, mostFrequentNWords.size());
    assertEquals("sun", mostFrequentNWords.get(0).word());
    assertEquals(3, mostFrequentNWords.get(0).frequency());
    assertEquals("the", mostFrequentNWords.get(1).word());
    assertEquals(2, mostFrequentNWords.get(1).frequency());
  }

  @Test
  void testCalculateHighestFrequencyWithEmptyTextThrows() {

    String text = "";

    assertThrows(
        WordFrequencyAnalyzerException.class, () -> testable.calculateHighestFrequency(text));
  }

  @Test
  void testCalculateFrequencyForWordWhenWordIsEmptyThrows() {

    String text = "The sun shines over the lake";

    assertThrows(
        WordFrequencyAnalyzerException.class, () -> testable.calculateFrequencyForWord(text, ""));
  }

  @Test
  void testCalculateFrequencyForWordWhenTextIsEmptyThrows() {

    String word = "sun";

    assertThrows(
        WordFrequencyAnalyzerException.class, () -> testable.calculateFrequencyForWord("", word));
  }

  @Test
  void testCalculateMostFrequentNWordsIsDescendingOrder() {

    String text = "The sun its son shines over son its lake sun";
    int n = 4;

    List<WordFrequency> mostFrequentNWords = testable.calculateMostFrequentNWords(text, n);

    assertEquals(4, mostFrequentNWords.size());
    assertEquals("its", mostFrequentNWords.get(0).word());
    assertEquals(2, mostFrequentNWords.get(0).frequency());
    assertEquals("son", mostFrequentNWords.get(1).word());
    assertEquals(2, mostFrequentNWords.get(1).frequency());
    assertEquals("sun", mostFrequentNWords.get(2).word());
    assertEquals(2, mostFrequentNWords.get(2).frequency());
    assertEquals("lake", mostFrequentNWords.get(3).word());
    assertEquals(1, mostFrequentNWords.get(3).frequency());
  }

  @Test
  void testCalculateMostFrequentNWordsWhenTextIsEmptyThrows() {

    int n = 1;

    assertThrows(
        WordFrequencyAnalyzerException.class, () -> testable.calculateMostFrequentNWords("", n));
  }

  @Test
  void testCalculateMostFrequentNWordsIsNegativeThrows() {

    String text = "The sun shines over the lake";
    int n = -1;

    assertThrows(
        WordFrequencyAnalyzerException.class, () -> testable.calculateMostFrequentNWords(text, n));
  }
}
