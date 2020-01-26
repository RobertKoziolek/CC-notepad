package com.robcio.ccnotepad.scheduler;

import com.robcio.ccnotepad.model.entity.MonitoredMovie;
import com.robcio.ccnotepad.service.CinemaApiService;
import com.robcio.ccnotepad.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class MovieMonitorScheduler {

    final private List<MonitoredMovie> monitoredMovies = new LinkedList<>();

    final private EmailService emailService;

    final private CinemaApiService cinemaApiService;

    @Autowired
    public MovieMonitorScheduler(final EmailService emailService, final CinemaApiService cinemaApiService) {
        this.emailService = emailService;
        this.cinemaApiService = cinemaApiService;
    }

    public void add(final MonitoredMovie monitoredMovie) {
        monitoredMovies.add(monitoredMovie);
    }

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void checkForMovie() {
        //check dates when there are some movies
        //for each date check movies
        //collect all of these
        //if any collected matches monitored send email
        //also create view page for monitored movies (add monitored, remove, see if there is anything)
    }
}
