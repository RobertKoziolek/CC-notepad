package com.robcio.ccnotepad.service;

import com.robcio.ccnotepad.factory.ViewMovieFactory;
import com.robcio.ccnotepad.model.json.EventInfo;
import com.robcio.ccnotepad.model.json.JsonWrapper;
import com.robcio.ccnotepad.model.json.MovieInfo;
import com.robcio.ccnotepad.model.json.ScheduleInfo;
import com.robcio.ccnotepad.model.view.ViewMovie;
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
            case TODAY_AND_TOMORROW:
                scheduleInfo = getForTodayAndTomorrow();
                break;
        }
        if (scheduleInfo == null) {
            throw new IllegalArgumentException("Selected range setting not available");
        }
        return prepareForView(scheduleInfo);
    }

    private Set<ViewMovie> prepareForView(final ScheduleInfo scheduleInfo) {
        final ViewMovieFactory viewMovieFactory = new ViewMovieFactory();
        final Set<MovieInfo> films = scheduleInfo.getFilms()
                                                 .stream()
                                                 .filter(this::filterAnimation)
                                                 .collect(Collectors.toSet());
        return films.stream().map(f -> {
            return viewMovieFactory.create(f, getEventsFor(f.getId()));
        }).collect(Collectors.toSet());
    }

    private boolean filterAnimation(final MovieInfo movieInfo) {
        return !settingService.isFilterOutAnimation() || !movieInfo.getAttributeIds().contains("animation");
    }

    //TODO bugged, takes only for tomorrow
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