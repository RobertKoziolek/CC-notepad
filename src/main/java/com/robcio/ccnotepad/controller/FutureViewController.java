package com.robcio.ccnotepad.controller;

import com.robcio.ccnotepad.service.CinemaApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/future")
public class FutureViewController {

    @Autowired
    private CinemaApiService cinemaApiService;

    @GetMapping
    public String getFutureView(final Model model) {
        model.addAttribute("movies", cinemaApiService.getFutureMoviesForView());
        return "futureView";
    }
}
