package com.tasosmartidis.caching_demo.testutils;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class HttpHeader {
    private String headerName;
    private String headerValue;

}
