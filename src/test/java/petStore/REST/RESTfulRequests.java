package petStore.REST;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RESTfulRequests {
    public static Response post(String uriString, String requestBody){
        Response response = RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post(uriString)
                .then()
                .extract().response();

        return response;
    }

}
