package com.robcio.ccnotepad.controller;

import com.robcio.ccnotepad.model.ScheduleInfo;
import com.robcio.ccnotepad.service.CinemaApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class GetMovieInfoForDateController {

    @Autowired
    private CinemaApiService cinemaApiService;

    @GetMapping("/today")
    public String getMovie() {
        final ScheduleInfo scheduleInfo = cinemaApiService.getForToday();
        return scheduleInfo.toString();
    }
}
