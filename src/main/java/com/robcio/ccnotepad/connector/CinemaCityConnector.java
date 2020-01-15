package com.robcio.ccnotepad.connector;

import com.robcio.ccnotepad.configuration.LinkConfiguration;
import com.robcio.ccnotepad.model.json.BodyInfo;
import com.robcio.ccnotepad.model.json.FutureInfo;
import com.robcio.ccnotepad.model.json.JsonWrapper;
import com.robcio.ccnotepad.model.json.ScheduleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CinemaCityConnector {

    final private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LinkConfiguration linkConfiguration;


    public ScheduleInfo getFor(final Date date) {
        final String dateString = format.format(date);
        final String url = String.format(linkConfiguration.getDateLink(), dateString);
        return callRest(url, new ParameterizedTypeReference<JsonWrapper<ScheduleInfo>>() {
        });
    }

    public FutureInfo getFuture() {
        final String url = linkConfiguration.getFutureLink();
        return callRest(url, new ParameterizedTypeReference<JsonWrapper<FutureInfo>>() {
        });
    }

    private <T extends BodyInfo> T callRest(final String url,
                                            final ParameterizedTypeReference<JsonWrapper<T>> parameterizedTypeReference) {
        final ResponseEntity<JsonWrapper<T>> response = restTemplate.exchange(url,
                                                                              HttpMethod.GET,
                                                                              null,
                                                                              parameterizedTypeReference);
        try {
            return response.getBody().getBody();
        } catch (final NullPointerException e) {
            throw new IllegalStateException("Could not get a response from Cinema City", e);
        }
    }
}
