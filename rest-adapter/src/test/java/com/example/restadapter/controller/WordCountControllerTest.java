/* (C)2023 */
package com.example.restadapter.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.core.application.exception.WordFrequencyAnalyzerException;
import com.example.core.application.exception.WordFrequencyException;
import com.example.core.application.service.WordFrequencyAnalyzerService;
import com.example.core.domain.WordFrequency;
import com.example.core.domain.WordFrequencyImpl;
import jakarta.ws.rs.WebApplicationException;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WordCountControllerTest {

  private WordCountController testable;
  private WordFrequencyAnalyzerService wordFrequencyAnalyzerService;

  @BeforeEach
  void setUp() {

    wordFrequencyAnalyzerService = mock(WordFrequencyAnalyzerService.class);
    testable = new WordCountController(wordFrequencyAnalyzerService);
  }

  @Test
  void testGetWordWhenGivenIsOkReturnsPlainTextResponse() {

    final String text = "This is a test text";
    final String word = "test";
    final int n = 2;
    List<WordFrequency> wordFrequencyList =
        Collections.singletonList(new WordFrequencyImpl(word, 1));

    final String expectedResponse =
        String.format(
            "Highest frequency: %s%nFrequency for word '%s': %s%nMost frequent N words: %s",
            1, word, 1, wordFrequencyList);

    when(wordFrequencyAnalyzerService.calculateHighestFrequency(text)).thenReturn(1);
    when(wordFrequencyAnalyzerService.calculateFrequencyForWord(text, word)).thenReturn(1);
    when(wordFrequencyAnalyzerService.calculateMostFrequentNWords(text, n))
        .thenReturn(wordFrequencyList);

    final String result = testable.getWordCount(text, word, n).getEntity().toString();

    assertEquals(expectedResponse, result);
  }

  @Test
  void
      testGetWordWhenServiceCalculateHighestFrequencyThrowsWordFrequencyAnalyzerExceptionRestApiThrowsWebApplicationException() {

    final String text = "This is a test text";
    final String word = "test";
    final int n = 2;

    when(wordFrequencyAnalyzerService.calculateHighestFrequency(text))
        .thenThrow(new WordFrequencyAnalyzerException(anyString()));

    assertThrows(WebApplicationException.class, () -> testable.getWordCount(text, word, n));
  }

  @Test
  void
      testGetWordWhenServiceCalculateFrequencyForWordThrowsWordFrequencyAnalyzerExceptionRestApiThrowsWebApplicationException() {

    final String text = "This is a test text";
    final String word = "test";
    final int n = 2;

    when(wordFrequencyAnalyzerService.calculateFrequencyForWord(text, word))
        .thenThrow(new WordFrequencyAnalyzerException(anyString()));

    assertThrows(WebApplicationException.class, () -> testable.getWordCount(text, word, n));
  }

  @Test
  void
      testGetWordWhenCalculateMostFrequentNWordsWordFrequencyExceptionRestApiThrowsWebApplicationException() {

    final String text = "This is a test text";
    final String word = "test";
    final int n = 2;

    when(wordFrequencyAnalyzerService.calculateMostFrequentNWords(text, n))
        .thenThrow(new WordFrequencyException(anyString()));

    assertThrows(WebApplicationException.class, () -> testable.getWordCount(text, word, n));
  }
}
