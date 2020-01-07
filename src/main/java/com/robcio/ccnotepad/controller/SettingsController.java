package com.robcio.ccnotepad.controller;

import com.robcio.ccnotepad.enumeration.CollectRange;
import com.robcio.ccnotepad.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/setting")
public class SettingsController {

    private final SettingService settingService;

    @Autowired
    public SettingsController(final SettingService settingService) {
        this.settingService = settingService;
    }

    @PutMapping("/range")
    public String setWatched(@RequestParam final CollectRange range) {
        settingService.setCollectRange(range);
        return "redirect:/";
    }

    @PutMapping("/filterOutAnimation")
    public String setWatched(@RequestParam final Boolean filterOutAnimation) {
        settingService.setFilterOutAnimation(filterOutAnimation);
        return "redirect:/";
    }
}
