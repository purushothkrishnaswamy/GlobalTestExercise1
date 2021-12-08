package com.global.restassured;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Epic("SpaceX GraphQL Read Operations using Rest Assured")
@Feature("Verify Fetch Operations on Launches in SpaceXLand")
@ExtendWith(RestAssuredExtension.class)
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = {"classpath:application-test.properties"})
class SpaceXDemoTest {

    @Value("${getAllLaunches}")
    private String getAllLaunchesQuery;

    @Value("${getAllLaunchesWithLimit}")
    private String getAllLaunchesWithLimitQuery;

    @Value("${getAllLaunchesWithOffset}")
    private String getAllLaunchesWithOffsetQuery;

    @Test
    @Story("Verify Get All Launches")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description : Verify the Launches")
    void testAllLaunches() {

        GraphQLQuery query = new GraphQLQuery();
        query.setQuery(getAllLaunchesQuery);

        // Post Service call
        Response response = getRequestSpecification(query).expect().when().post();
        // General Assertions
        assertLaunches(response);
    }

    @ParameterizedTest
    @Story("Verify Get Launches with a specified Limit")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description : Verify the Launches with limit")
    @ValueSource(ints = {2, 5, 10})
    void testLaunchesWithLimit(int limit) {

        GraphQLQuery query = new GraphQLQuery();
        query.setQuery(getAllLaunchesWithLimitQuery);

        JSONObject variables = new JSONObject();
        variables.put("limit", limit);
        query.setVariables(variables.toString());

        // Post Service call
        Response response = getRequestSpecification(query).expect().when().post();

        // Asset results have the right size based on limit
        assertThat(response.path("data.launches"), hasSize(limit));
        // General Assertions
        assertLaunches(response);
    }

    @ParameterizedTest
    @Story("Verify Get Launches with specified offset")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description : Verify the Launches with offset")
    @ValueSource(ints = {5, 10, 20})
    void testLaunchesWithOffset(int offset) {

        GraphQLQuery query = new GraphQLQuery();
        query.setQuery(getAllLaunchesWithOffsetQuery);

        JSONObject variables = new JSONObject();
        variables.put("offset", offset);
        query.setVariables(variables.toString());

        // Post Service call
        Response response = getRequestSpecification(query).expect().when().post();
        // General Assertions
        assertLaunches(response);
    }

    private RequestSpecification getRequestSpecification(GraphQLQuery query) {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setContentType("application/json");
        requestSpecBuilder.setBody(query);

        return RestAssured.given(requestSpecBuilder.build());
    }

    private void assertLaunches(Response response) {
        assertEquals(200, response.statusCode());
        assertThat(response.path("data.launches.mission_name"), everyItem(not(emptyOrNullString())));
        assertThat(response.path("data.launches"), hasSize(greaterThan(0)));
        assertThat(response.path("data.launches.ships"), hasSize(greaterThan(0)));
        assertNotNull(response.path("data.launches.rocket.first_stage"));
        assertNotNull(response.path("data.launches.rocket.second_stage"));
    }
}