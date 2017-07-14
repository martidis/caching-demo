package com.tasosmartidis.caching_demo.web;

import com.tasosmartidis.caching_demo.data.StubData;
import com.tasosmartidis.caching_demo.data.StubDataDao;
import com.tasosmartidis.caching_demo.utils.LoggingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;

import static com.tasosmartidis.caching_demo.utils.HttpUtils.*;

@RestController
public class StateBasedCachedService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StateBasedCachedService.class);

    private static final String STATE_BASED_BASE_ENDPOINT            = "/stubdata";
    private static final String STATE_BASED_UPDATE_RESOURCE_ENDPOINT = STATE_BASED_BASE_ENDPOINT+"/{id}";
    private static final String LAST_MODIFIED_ENDPOINT               = STATE_BASED_BASE_ENDPOINT + "/lastmodified/{id}";
    private static final String ETAG_ENDPOINT                        = STATE_BASED_BASE_ENDPOINT + "/etag/{id}";

    @Autowired
    private StubDataDao dataDao;

    @RequestMapping(value=LAST_MODIFIED_ENDPOINT, method = RequestMethod.GET)
    public ResponseEntity<StubData> getResourceWithLastModified(@PathVariable String id, HttpServletRequest request) throws ParseException {
        StubData data = dataDao.getDataEntry(id);

        if(modifiedSinceHeaderPresent(request)) {
            Date modifiedSinceHeaderDate = parseIfModifiedSinceHeaderToDate(request);

            if(modifiedSinceHeaderDate.equals(data.getLastModified())) {
                LoggingUtils.logExplanationMessage("Resource not changed, sending back 304!", LOGGER);
                return make304NotModifiedResponse();
            }
        }

        return ResponseEntity.ok()
                .cacheControl(CacheControl.empty()).lastModified(data.getLastModified().getTime())
                .body(data);
    }

    @RequestMapping(value=ETAG_ENDPOINT, method = RequestMethod.GET)
    public ResponseEntity<StubData> getResourceWithEtag(@PathVariable String id, HttpServletRequest request) throws ParseException {
        StubData data = dataDao.getDataEntry(id);

        if(checkIfEtagHeaderPresent(request)) {
            String etagSent = parseEtagHeader(request);

            if(etagSent.equals(String.valueOf(data.hashCode()))) {
                LoggingUtils.logExplanationMessage("Resource not changed, sending back 304!", LOGGER);
                return make304NotModifiedResponse();
            }
        }

        return ResponseEntity.ok()
                .cacheControl(CacheControl.empty()).eTag(String.valueOf(data.hashCode()))
                .body(data);
    }



    @RequestMapping(value=STATE_BASED_UPDATE_RESOURCE_ENDPOINT, produces = "application/json", method = RequestMethod.PUT)
    public ResponseEntity updateResource(@PathVariable String id) {
        dataDao.updateDataEntry(id);

        return ResponseEntity.ok().build();
    }

}
