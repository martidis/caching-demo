package com.tasosmartidis.caching_demo.utils;

import org.slf4j.Logger;

public class LoggingUtils {

    public static void logMethodEntered(Logger logger) {
        logger.info("Got into the method!");
    }

    public static void logResponse(String message, Logger logger) {
        logger.info(message);
        logger.info("");
    }

    public static void logExplanationMessage(String message, Logger logger) {
        logger.info(message);
    }

    public static void logMethodUnderTestStart(String methodName, Logger logger) {
        logger.info("Test for method '{}' started...", methodName);
    }

    public static void logMehodUnderTestEnd(String methodName, Logger logger) {
        logger.info("Test for method '{}' finished...", methodName);
    }
}
