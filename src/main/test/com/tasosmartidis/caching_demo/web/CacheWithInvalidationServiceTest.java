package com.tasosmartidis.caching_demo.web;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.tasosmartidis.caching_demo.testutils.HttpMethodsUtils.doGetEnsure200AndReturnResponseAsString;
import static com.tasosmartidis.caching_demo.testutils.HttpMethodsUtils.doPutEnsure200AndReturnResponseAsString;
import static com.tasosmartidis.caching_demo.utils.LoggingUtils.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CacheWithInvalidationServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheWithInvalidationServiceTest.class);

    private static final String STUB_DATA_UPDATE_CACHE_ENDPOINT = "/update-cache/stubdata/1";
    private static final String STUB_DATA_EVICT_CACHE_ENDPOINT  = "/evict-cache/stubdata/1";
    private static final String STUB_DATA_ENDPOINT              = "/stubdata/1";

    @Test
    public void testCacheEviction() throws Exception {
        logMethodUnderTestStart("cache-eviction", LOGGER);

        logExplanationMessage("Should get in method for the resource!", LOGGER);
        doGetStubDataService(STUB_DATA_ENDPOINT);

        logExplanationMessage("Should use cache response now!", LOGGER);
        doGetStubDataService(STUB_DATA_ENDPOINT);

        logExplanationMessage("Updating resource! Clear resource from cache!", LOGGER);
        doPutStubDataService(STUB_DATA_EVICT_CACHE_ENDPOINT);

        logExplanationMessage("Should use updated cache response now!", LOGGER);
        doGetStubDataService(STUB_DATA_ENDPOINT);

        logMehodUnderTestEnd("cache-eviction", LOGGER);
    }

    @Test
    public void testCacheUpdate() throws Exception {
        logMethodUnderTestStart("cache-update", LOGGER);

        logExplanationMessage("Should get in the method for response now!", LOGGER);
        doGetStubDataService(STUB_DATA_ENDPOINT);

        logExplanationMessage("Should use cache response now!", LOGGER);
        doGetStubDataService(STUB_DATA_ENDPOINT);

        logExplanationMessage("Updating resource! Update resource from cache!", LOGGER);
        doPutStubDataService(STUB_DATA_UPDATE_CACHE_ENDPOINT);

        logExplanationMessage("Cache should serve updated response now!", LOGGER);
        doGetStubDataService(STUB_DATA_ENDPOINT);

        logMehodUnderTestEnd("cache-update", LOGGER);
    }

    private void doGetStubDataService(String endpoint) {
        String result = doGetEnsure200AndReturnResponseAsString(endpoint);
        logResponse(result, LOGGER);
    }

    private void doPutStubDataService(String endpoint) {
        String result = doPutEnsure200AndReturnResponseAsString(endpoint);
        logResponse(result, LOGGER);
    }
}
