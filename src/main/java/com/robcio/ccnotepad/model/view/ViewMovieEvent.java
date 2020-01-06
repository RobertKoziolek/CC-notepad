package com.robcio.ccnotepad.model.view;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class ViewMovieEvent {
    private String eventDateTime;
    private String bookingLink;
    private Set<String> attributeIds;
}
