package com.robcio.ccnotepad.scheduler;

import com.robcio.ccnotepad.enumeration.MonitoredMovieStatus;
import com.robcio.ccnotepad.model.entity.MonitoredMovie;
import com.robcio.ccnotepad.model.json.Movie;
import com.robcio.ccnotepad.model.json.ScheduleInfo;
import com.robcio.ccnotepad.service.CinemaApiService;
import com.robcio.ccnotepad.service.EmailService;
import com.robcio.ccnotepad.service.MonitoredMovieService;
import com.robcio.ccnotepad.util.DateUtils;
import com.robcio.ccnotepad.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MovieMonitorScheduler {
    @Autowired
    private EmailService emailService;
    @Autowired
    private CinemaApiService cinemaApiService;
    @Autowired
    private MonitoredMovieService monitoredMovieService;

    //TODO take care of expired movies
//    @Scheduled(fixedRate = 12 * 60 * 60 * 1000)
//    @Scheduled(fixedRate = 30 * 1000)
    public void checkMovies() {
        final StringBuilder stringBuilder = new StringBuilder();
        boolean foundFlag = false;

        final List<Date> dates = cinemaApiService.getDates();
        final Set<MonitoredMovie> monitoredMovieSet = monitoredMovieService.getMovies()
                                                                           .stream()
                                                                           .filter(m -> m.getStatus()
                                                                                         .equals(MonitoredMovieStatus.MONITORING))
                                                                           .collect(Collectors.toSet());
        for (final Date date : dates) {
            final ScheduleInfo scheduleInfo = cinemaApiService.getForDate(date);
            for (final Movie movie : scheduleInfo.getFilms()) {
                final String name = movie.getName().toLowerCase();
                final Optional<MonitoredMovie> any = monitoredMovieSet.stream()
                                                                      .filter(m -> name.contains(m.getMovieName()
                                                                                                  .toLowerCase()))
                                                                      .findAny();
                if (any.isPresent()) {
                    final MonitoredMovie monitoredMovie = any.get();
                    monitoredMovie.setFilmId(movie.getId());
                    monitoredMovie.setStatus(MonitoredMovieStatus.FOUND);
//                    monitoredMovieService.save(monitoredMovie);//TODO has to remember it found it
                    Log.error("Found for {} and has {} events for date {}",
                              monitoredMovie.getMovieName(),
                              scheduleInfo.getEvents()
                                          .stream()
                                          .filter(e -> e.getFilmId().equals(movie.getId()))
                                          .count(), DateUtils.shortFormat(date));
                    //TODO better mail pls
                    stringBuilder.append(String.format("Found for %s and it has %d events on %s \n",
                                                       monitoredMovie.getMovieName(),
                                                       scheduleInfo.getEvents()
                                                                   .stream()
                                                                   .filter(e -> e.getFilmId().equals(movie.getId()))
                                                                   .count(),
                                                       DateUtils.shortFormat(date)));
                    foundFlag = true;

                    monitoredMovieSet.remove(monitoredMovie);
                }

            }
        }
//        if (foundFlag) {
//            emailService.sendSimpleMessage("",
//                                           "Movies you wanted are scheduled in cinemas",
//                                           stringBuilder.toString());
//        }
        //check dates when there are some movies
        //for each date check movies
        //collect all of these
        //if any collected matches monitored send email
        //also create view page for monitored movies (add monitored, remove, see if there is anything)
    }
}
