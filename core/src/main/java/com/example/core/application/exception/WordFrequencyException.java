/* (C)2023 */
package com.example.core.application.exception;

public class WordFrequencyException extends RuntimeException {

  public WordFrequencyException(final String message) {
    super("Cannot construct WordFrequency: " + message);
  }
}
