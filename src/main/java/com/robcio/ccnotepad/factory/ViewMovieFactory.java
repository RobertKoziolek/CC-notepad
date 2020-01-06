package com.robcio.ccnotepad.factory;

import com.robcio.ccnotepad.model.json.EventInfo;
import com.robcio.ccnotepad.model.json.MovieInfo;
import com.robcio.ccnotepad.model.view.ViewMovie;

import java.util.Set;

public class ViewMovieFactory {

    private ViewEventPackFactory eventPackFactory = new ViewEventPackFactory();

    public ViewMovie create(final MovieInfo movieInfo, final Set<EventInfo> events) {

        final String id = movieInfo.getId();
        final String name = movieInfo.getName();
        final Integer length = movieInfo.getLength();
        final String posterLink = movieInfo.getPosterLink();
        final String videoLink = movieInfo.getVideoLink();
        final String releaseYear = movieInfo.getReleaseYear();

        return new ViewMovie(id, name, length, posterLink, videoLink, releaseYear, eventPackFactory.create(events));
    }
}
