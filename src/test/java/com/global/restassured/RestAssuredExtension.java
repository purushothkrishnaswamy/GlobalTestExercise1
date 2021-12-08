package com.global.restassured;

import io.restassured.RestAssured;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Optional;

class RestAssuredExtension implements BeforeAllCallback, AfterAllCallback {

  @Override
  public void beforeAll(ExtensionContext context) {
    Optional<String> baseUri = Optional.ofNullable(System.getProperty("baseUri"));
    RestAssured.baseURI = baseUri.orElse("https://api.spacex.land/graphql/");
  }

  @Override
  public void afterAll(ExtensionContext context){
    RestAssured.reset();
  }
}