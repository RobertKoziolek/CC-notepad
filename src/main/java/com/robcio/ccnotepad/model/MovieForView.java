package com.robcio.ccnotepad.model;

import lombok.Getter;

import java.util.Set;

@Getter
public class MovieForView {
    private String id;
    private String name;
    private Integer length;
    private String posterLink;
    private String videoLink;
    private String releaseYear;
    private Set<EventInfo> events;

    public MovieForView(final MovieInfo movieInfo, final Set<EventInfo> events) {
        id = movieInfo.getId();
        name = movieInfo.getName();
        length = movieInfo.getLength();
        posterLink = movieInfo.getPosterLink();
        videoLink = movieInfo.getVideoLink();
        releaseYear = movieInfo.getReleaseYear();
        this.events = events;
    }
}
