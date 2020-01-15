package com.robcio.ccnotepad.service;

import com.robcio.ccnotepad.connector.CinemaCityConnector;
import com.robcio.ccnotepad.model.json.FutureInfo;
import com.robcio.ccnotepad.model.json.FuturePoster;
import com.robcio.ccnotepad.model.json.ScheduleInfo;
import com.robcio.ccnotepad.model.view.ViewMovie;
import com.robcio.ccnotepad.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class CinemaApiService {

    @Autowired
    private SettingService settingService;
    @Autowired
    private ViewPreparationService viewPreparationService;
    @Autowired
    private CinemaCityConnector cinemaCityConnector;

    public Set<ViewMovie> getScheduleForView() {
        ScheduleInfo scheduleInfo = null;
        //TODO change for some date picker with only available dates
        switch (settingService.getCollectRange()) {
            case TODAY:
                scheduleInfo = cinemaCityConnector.getFor(new Date());
                break;
            case TOMORROW:
                final Date date = DateUtils.addDay(new Date());
                scheduleInfo = cinemaCityConnector.getFor(date);
                break;
        }
        if (scheduleInfo == null) {
            throw new IllegalArgumentException("Selected range setting not available");
        }
        //TODO perhaps should reduce model classes number as #FuturePoster in favor of #Movie/ViewMovie approach
        return viewPreparationService.prepareForView(scheduleInfo);
    }

    public Set<FuturePoster> getFutureMoviesForView() {
        final FutureInfo futureInfo = cinemaCityConnector.getFuture();
        return viewPreparationService.prepareForView(futureInfo);
    }

}