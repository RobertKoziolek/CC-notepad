package com.robcio.ccnotepad.model.entity;

import com.robcio.ccnotepad.enumeration.MonitoredMovieStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MonitoredMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String movieName;
    private MonitoredMovieStatus status;
    private String filmId;

    public MonitoredMovie(final String movieName) {
        this.movieName = movieName;
        this.status = MonitoredMovieStatus.MONITORING;
    }
}
