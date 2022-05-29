package com.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.junit.Test;
import org.testng.Assert;

import java.util.Collections;

public class myWorkspaceGet {

    //https://reqres.in/

    //1) Create mock in Postman + 2 examples with status code 200, 503
    //2)generate key in MyWorkspace: https://web.postman.co/settings/me/api-keys
    //PMAK-628ab087ec9e1a3630a2d1be-aeab88f4fcae014bcfd702cff3341a8681A

    //RESPONSE
    /*
   {
    "workspaces": [
        {
            "id": "23a1c63b-9559-4b5a-847e-29465c0575b2",
            "name": "My Workspace",
            "type": "personal"
        }
    ]
    }
   */

    String baseUri = "https://api.postman.com";
    String xApiKey = "X-Api-Key";
    String apiKey = "PMAK-628ab087ec9e1a3630a2d1be-aeab88f4fcae014bcfd702cff3341a8681";
    String path = "/workspaces";

    //Request: Validate status code 200
    @Test
    public void validate_get_status_code(){
        given().
                baseUri(baseUri).
                header(xApiKey, apiKey).
                when().
                get(path).
                then().
                assertThat().
                statusCode(200).
                log().all();
    }

    //Request: Validate body
    @Test
    public void validate_get_status_body(){
        given().
                baseUri(baseUri).
                header(xApiKey, apiKey).
                when().
                get(path).
                then().
                assertThat().
                statusCode(200).
                body("workspaces.name",hasItems("My Workspace"),
                        "workspaces.type",hasItems("personal"),
                        "workspaces[0].name",equalTo("My Workspace"),
                        "workspaces[0].name",is(equalTo("My Workspace")),
                        "workspaces.size()",equalTo(1)).
                log().all();
    }

    //Request: Validate extract response
    @Test
    public void validate_extract_response(){
        Response response = given().
                baseUri(baseUri).
                header(xApiKey, apiKey).
                when().
                get(path).
                then().
                assertThat().
                statusCode(200).
                extract().response();

        System.out.println("Extract response: " + response.asString());
    }

    //Request: Validate extract response with patb
    @Test
    public void validate_extract_single_value_response(){
        //Request 01 - Using Extract with Response
        Response response01 = given().
                baseUri(baseUri).
                header(xApiKey, apiKey).
                when().
                get(path).
                then().
                assertThat().
                statusCode(200).
                extract().response();

        System.out.println("Example 01 - Extract Response -> workspaces.name = "
                + response01.path("workspaces.name"));

        //Request 02 - Using JsonPath with Response
        Response response02 = given().
                baseUri(baseUri).
                header(xApiKey, apiKey).
                when().
                get(path).
                then().
                assertThat().
                statusCode(200).
                extract().response();

        JsonPath jsonPath = new JsonPath(response02.asString());

        System.out.println("Example 02 - Extract Jsonpath with Response -> workspaces.name = "
                + jsonPath.getString("workspaces.name"));


        //Request 03 - Using JsonPath with Response in String
        String response03 = given().
                baseUri(baseUri).
                header(xApiKey, apiKey).
                when().
                get(path).
                then().
                assertThat().
                statusCode(200).
                extract().response().asString();

        System.out.println("Example 03 - Extract with Response in String -> workspaces.name = "
                + jsonPath.from(response03).getString("workspaces.name"));

    }

    //Request: Validate assert using assertEquals
    @Test
    public void validate_assertEquals() {
        //Assert using assertEquals
        String response = given().
                baseUri(baseUri).
                header(xApiKey, apiKey).
                when().
                get(path).
                then().
                assertThat().
                statusCode(200).
                extract().response().path("workspaces[0].name");

        Assert.assertEquals(response, "My Workspace");

    }

    //Request: Validate assert using contains
    @Test
    public void validate_contains() {
        given().
                baseUri(baseUri).
                header(xApiKey, apiKey).
                when().
                get(path).
                then().
                assertThat().
                statusCode(200).
                body("workspaces.name", contains("My Workspace")).
                log().all();
    }

    //Request: Validate assert using containsInAnyOrder
    @Test
    public void validate_containsInAnyOrder() {
        given().
                baseUri(baseUri).
                header(xApiKey, apiKey).
                when().
                get(path).
                then().
                assertThat().
                statusCode(200).
                body("workspaces.name", containsInAnyOrder("My Workspace")).
                log().all();

    }

    //Request: Validate assert using is(not(empty())
    @Test
    public void validate_isNotEmpety() {
        given().
                baseUri(baseUri).
                header(xApiKey, apiKey).
                when().
                get(path).
                then().
                assertThat().
                statusCode(200).
                body("workspaces.name", is(not(empty()))).
                log().all();

    }

    //Request: Validate assert using isNotEmpetyArray
    @Test
    public void validate_isNotEmpetyArray() {
        given().
                baseUri(baseUri).
                header(xApiKey, apiKey).
                when().
                get(path).
                then().
                assertThat().
                statusCode(200).
                body("workspaces.name", is(not(emptyArray()))).
                log().all();

    }

    //Request: Validate assert using hasSize
    @Test
    public void validate_hasSize() {
        given().
                baseUri(baseUri).
                header(xApiKey, apiKey).
                when().
                get(path).
                then().
                assertThat().
                statusCode(200).
                body("workspaces.name", hasSize(1)).
                log().all();
    }

    //Request: Validate assert using everyItem(startsWith
    @Test
    public void validate_everyItemStartsWith() {
        given().
                baseUri(baseUri).
                header(xApiKey, apiKey).
                when().
                get(path).
                then().
                assertThat().
                statusCode(200).
                body("workspaces.name",everyItem(startsWith("My"))).
                log().all();
    }

    //Request: Validate assert using hasKey, hasValue, notEqualTo
    @Test
    public void validate_hasKey() {
        given().
                baseUri(baseUri).
                header(xApiKey, apiKey).
                when().
                get(path).
                then().
                assertThat().
                statusCode(200).
                body("workspaces[0]",hasKey("id"),
                        "workspaces[0]",hasValue("My Workspace"),
                        "workspaces[0]",not(equalTo(Collections.EMPTY_MAP))).
                log().all();
    }


    //Request: Validate assert using hasValue
    @Test
    public void validate_hasValue() {
        given().
                baseUri(baseUri).
                header(xApiKey, apiKey).
                when().
                get(path).
                then().
                assertThat().
                statusCode(200).
                body("workspaces[0]",hasValue("My Workspace")).
                log().all();
    }


    //Request: Validate assert using notEqualTo
    @Test
    public void validate_notEqualToCollectionsEmptyMap() {
        given().
                baseUri(baseUri).
                header(xApiKey, apiKey).
                when().
                get(path).
                then().
                assertThat().
                statusCode(200).
                body("workspaces[0]",not(equalTo(Collections.EMPTY_MAP))).
                log().all();
    }

    //Request: Validate assert using allOfStartsWithContainsString
    @Test
    public void validate_allOfStartsWithContainsString() {
        given().
                baseUri(baseUri).
                header(xApiKey, apiKey).
                when().
                get(path).
                then().
                assertThat().
                statusCode(200).
                body("workspaces[0].name",allOf(startsWith("My"),containsString("Workspace"))).
                log().all();
    }

    //Request: Using logs
    @Test
    public void request_response_logging() {
        given().
                baseUri(baseUri).
                header(xApiKey, apiKey).
                log().all().
                when().
                get(path).
                then().
                log().all().
                assertThat().
                statusCode(200);
//                body("workspaces[0].name",allOf(startsWith("My"),containsString("Workspace"))).
//                log().all();
    }
}
