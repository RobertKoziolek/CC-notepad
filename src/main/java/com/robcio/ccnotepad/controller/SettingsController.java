package com.robcio.ccnotepad.controller;

import com.robcio.ccnotepad.enumeration.Filter;
import com.robcio.ccnotepad.service.SettingService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/setting")
public class SettingsController {

    private final SettingService settingService;

    @Autowired
    public SettingsController(final SettingService settingService) {
        this.settingService = settingService;
    }

    @SneakyThrows
    @PutMapping("/date")
    public String setDate(@RequestParam final String dateString) {
        final DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        final Date date = df.parse(dateString);
        settingService.setSelectedDate(date);
        return "redirect:/";
    }

    @PutMapping("/filter")
    public String setFilter(@RequestParam final Filter filter, @RequestParam final Boolean value) {
        settingService.setFilter(filter, value);
        return "redirect:/";
    }

    @PutMapping("/email")
    public String setFilter(@RequestParam final String email) {
        settingService.setEmail(email);
        return "redirect:/monitor";
    }

}
