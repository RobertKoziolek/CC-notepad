package com.robcio.ccnotepad.controller;

import com.robcio.ccnotepad.enumeration.CollectRange;
import com.robcio.ccnotepad.service.CinemaApiService;
import com.robcio.ccnotepad.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PosterViewController {

    @Autowired
    private CinemaApiService cinemaApiService;
    @Autowired
    private SettingService settingService;

    @GetMapping
    public String getMainView(final Model model) {
        model.addAttribute("movies", cinemaApiService.getScheduleForView());
        model.addAttribute("rangeTypes", CollectRange.values());
        model.addAttribute("selectedRange", settingService.getCollectRange());
        model.addAttribute("filterOutAnimation", settingService.isFilterOutAnimation());
        return "mainView";
    }
}
