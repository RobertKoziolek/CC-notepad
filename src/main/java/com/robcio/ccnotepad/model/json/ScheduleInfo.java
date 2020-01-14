package com.robcio.ccnotepad.model.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleInfo implements BodyInfo {

    private Set<Movie> films;
    private Set<Event> events;

}
