/* (C)2023 */
package com.example.core.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.example.core.application.exception.WordFrequencyException;
import org.junit.jupiter.api.Test;

class WordFrequencyImplTest {

  private WordFrequency testable;

  @Test
  void testCreateByConstructorWhenWordIsNullThrowsException() {
    assertThrows(WordFrequencyException.class, () -> testable = new WordFrequencyImpl(null, 1));
  }

  @Test
  void testCreateByConstructorWhenWordContainsNonLetters() {
    assertThrows(WordFrequencyException.class, () -> testable = new WordFrequencyImpl("word1", 1));
  }

  @Test
  void testInitialization() {

    final String word = "word";
    final int frequency = 1;

    testable = new WordFrequencyImpl(word, frequency);

    assertEquals(word, testable.word());
    assertEquals(frequency, testable.frequency());
  }
}
