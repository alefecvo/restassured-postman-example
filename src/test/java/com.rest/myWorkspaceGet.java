package com.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;

public class myWorkspaceGet {

    //https://reqres.in/

    //1) Create mock in Postman + 2 examples with status code 200, 503
    //2)generate key in MyWorkspace: https://web.postman.co/settings/me/api-keys
    //PMAK-628ab087ec9e1a3630a2d1be-aeab88f4fcae014bcfd702cff3341a8681A

    String baseUri = "https://api.postman.com";
    String xApiKey = "X-Api-Key";
    String apiKey = "PMAK-628ab087ec9e1a3630a2d1be-aeab88f4fcae014bcfd702cff3341a8681A";
    String path = "/workspaces";
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
}
