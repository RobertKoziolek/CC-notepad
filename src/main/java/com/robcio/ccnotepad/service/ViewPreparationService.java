package com.robcio.ccnotepad.service;

import com.robcio.ccnotepad.factory.ViewMovieFactory;
import com.robcio.ccnotepad.model.json.*;
import com.robcio.ccnotepad.model.view.ViewMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
class ViewPreparationService {

    @Autowired
    private SettingService settingService;

    Set<ViewMovie> prepareForView(final ScheduleInfo scheduleInfo) {
        final ViewMovieFactory viewMovieFactory = new ViewMovieFactory();
        final Set<ViewMovie> movies = new TreeSet<>(Comparator.comparing(ViewMovie::getName));

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

    List<FuturePoster> prepareForView(final FutureInfo futureInfo) {
        final LinkedList<FuturePoster> list = new LinkedList<>(futureInfo.getPosters());
        list.sort(Comparator.comparing(FuturePoster::getName));
        return list;
    }

    public List<Date> prepareForView(final DaysInfo daysInfo) {
        final LinkedList<Date> list = new LinkedList<>(daysInfo.getDates());
        list.sort(Comparator.comparingLong(Date::getTime));
        return list;
    }
}
