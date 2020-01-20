package com.robcio.ccnotepad.service;

import com.robcio.ccnotepad.connector.CinemaCityConnector;
import com.robcio.ccnotepad.model.json.FutureInfo;
import com.robcio.ccnotepad.model.json.FuturePoster;
import com.robcio.ccnotepad.model.json.ScheduleInfo;
import com.robcio.ccnotepad.model.view.ViewMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CinemaApiService {

    @Autowired
    private SettingService settingService;
    @Autowired
    private ViewPreparationService viewPreparationService;
    @Autowired
    private CinemaCityConnector connector;

    public Set<ViewMovie> getScheduleForView() {
        final ScheduleInfo scheduleInfo = connector.getFor(settingService.getSelectedDate());
        //TODO perhaps should reduce model classes number as #FuturePoster in favor of #Movie/ViewMovie approach
        return viewPreparationService.prepareForView(scheduleInfo);
    }

    public Set<FuturePoster> getFutureMoviesForView() {
        final FutureInfo futureInfo = connector.getFuture();
        return viewPreparationService.prepareForView(futureInfo);
    }

    public List<Date> getDates() {
        final LinkedList<Date> list = new LinkedList<>(connector.getDays().getDates());
        list.sort(Comparator.comparingLong(Date::getTime));
        return list;
    }
}