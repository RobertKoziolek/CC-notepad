package com.robcio.ccnotepad.beans;

import com.robcio.ccnotepad.enumeration.Filter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;


@Component
@Setter
public class Settings {

    private Date selectedDate;
    @Getter
    private Map<Filter, Boolean> filters;

    {
        filters = new TreeMap<>(Comparator.comparing(Filter::getLabel));
        for (final Filter filter : Filter.values()) {
            filters.put(filter, Boolean.TRUE);
        }
        selectedDate = new Date();
    }

    public Date getSelectedDate() {
        final Date today = new Date();
        if (selectedDate.getTime() < today.getTime()) {
            setSelectedDate(today);
        }
        return today;
    }
}
