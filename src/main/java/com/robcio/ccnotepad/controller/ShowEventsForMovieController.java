package com.robcio.ccnotepad.controller;

import com.robcio.ccnotepad.model.EventInfo;
import com.robcio.ccnotepad.service.CinemaApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/events")
public class ShowEventsForMovieController {

    @Autowired
    private CinemaApiService cinemaApiService;

    @PutMapping("/{id}")
    public String showEventsFor(@PathVariable final String id, final Model model) {
        final Set<EventInfo> eventInfoSet = cinemaApiService.getEventsFor(id);
        model.addAttribute("movie", cinemaApiService.getMovie(id));
        model.addAttribute("events", eventInfoSet);
        return "bookingView";
    }
}
