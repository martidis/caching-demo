package com.tasosmartidis.caching_demo.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.tasosmartidis.caching_demo.testutils.HttpMethodsUtils.doGetEnsure200AndReturnResponseAsString;
import static com.tasosmartidis.caching_demo.utils.LoggingUtils.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TimeBasedCachedServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeBasedCachedServiceTest.class);
    private static final String HELLO_WORLD_RESOURCE = "helloworld";
    private static final String HELLO_INPUT_RESOURCE = "hello/";

    @Test
    public void sayHello() throws Exception {
        logMethodUnderTestStart("Hello World",LOGGER);

        logExplanationMessage("Should get into the method!", LOGGER);
        doGetHelloService(HELLO_WORLD_RESOURCE);
        Thread.sleep(5000);

        logExplanationMessage("Should use cache response now!", LOGGER);
        doGetHelloService(HELLO_WORLD_RESOURCE);
        Thread.sleep(6000);

        logExplanationMessage("Should get into the method again!", LOGGER);
        doGetHelloService(HELLO_WORLD_RESOURCE);

        logMehodUnderTestEnd("Hello World", LOGGER);
    }


    @Test
    public void sayHelloByName() throws Exception {
        logMethodUnderTestStart("Hello {input}", LOGGER);

        logExplanationMessage("Should get into method now!", LOGGER);
        doGetHelloService(HELLO_INPUT_RESOURCE + "Kent");

        logExplanationMessage("Should use cache response now!", LOGGER);
        doGetHelloService(HELLO_INPUT_RESOURCE + "Kent");

        logExplanationMessage("Should get into the method again!", LOGGER);
        doGetHelloService( HELLO_INPUT_RESOURCE + "Martin");

        logMehodUnderTestEnd("Hello {input}", LOGGER);
    }

    private void doGetHelloService(String resource) {
        String result = doGetEnsure200AndReturnResponseAsString(resource);
        logResponse(result, LOGGER);
    }

}