package com.robcio.ccnotepad.service;

import com.robcio.ccnotepad.configuration.LinkConfiguration;
import com.robcio.ccnotepad.factory.ViewMovieFactory;
import com.robcio.ccnotepad.model.json.*;
import com.robcio.ccnotepad.model.view.ViewMovie;
import com.robcio.ccnotepad.util.DateUtils;
import com.robcio.ccnotepad.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CinemaApiService {

    final private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private SettingService settingService;
    @Autowired
    private LinkConfiguration linkConfiguration;

    private ScheduleInfo cachedScheduleInfo;

    public Set<EventInfo> getEventsFor(final String id) {
        return cachedScheduleInfo.getEvents()
                .stream()
                .filter(e -> id.equals(e.getFilmId()))
                .collect(Collectors.toSet());
    }

    public Set<ViewMovie> getScheduleForView() {
        ScheduleInfo scheduleInfo = null;
        switch (settingService.getCollectRange()) {
            case TODAY:
                scheduleInfo = getForToday();
                break;
            case TOMORROW:
                scheduleInfo = getForTomorrow();
                break;
        }
        if (scheduleInfo == null) {
            throw new IllegalArgumentException("Selected range setting not available");
        }
        return prepareForView(scheduleInfo);
    }

    private Set<ViewMovie> prepareForView(final ScheduleInfo scheduleInfo) {
        final ViewMovieFactory viewMovieFactory = new ViewMovieFactory();
        final Set<MovieInfo> movies = scheduleInfo.getFilms()
                .stream()
                .filter(this::filterAnimation)
                .collect(Collectors.toSet());
        return movies.stream().map(f -> {
            return viewMovieFactory.create(f, getEventsFor(f.getId()));
        }).collect(Collectors.toSet());
    }

    private boolean filterAnimation(final MovieInfo movieInfo) {
        return !settingService.isFilterOutAnimation() || !movieInfo.getAttributeIds().contains("animation");
    }

    private ScheduleInfo getForToday() {
        return getForDateWithCache(new Date());
    }

    private ScheduleInfo getForTomorrow() {
        final Date date = DateUtils.addDay(new Date());
        return getForDateWithCache(date);
    }

    private ScheduleInfo getForDateWithCache(final Date date) {
        final ScheduleInfo scheduleInfo = getForDate(date);
        cachedScheduleInfo = scheduleInfo;
        return scheduleInfo;
    }

    public ScheduleInfo getForDate(final Date date) {
        final String dateString = format.format(date);
        final String url = String.format(linkConfiguration.getDateLink(), dateString);
        return callRest(url);
    }

    public FutureInfo getFutureMovies() {
        final String url = linkConfiguration.getFutureLink();
        return callRest(url);
    }

    private <T> T callRest(final String url) {
        final ResponseEntity<JsonWrapper<T>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<JsonWrapper<T>>() {
                });
        try {
            return response.getBody().getBody();
        } catch (final NullPointerException e) {
            throw new IllegalStateException("Could not get a response from Cinema City", e);
        }
    }

    public Set<ViewMovie> getFutureView() {
        return null;
    }
}