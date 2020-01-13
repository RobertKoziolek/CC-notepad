package com.robcio.ccnotepad.controller;

import com.robcio.ccnotepad.model.json.FutureInfo;
import com.robcio.ccnotepad.service.CinemaApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/future")
public class FutureViewController {

    @Autowired
    private CinemaApiService cinemaApiService;

    @GetMapping
    public FutureInfo getFutureView(final Model model) {
//        model.addAttribute("movies", cinemaApiService.getFutureView());
//        return "futureView";
        return cinemaApiService.getFutureMovies();
    }
}
