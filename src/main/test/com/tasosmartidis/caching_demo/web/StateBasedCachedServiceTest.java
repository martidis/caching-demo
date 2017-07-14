package com.tasosmartidis.caching_demo.web;


import com.jayway.restassured.response.Response;
import com.tasosmartidis.caching_demo.testutils.HttpHeader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.tasosmartidis.caching_demo.testutils.HttpMethodsUtils.*;
import static com.tasosmartidis.caching_demo.utils.LoggingUtils.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
public class StateBasedCachedServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(StateBasedCachedServiceTest.class);
    private static String STUB_DATA_RESOURCE_LAST_MODIFIED = "stubdata/lastmodified/1";
    private static String STUB_DATA_RESOURCE_ETAG          = "stubdata/etag/1";
    private static String STUB_DATA_UPDATE_RESOURCE        = "stubdata/1";

    @Test
    public void testLastModifiedImplementation() {
        logMethodUnderTestStart("last-modified", LOGGER);

        logExplanationMessage("Get resource for the first time", LOGGER);
        Response response = doGetResource(STUB_DATA_RESOURCE_LAST_MODIFIED);


        logExplanationMessage("Get resource and send last-modified header should get 304", LOGGER);
        HttpHeader lastModified = extractLastModifiedHeader(response);
        doGetResourceWithLastModifiedHeaderExpect304(response);

        logExplanationMessage("Update the data", LOGGER);
        doPutResource(STUB_DATA_UPDATE_RESOURCE);

        logExplanationMessage("Get resource again and send last-modified. Should return resource", LOGGER);
        doGetResource(STUB_DATA_RESOURCE_LAST_MODIFIED, lastModified);

        logMehodUnderTestEnd("last-modified", LOGGER);
    }

    private Response doGetResourceWithLastModifiedHeaderExpect304(Response response) {
        HttpHeader httpHeader = extractLastModifiedHeader(response);
        response  = doGetEnsure304AndReturnResponse(STUB_DATA_RESOURCE_LAST_MODIFIED,httpHeader);
        logResponse(response.getStatusCode() + response.getHeaders().asList().toString(), LOGGER);

        return response;
    }

    private HttpHeader extractLastModifiedHeader(Response response) {
        String lastModifiedHeader = response.getHeader("Last-Modified");
        return HttpHeader.builder().headerName("if-modified-since").headerValue(lastModifiedHeader).build();
    }

    @Test
    public void testEtagImplementation() {
        logMethodUnderTestStart("Etag", LOGGER);

        logExplanationMessage("Get resource for the first time", LOGGER);
        Response response = doGetResource(STUB_DATA_RESOURCE_ETAG);

        logExplanationMessage("Get resource and send ETag, should get 304", LOGGER);
        doGetWithEtagExpect304(response);

        logExplanationMessage("Update the data", LOGGER);
        doPutResource(STUB_DATA_UPDATE_RESOURCE);

        logExplanationMessage("Get resource and send ETag, should get the resource", LOGGER);
        doGetResource(STUB_DATA_RESOURCE_ETAG);


        logMehodUnderTestEnd("Etag", LOGGER);
    }

    private void doGetWithEtagExpect304(Response response) {
        HttpHeader httpHeader = extractEtagHeader(response);
        response= doGetEnsure304AndReturnResponse(STUB_DATA_RESOURCE_ETAG, httpHeader);
        logResponse(response.getStatusCode() + response.getHeaders().asList().toString(), LOGGER);
    }

    private HttpHeader extractEtagHeader(Response response) {
        String  eTag = response.getHeader("ETag");
        return HttpHeader.builder().headerName("etag").headerValue(eTag).build();
    }

    private Response doGetResource(String endpoint, HttpHeader... headers) {
        Response response = doGetEnsure200AndReturnResponse(endpoint, headers);
        logResponse(response.asString(), LOGGER);
        return response;
    }

    private void doPutResource(String endpoint) {
        String responseAsString;
        responseAsString = doPutEnsure200AndReturnResponseAsString(endpoint);
        logResponse(responseAsString, LOGGER);
    }

}
