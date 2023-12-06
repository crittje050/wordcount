/* (C)2023 */
package com.example.restadapter.controller;

import static org.slf4j.LoggerFactory.getLogger;

import com.example.core.application.exception.WordFrequencyAnalyzerException;
import com.example.core.application.exception.WordFrequencyException;
import com.example.core.application.service.WordFrequencyAnalyzerService;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;

@Path("/word-count")
public class WordCountController {

  private final WordFrequencyAnalyzerService wordFrequencyAnalyzerService;
  private final Logger logger;

  @Inject
  public WordCountController(WordFrequencyAnalyzerService wordFrequencyAnalyzerService) {
    this.wordFrequencyAnalyzerService = wordFrequencyAnalyzerService;
    this.logger = getLogger(WordCountController.class);
  }

  @GET
  @Produces("text/plain")
  public Response getWordCount(
      @QueryParam("text") @NotNull final String text,
      @QueryParam("word") @NotNull final String word,
      @QueryParam("n") final int n) {

    logger.info("Request for analysis received with text: '{}', word: '{}', n: {}.", text, word, n);

    try {

      return Response.ok(
              String.format(
                  "Highest frequency: %s%nFrequency for word '%s': %s%nMost frequent N words: %s",
                  wordFrequencyAnalyzerService.calculateHighestFrequency(text),
                  word,
                  wordFrequencyAnalyzerService.calculateFrequencyForWord(text, word),
                  wordFrequencyAnalyzerService.calculateMostFrequentNWords(text, n)))
          .build();

    } catch (WordFrequencyAnalyzerException | WordFrequencyException e) {
      if (logger.isErrorEnabled()) {
        logger.error("Error while analyzing: {}", e.getMessage());
      }
      throw new WebApplicationException(
          Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build());
    }
  }
}
