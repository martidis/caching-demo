package com.tasosmartidis.caching_demo.data;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
public class StubData {
    private String id;
    private String name;
    private Date lastModified;

    public void updateData() {
        name = "name changed at: " + new Date();
        lastModified = new Date();
    }

}
