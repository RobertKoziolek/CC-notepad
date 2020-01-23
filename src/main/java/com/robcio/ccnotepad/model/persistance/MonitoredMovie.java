package com.robcio.ccnotepad.model.persistance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
public class MonitoredMovie {

    @Id
    @GeneratedValue
    private Long id;
    private String movieName;
}
