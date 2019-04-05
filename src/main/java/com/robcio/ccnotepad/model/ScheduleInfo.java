package com.robcio.ccnotepad.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleInfo {

    private Set<MovieInfo> films;
    private Set<EventInfo> events;

}
