package com.robcio.ccnotepad.scheduler;

import com.robcio.ccnotepad.enumeration.MonitoredMovieStatus;
import com.robcio.ccnotepad.model.entity.MonitoredMovie;
import com.robcio.ccnotepad.model.json.Event;
import com.robcio.ccnotepad.model.json.Movie;
import com.robcio.ccnotepad.model.json.ScheduleInfo;
import com.robcio.ccnotepad.service.CinemaApiService;
import com.robcio.ccnotepad.service.EmailService;
import com.robcio.ccnotepad.service.MonitoredMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MovieMonitorScheduler {
    @Autowired
    private EmailService emailService;
    @Autowired
    private CinemaApiService cinemaApiService;
    @Autowired
    private MonitoredMovieService monitoredMovieService;
    final Map<String, SortedSet<Date>> map = new HashMap<>();

    private void addToMap(final String movieName, final Collection<Date> eventDates) {
        if (map.containsKey(movieName)) {
            map.get(movieName).addAll(eventDates);
        } else {
            final TreeSet<Date> set = new TreeSet<>(Comparator.comparingLong(Date::getTime));
            set.addAll(eventDates);
            map.put(movieName, set);
        }
    }

    //TODO take care of expired movies
    @Scheduled(fixedRate = 12 * 60 * 60 * 1000)
    public void checkMovies() {
        final Set<MonitoredMovie> monitoredMovieSet = monitoredMovieService.getMovies()
                                                                           .stream()
                                                                           .filter(m -> m.getStatus()
                                                                                         .equals(MonitoredMovieStatus.MONITORING))
                                                                           .collect(Collectors.toSet());
        if (monitoredMovieSet.isEmpty()) return;
        final List<Date> dates = cinemaApiService.getDates();
        map.clear();

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
                    monitoredMovieService.save(monitoredMovie);
                    addToMap(movie.getName(),
                             scheduleInfo.getEvents()
                                         .stream()
                                         .filter(e -> e.getFilmId().equals(movie.getId()))
                                         .map(Event::getEventDateTime)
                                         .collect(Collectors.toSet()));
                }

            }
        }
        if (!map.isEmpty()) {
            final Set<String> phrases = monitoredMovieSet.stream()
                                                         .map(MonitoredMovie::getMovieName)
                                                         .collect(Collectors.toSet());
            emailService.sendMonitoredMoviesNotification(map, phrases);
        }
    }
}
