package com.robcio.ccnotepad.service;

import com.robcio.ccnotepad.factory.ViewMovieFactory;
import com.robcio.ccnotepad.model.json.*;
import com.robcio.ccnotepad.model.view.ViewMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
class ViewPreparationService {

    @Autowired
    private SettingService settingService;

    Set<ViewMovie> prepareForView(final ScheduleInfo scheduleInfo) {
        final ViewMovieFactory viewMovieFactory = new ViewMovieFactory();
        final Set<ViewMovie> movies = new HashSet<>();

        final Set<String> filteredAttributes = settingService.getFilters()
                                                             .entrySet()
                                                             .stream()
                                                             .filter(e -> !e.getValue())
                                                             .map(e -> e.getKey().getAttribute())
                                                             .collect(Collectors.toSet());
        for (final Movie movie : scheduleInfo.getFilms()) {
            final Set<Event> movieEvents = scheduleInfo.getEvents()
                                                       .stream()
                                                       .filter(event -> movie.getId()
                                                                             .equals(event.getFilmId())
                                                               && Collections.disjoint(
                                                               event.getAttributeIds(),
                                                               filteredAttributes)).collect(Collectors.toSet());
            if (!movieEvents.isEmpty()) {
                movies.add(viewMovieFactory.create(movie, movieEvents));
            }
        }
        return movies;
    }

    private boolean filter(final Movie movie) {
//        final Map<Filter, Boolean> filters = settingService.getFilters();
//        filters.entrySet().stream().filter(Map.Entry::getValue).map(e -> e.getKey().getAttribute()).
        //TODO filtering should be done on events?
        return true;
//        return !settingService.isFilterOutAnimation() || !movie.getAttributeIds().contains("animation");
    }

    private Set<Event> getEventsFor(final ScheduleInfo scheduleInfo, final Movie movie) {
        return scheduleInfo.getEvents()
                           .stream()
                           .filter(e -> movie.getId().equals(e.getFilmId()))
                           .collect(Collectors.toSet());
    }

    Set<FuturePoster> prepareForView(final FutureInfo futureInfo) {
        return futureInfo.getPosters();
    }
}
