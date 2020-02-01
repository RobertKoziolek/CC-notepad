package com.robcio.ccnotepad.model.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Event {

    private String id;
    private String filmId;
    private String cinemaId;
    private String businessDay;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Europe/Warsaw")
    private Date eventDateTime;
    private Set<String> attributeIds;
    private String bookingLink;
    private Boolean soldOut;
}
