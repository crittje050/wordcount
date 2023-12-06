# Word Count Analyzer

## Introduction
This is a small project that implements a word count analyzer.
The project is written with Java (17) and is a multi-module maven project.

## Modules

The `core` module represents the library containing the required functions
to analyze a text and return the word count.

The `rest-adapter` module represents a REST API that exposes the core library using Jakarta.

## Build

First clone the project from the repository as per your preferences.

To build the project, run the following command in the root directory of the project:

```shell
$ mvn clean install
```

## Run

To run the REST API, run the following command in the root directory of the project:

```shell
$ java -jar ./rest-adapter/target/rest-adapter-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

Swagger documentation is available at: http://localhost:8080/ui.

The relevant endpoint is called `word-count`, you can use swagger or any other client.

## Test

To run the tests, run the following command in the root directory of the project:

```shell
$ mvn clean test
```
