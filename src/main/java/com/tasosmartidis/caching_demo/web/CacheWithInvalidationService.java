package com.tasosmartidis.caching_demo.web;

import com.tasosmartidis.caching_demo.data.StubData;
import com.tasosmartidis.caching_demo.data.StubDataDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.tasosmartidis.caching_demo.utils.LoggingUtils.logMethodEntered;

@RestController
public class CacheWithInvalidationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheWithInvalidationService.class);

    private static final String STUB_DATA_UPDATE_CACHE_ENDPOINT = "/update-cache/stubdata/{id}";
    private static final String STUB_DATA_EVICT_CACHE_ENDPOINT  = "/evict-cache/stubdata/{id}";
    private static final String INVALIDATION_CACHE_NAME         = "resource-level-cache";
    private static final String STUB_DATA_ENDPOINT              = "/stubdata/{id}";
    private static final String ID_CACHE_KEY                    = "#id";

    @Autowired
    private StubDataDao dataDao;

    @RequestMapping(value=STUB_DATA_ENDPOINT, method = RequestMethod.GET)
    @Cacheable(value = INVALIDATION_CACHE_NAME, key = ID_CACHE_KEY)
    public ResponseEntity<StubData> getResource(@PathVariable String id) {
        logMethodEntered(LOGGER);
        StubData data = dataDao.getDataEntry(id);

        return ResponseEntity.ok().body(data);
    }

    @RequestMapping(value=STUB_DATA_EVICT_CACHE_ENDPOINT, method = RequestMethod.PUT)
    @CacheEvict(value = INVALIDATION_CACHE_NAME, key = ID_CACHE_KEY)
    public ResponseEntity updateResourceAndEvictFromCache(@PathVariable String id) {
        logMethodEntered(LOGGER);
        dataDao.updateDataEntry(id);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value=STUB_DATA_UPDATE_CACHE_ENDPOINT, method = RequestMethod.PUT)
    @CachePut(value = INVALIDATION_CACHE_NAME, key = ID_CACHE_KEY)
    public ResponseEntity<StubData> updateResourceAndUpdateCache(@PathVariable String id) {
        logMethodEntered(LOGGER);
        dataDao.updateDataEntry(id);

        return ResponseEntity.ok().body(dataDao.getDataEntry(id));
    }

}
