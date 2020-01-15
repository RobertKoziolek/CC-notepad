package com.robcio.ccnotepad.service;

import com.robcio.ccnotepad.factory.ViewMovieFactory;
import com.robcio.ccnotepad.model.json.*;
import com.robcio.ccnotepad.model.view.ViewMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
class ViewPreparationService {

    @Autowired
    private SettingService settingService;

    Set<ViewMovie> prepareForView(final ScheduleInfo scheduleInfo) {
        final ViewMovieFactory viewMovieFactory = new ViewMovieFactory();
        final Set<Movie> movies = scheduleInfo.getFilms()
                                              .stream()
                                              .filter(this::filterAnimation)
                                              .collect(Collectors.toSet());
        return movies.stream().map(f -> {
            return viewMovieFactory.create(f, getEventsFor(scheduleInfo, f.getId()));
        }).collect(Collectors.toSet());
    }

    private boolean filterAnimation(final Movie movie) {
        return !settingService.isFilterOutAnimation() || !movie.getAttributeIds().contains("animation");
    }

    private Set<Event> getEventsFor(final ScheduleInfo scheduleInfo, final String id) {
        return scheduleInfo.getEvents()
                           .stream()
                           .filter(e -> id.equals(e.getFilmId()))
                           .collect(Collectors.toSet());
    }

    Set<FuturePoster> prepareForView(final FutureInfo futureInfo) {
        return futureInfo.getPosters();
    }
}
