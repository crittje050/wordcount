/* (C)2023 */
package com.example.core.domain;

import com.example.core.application.exception.WordFrequencyException;

public record WordFrequencyImpl(String word, int frequency) implements WordFrequency {
  public WordFrequencyImpl {
    if (word == null || word.isEmpty()) {
      throw new WordFrequencyException("Word cannot be null or empty");
    }

    if (!word.matches("[a-zA-Z]+")) {
      throw new WordFrequencyException("Word must contain only letters");
    }
  }
}
