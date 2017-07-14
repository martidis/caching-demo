package com.tasosmartidis.caching_demo.testutils;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;

public class HttpMethodsUtils {

    public static String doGetEnsure200AndReturnResponseAsString(String resource) {
        return doGetEnsure200AndReturnResponse(resource).asString();
    }

    public static Response doGetEnsure200AndReturnResponse(String resource, HttpHeader... headers) {
        RequestSpecification requestSpecification = given();

        if(headers.length>0) {

            for(HttpHeader header : headers) {
                requestSpecification.header(header.getHeaderName(), header.getHeaderValue());
            }
        }

        Response response = requestSpecification.when()
                                .get(resource)
                            .then()
                                .statusCode(HttpStatus.SC_OK)
                            .extract().response();

        return response;
    }

    public static Response doGetEnsure304AndReturnResponse(String resource, HttpHeader... headers) {
         RequestSpecification requestSpecification = given();

         for(HttpHeader header : headers) {
             requestSpecification.header(header.getHeaderName(), header.getHeaderValue());
         }

         Response response =    requestSpecification.when()
                                    .get(resource)
                                .then()
                                    .statusCode(HttpStatus.SC_NOT_MODIFIED)
                                .extract().response();

        return response;
    }

    public static String doPutEnsure200AndReturnResponseAsString(String resource) {
        Response response = when()
                                .put(resource)
                            .then()
                                .statusCode(HttpStatus.SC_OK)
                            .extract().response();

        return response.asString();
    }
}
