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
public class EventInfo {

    private String id;
    private String filmId;
    private String cinemaId;
    private String businessDay;
    private String eventDateTime;
    private Set<String> attributeIds;
    private String bookingLink;
    private Boolean soldOut;
}
