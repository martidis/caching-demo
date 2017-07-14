package com.tasosmartidis.caching_demo.web;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CacheWithInvalidationServiceTest.class,
        StateBasedCachedServiceTest.class,
        TimeBasedCachedServiceTest.class})
public class CachingTestSuite {

}
