package com.tasosmartidis.caching_demo.web;

import org.slf4j.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.tasosmartidis.caching_demo.utils.LoggingUtils.logMethodEntered;

@RestController
public class TimeBasedCachedService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeBasedCachedService.class);

    private static final String HELLO_INPUT_NAME_ENDPOINT = "/hello/{name}";
    private static final String HELLO_WORLD_ENDPOINT      = "/helloworld";
    private static final String HELLO_SHORT_CACHE         = "time-based-short-lived";
    private static final String HELLO_LONG_CACHE          = "time-based-long-lived";
    private static final String NAME_CACHE_KEY            = "#name";

    @RequestMapping(value= HELLO_WORLD_ENDPOINT, method = RequestMethod.GET)
    @Cacheable(HELLO_SHORT_CACHE)
    public ResponseEntity<String> sayHelloWorld() {
        logMethodEntered(LOGGER);

        return ResponseEntity.ok().body("Hello World!");
    }

    @RequestMapping(value= HELLO_INPUT_NAME_ENDPOINT, method = RequestMethod.GET)
    @Cacheable(value = HELLO_LONG_CACHE, key = NAME_CACHE_KEY)
    public ResponseEntity<String> sayHelloByName(@PathVariable String name) {
        logMethodEntered(LOGGER);

       return  ResponseEntity.ok().body("Hello " + name + "!");
    }



}
