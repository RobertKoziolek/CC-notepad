package com.robcio.ccnotepad.controller;

import com.robcio.ccnotepad.scheduler.MovieMonitorScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private MovieMonitorScheduler monitorScheduler;

    @GetMapping("/runScheduler")
    public String runScheduler() {
        monitorScheduler.checkMovies();
        return "redirect:/";
    }

}
