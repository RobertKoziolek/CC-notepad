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
public class Movie {

    private String id;
    private String name;
    private Integer length;
    private String posterLink;
    private String videoLink;
    private String link;
    private Integer weight;
    private String releaseYear;
    private Set<String> attributeIds;

}
