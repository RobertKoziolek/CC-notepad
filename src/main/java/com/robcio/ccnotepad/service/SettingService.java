package com.robcio.ccnotepad.service;

import com.robcio.ccnotepad.beans.Settings;
import com.robcio.ccnotepad.enumeration.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class SettingService {

    @Autowired
    private final Settings settings;

    @Autowired
    public SettingService(final Settings settings) {
        this.settings = settings;
    }

    public void setSelectedDate(final Date date) {
        settings.setSelectedDate(date);
    }

    public Date getSelectedDate() {
        return settings.getSelectedDate();
    }

    public void setFilter(final Filter filter, final boolean value) {
        settings.getFilters().put(filter, value);
    }

    public Map<Filter, Boolean> getFilters() {
        return settings.getFilters();
    }
}
