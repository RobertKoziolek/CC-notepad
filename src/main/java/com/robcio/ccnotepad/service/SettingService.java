package com.robcio.ccnotepad.service;

import com.robcio.ccnotepad.beans.Settings;
import com.robcio.ccnotepad.enumeration.CollectRange;
import com.robcio.ccnotepad.enumeration.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SettingService {

    @Autowired
    private final Settings settings;

    @Autowired
    public SettingService(final Settings settings) {
        this.settings = settings;
    }

    public void setCollectRange(final CollectRange collectRange) {
        settings.setCollectRange(collectRange);
    }

    public CollectRange getCollectRange() {
        CollectRange collectRange = settings.getCollectRange();
        if (collectRange == null) {
            collectRange = CollectRange.class.getEnumConstants()[0];
            setCollectRange(collectRange);
        }
        return collectRange;
    }

    public void setFilter(final Filter filter, final boolean value) {
        settings.getFilters().put(filter, value);
    }

    public Map<Filter, Boolean> getFilters() {
        return settings.getFilters();
    }
}
