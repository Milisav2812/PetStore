package petStore.REST;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

public class RESTfulRequests {

    public static Response get(String uriString, Map<String, String> params){
        Response response = RestAssured
                .given()
                .log().all()
                .pathParams(params)
                .contentType(ContentType.JSON)
                .when()
                .get(uriString)
                .then()
                .extract().response();

        return response;
    }

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

    public static Response put(String uriString, String requestBody){
        Response response = RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put(uriString)
                .then()
                .extract().response();

        return response;
    }

}
