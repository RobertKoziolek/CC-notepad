package com.robcio.ccnotepad.controller;

import com.robcio.ccnotepad.service.MonitoredMovieService;
import com.robcio.ccnotepad.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/monitor")
public class MonitorViewController {
    @Autowired
    private MonitoredMovieService monitorService;
    @Autowired
    private SettingService settingService;

    @GetMapping
    public String getMonitorView(final Model model) {
        model.addAttribute(AttributeConstant.MONITOR_MOVIES, monitorService.getMovies());
        model.addAttribute(AttributeConstant.EMAIL, settingService.getEmail());
        return "monitorView";
    }

    @PutMapping("/add")
    public String addMovie(@RequestParam final String movieName, @RequestParam final Optional<String> redirect) {
        monitorService.add(movieName);
        return "redirect:/" + redirect.orElse("monitor");
    }

    @PutMapping("/remove/id")
    public String removeMovie(@RequestParam final Long id) {
        monitorService.remove(id);
        return "redirect:/monitor";
    }

    @PutMapping("/remove")
    public String removeMovie(@RequestParam final String movieName, @RequestParam final Optional<String> redirect) {
        monitorService.remove(movieName);
        return "redirect:/" + redirect.orElse("monitor");
    }
}
