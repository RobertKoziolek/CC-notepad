package com.robcio.ccnotepad.controller;

import com.robcio.ccnotepad.service.CinemaApiService;
import com.robcio.ccnotepad.service.SettingService;
import com.robcio.ccnotepad.util.DateUtils;
import com.robcio.ccnotepad.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

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
        model.addAttribute("filters", settingService.getFilters());
        final List<Date> dates = cinemaApiService.getDates();
        model.addAttribute("selectedDateIndex", getSelectedDateIndex(dates, settingService.getSelectedDate()));
        model.addAttribute("dates", dates);
        return "mainView";
    }

    //TODO check if it works at late hours in the day
    private int getSelectedDateIndex(final List<Date> dates, final Date selectedDate) {
        final String selectedDateString = DateUtils.shortFormat(selectedDate);
        for (int i = 0; i < dates.size(); i++) {
            if (selectedDateString.equals(DateUtils.shortFormat(dates.get(i)))){
                return i;
            }
        }
        Log.error("Selected date does not match any available date");
        return -1;
    }
}
