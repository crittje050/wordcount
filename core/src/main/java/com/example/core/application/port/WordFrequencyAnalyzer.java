/* (C)2023 */
package com.example.core.application.port;

import com.example.core.domain.WordFrequency;
import java.util.List;

public interface WordFrequencyAnalyzer {

  int calculateHighestFrequency(final String text);

  int calculateFrequencyForWord(final String text, final String word);

  List<WordFrequency> calculateMostFrequentNWords(final String text, int n);
}
