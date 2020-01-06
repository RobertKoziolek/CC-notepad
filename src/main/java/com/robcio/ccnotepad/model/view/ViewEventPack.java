package com.robcio.ccnotepad.model.view;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.SortedSet;

@Getter
@AllArgsConstructor
public class ViewEventPack {
    private String name;
    private SortedSet<ViewMovieEvent> events;
}
