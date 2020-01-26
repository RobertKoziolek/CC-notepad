package com.robcio.ccnotepad.repository;

import com.robcio.ccnotepad.model.entity.MonitoredMovie;
import org.springframework.data.repository.CrudRepository;

public interface MonitoredMovieRepository extends CrudRepository<MonitoredMovie, Long> {

    boolean existsByMovieName(final String movieName);

    void deleteByMovieName(final String movieName);

}
