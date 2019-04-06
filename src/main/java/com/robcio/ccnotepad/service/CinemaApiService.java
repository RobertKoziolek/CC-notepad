package com.robcio.ccnotepad.service;

import com.robcio.ccnotepad.model.JsonWrapper;
import com.robcio.ccnotepad.model.ScheduleInfo;
import com.robcio.ccnotepad.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CinemaApiService {
    private final String urlFormatString = "https://www.cinema-city.pl/pl/data-api-service/v1/quickbook/10103/film-events/in-cinema/1087/at-date/%s?attr=&lang=pl_PL";
    final private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private RestTemplate restTemplate;

    public ScheduleInfo getForDate(final Date date) {
        final String dateString = format.format(date);
        Log.debug("Getting info for {}", dateString);
        final String url = String.format(urlFormatString, dateString);
        final ResponseEntity<JsonWrapper> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<JsonWrapper>() {
                });
        try {
            final ScheduleInfo scheduleInfo = response.getBody().getBody();
            fixUrls(scheduleInfo);

            return scheduleInfo;
        } catch (NullPointerException e) {
            Log.error("Could not get the schedule info for {}", dateString);
            throw e;
        }
    }

    private void fixUrls(final ScheduleInfo scheduleInfo) {
        final String ccLink = "https://www.cinema-city.pl%s";
        scheduleInfo.getFilms().forEach(film -> {
            film.setPosterLink(String.format(ccLink, film.getPosterLink()));
            film.setLink(String.format(ccLink, film.getLink()));
        });
        scheduleInfo.getEvents().forEach(event -> {
            event.setBookingLink(String.format(ccLink, event.getBookingLink()));
        });
    }

    public ScheduleInfo getForToday() {
        return getForDate(new Date());
    }
}
