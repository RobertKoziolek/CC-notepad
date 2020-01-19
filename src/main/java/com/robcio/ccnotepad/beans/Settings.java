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
@Getter
@Setter
public class Settings {

    private Date selectedDate;
    private Map<Filter, Boolean> filters;

    {
        filters = new TreeMap<>(Comparator.comparing(Filter::getLabel));
        for (final Filter filter : Filter.values()) {
            filters.put(filter, Boolean.TRUE);
        }
        selectedDate = new Date();
    }

}
