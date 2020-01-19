package com.robcio.ccnotepad.connector;

import com.robcio.ccnotepad.configuration.LinkConfiguration;
import com.robcio.ccnotepad.model.json.*;
import com.robcio.ccnotepad.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Component
public class CinemaCityConnector {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LinkConfiguration linkConfiguration;

    public ScheduleInfo getFor(final Date date) {
        final String dateString = DateUtils.format(date);
        final String url = String.format(linkConfiguration.getDateLink(), dateString);
        return callRest(url, new ParameterizedTypeReference<JsonWrapper<ScheduleInfo>>() {
        });
    }

    public FutureInfo getFuture() {
        final String url = linkConfiguration.getFutureLink();
        return callRest(url, new ParameterizedTypeReference<JsonWrapper<FutureInfo>>() {
        });
    }

    public DaysInfo getDays() {
        final String dateString = DateUtils.format(DateUtils.addYear(new Date()));
        final String url = String.format(linkConfiguration.getDatesLink(), dateString);
        return callRest(url, new ParameterizedTypeReference<JsonWrapper<DaysInfo>>() {
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
