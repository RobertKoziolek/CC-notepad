package com.robcio.ccnotepad.model.view;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.SortedSet;

@Getter
@AllArgsConstructor
public class ViewMovie {
    private String id;
    private String name;
    private Integer length;
    private String posterLink;
    private String videoLink;
    private String releaseYear;
    private SortedSet<ViewEventPack> eventPacks;

}
