/* (C)2023 */
package com.example.restadapter.config;

import com.example.core.application.service.WordFrequencyAnalyzerService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ServiceBindings extends AbstractBinder {
  @Override
  protected void configure() {
    bind(WordFrequencyAnalyzerService.class).to(WordFrequencyAnalyzerService.class);
  }
}
