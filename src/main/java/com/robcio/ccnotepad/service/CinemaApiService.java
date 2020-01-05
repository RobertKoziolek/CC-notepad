package com.robcio.ccnotepad.service;

import com.robcio.ccnotepad.enumeration.CollectRange;
import com.robcio.ccnotepad.model.*;
import com.robcio.ccnotepad.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    @Autowired
    private SettingService settingService;
    private ScheduleInfo cachedScheduleInfo;

    public Set<EventInfo> getEventsFor(final String id) {
        final Set<EventInfo> eventInfoSet = cachedScheduleInfo.getEvents()
                                                              .stream()
                                                              .filter(e -> id.equals(e.getFilmId()))
                                                              .collect(
                                                                      Collectors.toSet());
        Log.debug("Event for the movie {}: {}", id, eventInfoSet.toString());
        return eventInfoSet;
    }

    public MovieInfo getMovie(final String id) {
        final Optional<MovieInfo> movieInfoOptional = cachedScheduleInfo.getFilms()
                                                                        .stream()
                                                                        .filter(f -> id.equals(f.getId()))
                                                                        .findFirst();
        if (movieInfoOptional.isPresent()) {
            return movieInfoOptional.get();
        } else {
            Log.error("Could not find movie for id: {}", id);
            throw new IllegalArgumentException("Could not find the movie");
        }
    }

    public Set<MovieForView> getScheduleForView() {
        ScheduleInfo scheduleInfo = null;
        switch (settingService.getSetting(CollectRange.class)) {
            case TODAY:
                scheduleInfo = getForToday();
                break;
            case TOMORROW:
                scheduleInfo = getForTomorrow();
                break;
            case TODAY_AND_TOMORROW:
                scheduleInfo = getForTodayAndTomorrow();
                break;
        }
        if (scheduleInfo == null) {
            throw new IllegalArgumentException("Selected range setting not available");
        }
        return prepareForView(scheduleInfo);
    }

    private Set<MovieForView> prepareForView(final ScheduleInfo scheduleInfo) {
        final Set<MovieInfo> films = scheduleInfo.getFilms();
        return films.stream().map(f -> {
            return new MovieForView(f, getEventsFor(f.getId()));
        }).collect(Collectors.toSet());
    }

    private ScheduleInfo getForTodayAndTomorrow() {
        final ScheduleInfo forToday = getForToday();
        final ScheduleInfo forTomorrow = getForTomorrow();
        final Set<String> todayIds = forToday.getFilms().stream().map(MovieInfo::getId).collect(Collectors.toSet());
        final Set<MovieInfo> newFilms = forTomorrow.getFilms()
                                                   .stream()
                                                   .filter(f -> !todayIds.contains(f.getId()))
                                                   .collect(Collectors.toSet());
        forToday.getFilms().addAll(newFilms);
        forToday.getEvents().addAll(forTomorrow.getEvents());
        return forToday;
    }

    public ScheduleInfo getForToday() {
        return getForDate(new Date());
    }

    private ScheduleInfo getForTomorrow() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
        return getForDate(calendar.getTime());
    }

    private ScheduleInfo getForDate(final Date date) {
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
            throw new IllegalStateException("Could not get a response from Cinema City", e);
        }
    }
}