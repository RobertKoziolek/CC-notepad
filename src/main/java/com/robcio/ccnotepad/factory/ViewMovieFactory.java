package com.robcio.ccnotepad.factory;

import com.robcio.ccnotepad.model.json.Event;
import com.robcio.ccnotepad.model.json.Movie;
import com.robcio.ccnotepad.model.view.ViewMovie;

import java.util.Set;

public class ViewMovieFactory {

    private ViewEventPackFactory eventPackFactory = new ViewEventPackFactory();

    public ViewMovie create(final Movie movie, final Set<Event> events) {

        final String id = movie.getId();
        final String name = movie.getName();
        final Integer length = movie.getLength();
        final String posterLink = movie.getPosterLink();
        final String videoLink = movie.getVideoLink();
        final Integer availableScreenings = events.size();

        return new ViewMovie(id,
                             name,
                             length,
                             posterLink,
                             videoLink,
                             availableScreenings,
                             eventPackFactory.create(events));
    }
}
