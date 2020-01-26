package com.robcio.ccnotepad.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class MonitorService {

    public Set<String> getMovies() {
        return new HashSet<>(Arrays.asList("Psy 9", "Iron Man 9", "Imie Wiatru"));
    }
}
