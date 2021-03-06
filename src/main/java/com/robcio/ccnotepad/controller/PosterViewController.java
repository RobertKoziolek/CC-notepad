package com.robcio.ccnotepad.controller;

import com.robcio.ccnotepad.service.CinemaApiService;
import com.robcio.ccnotepad.service.SettingService;
import com.robcio.ccnotepad.util.DateUtils;
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
    private SettingService settings;

    @GetMapping
    public String getMainView(final Model model) {
        model.addAttribute(AttributeConstant.SCHEDULE_MOVIES, cinemaApiService.getScheduleForView());
        model.addAttribute(AttributeConstant.FILTER_MAP, settings.getFilters());
        final List<Date> dates = cinemaApiService.getDates();
        model.addAttribute(AttributeConstant.SELECTED_DATE_INDEX,
                           getSelectedDateIndex(dates, settings.getSelectedDate()));
        model.addAttribute(AttributeConstant.AVAILABLE_DATES, dates);
        return "mainView";
    }

    //TODO check if it works at late hours in the day
    private int getSelectedDateIndex(final List<Date> dates, final Date selectedDate) {
        return dates.stream()
                    .filter(d -> DateUtils.isSameDay(d, selectedDate))
                    .findFirst()
                    .map(dates::indexOf)
                    .orElse(-1);
    }
}
