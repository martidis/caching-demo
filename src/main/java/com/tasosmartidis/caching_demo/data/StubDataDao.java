package com.tasosmartidis.caching_demo.data;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class StubDataDao {
    private Map<String, StubData> fakeDatabase;

    public StubDataDao() {
        fakeDatabase = new HashMap<>();
        initializeFakeDatabaseState();
    }

    public StubData getDataEntry(String id) {
        assertIdExistsInDatabaseOrThrowRuntimeException(id);

        return fakeDatabase.get(id);
    }

    public void updateDataEntry(String id) {
        assertIdExistsInDatabaseOrThrowRuntimeException(id);

        fakeDatabase.get(id).updateData();
    }

    private void assertIdExistsInDatabaseOrThrowRuntimeException(String id) {
        if(!fakeDatabase.containsKey(id)) {
            throw new RuntimeException("Id not found!");
        }
    }

    private void initializeFakeDatabaseState() {
        String idOne   = "1";
        String idTwo   = "2";
        String idThree = "3";
        Date date = new Date();

        StubData entryOne = StubData.builder().id(idOne).name("Kent").lastModified(date).build();
        StubData entryTwo = StubData.builder().id(idTwo).name( "Martin").lastModified(date).build();
        StubData entryThree = StubData.builder().id(idThree).name("Bob").lastModified(date).build();

        fakeDatabase.put(idOne, entryOne);
        fakeDatabase.put(idTwo, entryTwo);
        fakeDatabase.put(idThree, entryThree);
    }
}
