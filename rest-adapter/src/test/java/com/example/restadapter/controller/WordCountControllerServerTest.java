/* (C)2023 */
package com.example.restadapter.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.example.restadapter.config.ServiceBindings;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;

class WordCountControllerServerTest extends JerseyTest {

  @Override
  protected Application configure() {
    return new ResourceConfig(WordCountController.class).register(new ServiceBindings());
  }

  @Test
  void testGetWordCountWithAllQueryParamsReturnsHttpStatus200() {

    Response response =
        target("/word-count")
            .queryParam("text", "test text")
            .queryParam("word", "text")
            .queryParam("n", 1)
            .request()
            .get();

    assertEquals(200, response.getStatus());
  }

  @Test
  void testGetWordCountWithTextIsNullReturnsHttpStatus400() {

    Response response =
        target("/word-count").queryParam("word", "text").queryParam("n", 1).request().get();

    assertEquals(400, response.getStatus());
    assertEquals(
        "Whoops something went wrong! Please provide a text to analyze.",
        response.readEntity(String.class));
  }

  @Test
  void testGetWordCountWithWordIsNullReturnsHttpStatus400() {

    Response response =
        target("/word-count").queryParam("text", "text").queryParam("n", 1).request().get();

    assertEquals(400, response.getStatus());
    assertEquals(
        "Whoops something went wrong! Please provide a word to analyze.",
        response.readEntity(String.class));
  }

  @Test
  void testGetWordCountWithTextHasNumbersIsNullReturnsHttpStatus400() {

    Response response =
        target("/word-count")
            .queryParam("text", "text123")
            .queryParam("word", "text")
            .queryParam("n", 1)
            .request()
            .get();

    assertEquals(400, response.getStatus());
    assertEquals(
        "Cannot construct WordFrequency: Word must contain only letters",
        response.readEntity(String.class));
  }
}
