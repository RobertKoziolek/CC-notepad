package com.robcio.ccnotepad.service;

import com.robcio.ccnotepad.model.entity.MonitoredMovie;
import com.robcio.ccnotepad.repository.MonitoredMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class MonitoredMovieService {

    @Autowired
    private MonitoredMovieRepository movieRepository;

    public void add(final String movieName) {
        if (!movieName.isEmpty() && !isMonitored(movieName)) {
            movieRepository.save(new MonitoredMovie(movieName));
        }
    }

    public Set<MonitoredMovie> getMovies() {
        return new HashSet<>((Collection<? extends MonitoredMovie>) movieRepository.findAll());
    }

    @Transactional
    public void remove(final String movieName) {
        movieRepository.deleteByMovieName(movieName);
    }

    public void remove(final Long id) {
        movieRepository.deleteById(id);
    }

    public boolean isMonitored(final String name) {
        return movieRepository.existsByMovieName(name);
    }

    public void save(final MonitoredMovie monitoredMovie) {
        movieRepository.save(monitoredMovie);
    }
}
