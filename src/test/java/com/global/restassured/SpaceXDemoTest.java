package com.global.restassured;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class SpaceXDemoTest {

  @Test
  void testAllLaunches() {

    String query =
        "{\"query\":\"{\\n launches {\\n id\\n mission_name\\n ships {\\n id\\n }\\n rocket {\\n first_stage {\\n cores {\\n flight\\n }\\n }\\n second_stage {\\n block\\n }\\n }\\n }\\n }\",\"variables\":null}";

    given()
        .contentType(ContentType.JSON)
        .body(query)
        .when()
        .post("https://api.spacex.land/graphql/")
        .then()
        .assertThat()
        .statusCode(200)
        .and().body("data.launches.mission_name", everyItem(not(emptyOrNullString())))
        .and().body("data.launches", hasSize(greaterThan(0)))
        .and().body("data.launches.ships", hasSize(greaterThan(0)))
        .and().body("data.launches.rocket.first_stage", everyItem(notNullValue()))
        .and().body("data.launches.rocket.second_stage", everyItem(notNullValue()));
  }

    @Test
    void getAllLaunchesWithLimit() {
        String query = "{\"query\":\"{\\n launches(limit: 2) {\\n id\\n mission_name\\n ships {\\n id\\n }\\n rocket {\\n first_stage {\\n cores {\\n flight\\n }\\n }\\n second_stage {\\n block\\n }\\n }\\n }\\n }\",\"variables\":null}";
        given()
                .contentType(ContentType.JSON)
                .body(query)
                .when()
                .post("https://api.spacex.land/graphql/")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and().body("data.launches.mission_name", everyItem( not(emptyOrNullString())))
                .and().body("data.launches", hasSize(greaterThan(0)))
                .and().body("data.launches.ships", hasSize(greaterThan(0)))
                .and().body("data.launches.rocket.first_stage", everyItem(notNullValue()))
                .and().body("data.launches.rocket.second_stage", everyItem(notNullValue()));
    }

    @Test
    void getAllLaunchesWithOffset() {
        String query = "{\"query\":\"{\\n launches(offset: 2) {\\n id\\n mission_name\\n ships {\\n id\\n }\\n rocket {\\n first_stage {\\n cores {\\n flight\\n }\\n }\\n second_stage {\\n block\\n }\\n }\\n }\\n }\",\"variables\":null}";
        given()
                .contentType(ContentType.JSON)
                .body(query).log().all()
                .when()
                .post("https://api.spacex.land/graphql/")
                .then()
                .assertThat()
                .statusCode(200)
                .and().body("data.launches.mission_name", everyItem( not(emptyOrNullString())))
                .and().body("data.launches", hasSize(greaterThan(0)))
                .and().body("data.launches.ships", hasSize(greaterThan(0)))
                .and().body("data.launches.rocket.first_stage", everyItem(notNullValue()))
                .and().body("data.launches.rocket.second_stage", everyItem(notNullValue()));
    }

}