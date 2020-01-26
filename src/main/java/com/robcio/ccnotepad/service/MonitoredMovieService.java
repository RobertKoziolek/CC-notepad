package com.robcio.ccnotepad.service;

import com.robcio.ccnotepad.model.entity.MonitoredMovie;
import com.robcio.ccnotepad.repository.MonitoredMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class MonitoredMovieService {

    @Autowired
    private MonitoredMovieRepository movieRepository;

    public void add(final MonitoredMovie monitoredMovie) {
        movieRepository.save(monitoredMovie);
    }

    public Set<MonitoredMovie> getMovies() {
        return new HashSet<>((Collection<? extends MonitoredMovie>) movieRepository.findAll());
    }

    public void remove(final Long id) {
        movieRepository.deleteById(id);
    }
}
