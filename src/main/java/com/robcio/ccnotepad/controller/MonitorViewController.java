package com.robcio.ccnotepad.controller;

import com.robcio.ccnotepad.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/monitor")
public class MonitorViewController {
    @Autowired
    private MonitorService monitorService;

    @GetMapping
    public String getMonitorView(final Model model) {
        model.addAttribute(AttributeConstant.MONITOR_MOVIES, monitorService.getMovies());
        return "monitorView";
    }
}
