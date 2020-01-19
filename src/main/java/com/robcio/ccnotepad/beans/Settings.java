package com.robcio.ccnotepad.beans;

import com.robcio.ccnotepad.enumeration.CollectRange;
import com.robcio.ccnotepad.enumeration.Filter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
@Getter
@Setter
public class Settings {

    private CollectRange collectRange;
    private Map<Filter, Boolean> filters;

    {
        filters = new HashMap<>();
        for (final Filter filter : Filter.values()) {
            filters.put(filter, Boolean.TRUE);
        }
    }

}
