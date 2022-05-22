package com.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class myWorkspaceGet {

    //https://reqres.in/

    //1) Create mock in Postman + 2 examples with status code 200, 503
    //2)generate key in MyWorkspace: https://web.postman.co/settings/me/api-keys
    //PMAK-628ab087ec9e1a3630a2d1be-aeab88f4fcae014bcfd702cff3341a8681A

    @Test
    public void validate_get_status_code(){
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-628ab087ec9e1a3630a2d1be-aeab88f4fcae014bcfd702cff3341a8681A").
                when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200).
                log().all();
    }

    @Test
    public void validate_get_status_body(){
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-628ab087ec9e1a3630a2d1be-aeab88f4fcae014bcfd702cff3341a8681A").
                when().
                get("/workspaces").
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
}
