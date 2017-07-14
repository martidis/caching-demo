package com.tasosmartidis.caching_demo.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HttpUtils {

    private static final String IF_MODIFIED_SINCE_HEADER = "if-modified-since";
    private static final String HEADER_DATE_PATTERN      = "EEE, dd MMM yyyy HH:mm:ss zzz";
    private static final String ETAG_HEADER              = "etag";

    public static ResponseEntity make304NotModifiedResponse() {
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    public static Date parseIfModifiedSinceHeaderToDate(HttpServletRequest request) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat(HEADER_DATE_PATTERN);
        return format.parse(request.getHeader(IF_MODIFIED_SINCE_HEADER));
    }

    public static boolean modifiedSinceHeaderPresent(HttpServletRequest request) {
        return request.getHeader(IF_MODIFIED_SINCE_HEADER)!=null;
    }

    public static boolean checkIfEtagHeaderPresent(HttpServletRequest request) {
        return request.getHeader(ETAG_HEADER)!=null;
    }

    public static String parseEtagHeader(HttpServletRequest request) throws ParseException {
        return request.getHeader(ETAG_HEADER).replace("\"","");
    }
}
