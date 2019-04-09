package com.robcio.ccnotepad.service;

import com.robcio.ccnotepad.model.EventInfo;
import com.robcio.ccnotepad.model.JsonWrapper;
import com.robcio.ccnotepad.model.MovieInfo;
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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CinemaApiService {
    private final String urlFormatString = "https://www.cinema-city.pl/pl/data-api-service/v1/quickbook/10103/film-events/in-cinema/1087/at-date/%s?attr=&lang=pl_PL";
    final private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private RestTemplate restTemplate;
    private ScheduleInfo cachedScheduleInfo;

    public ScheduleInfo getForDate(final Date date) {
        final String dateString = format.format(date);
        Log.debug(this.getClass(), "Getting info for {}", dateString);
        final String url = String.format(urlFormatString, dateString);
        final ResponseEntity<JsonWrapper> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<JsonWrapper>() {
                });
        try {
            final ScheduleInfo scheduleInfo = response.getBody().getBody();
            cachedScheduleInfo = scheduleInfo;
            return scheduleInfo;
        } catch (NullPointerException e) {
            Log.error(this.getClass(), "Could not get the schedule info for {}", dateString);
            throw e;
        }
    }

    public ScheduleInfo getForToday() {
        return getForDate(new Date());
    }

    public Set<EventInfo> getEventsFor(final String id) {
        final Set<EventInfo> eventInfoSet = cachedScheduleInfo.getEvents().stream().filter(e -> id.equals(e.getFilmId())).collect(Collectors.toSet());
        Log.debug("Event for the movie {}: {}", id, eventInfoSet.toString());
        return eventInfoSet;
    }

    public MovieInfo getMovie(final String id) {
        final Optional<MovieInfo> movieInfoOptional = cachedScheduleInfo.getFilms().stream().filter(f -> id.equals(f.getId())).findFirst();
        if (movieInfoOptional.isPresent()) {
            return movieInfoOptional.get();
        } else {
            Log.error("Could not find movie for id: {}", id);
            throw new IllegalArgumentException("Could not find the movie");
        }
    }
}