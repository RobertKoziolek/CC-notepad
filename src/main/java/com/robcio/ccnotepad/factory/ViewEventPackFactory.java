package com.robcio.ccnotepad.factory;

import com.robcio.ccnotepad.enumeration.PackConfig;
import com.robcio.ccnotepad.model.json.Event;
import com.robcio.ccnotepad.model.view.ViewEventPack;
import com.robcio.ccnotepad.model.view.ViewMovieEvent;
import com.robcio.ccnotepad.util.DateUtils;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ViewEventPackFactory {

    final private static Supplier<TreeSet<ViewMovieEvent>> supplier = () -> new TreeSet<>(Comparator.comparing(
            ViewMovieEvent::getEventDateTime));

    public SortedSet<ViewEventPack> create(final Set<Event> events) {
        final SortedSet<ViewMovieEvent> allEvents = sortAndMap(events);
        final SortedSet<ViewEventPack> viewEventPacks = new TreeSet<>(Comparator.comparing(ViewEventPack::getName)
                                                                                .reversed());
        for (PackConfig packConfig : PackConfig.values()) {
            final String[] attributes = packConfig.getAttributes();
            final TreeSet<ViewMovieEvent> packEvents = allEvents.stream()
                                                                .filter(e -> e.getAttributeIds()
                                                                              .containsAll(Arrays.asList(attributes)))
                                                                .collect(
                                                                        Collectors.toCollection(supplier));
            if (!packEvents.isEmpty()) {
                viewEventPacks.add(new ViewEventPack(packConfig.getViewName(), packEvents));
            }
        }
        final TreeSet<ViewMovieEvent> addedEvents = viewEventPacks.stream()
                                                                  .flatMap((ViewEventPack viewEventPack) -> viewEventPack
                                                                          .getEvents()
                                                                          .stream())
                                                                  .filter(allEvents::contains)
                                                                  .collect(Collectors.toCollection(supplier));
        allEvents.removeAll(addedEvents);
        if (!allEvents.isEmpty()) {
            viewEventPacks.add(new ViewEventPack("Other", allEvents));
        }
        return viewEventPacks;
    }

    private SortedSet<ViewMovieEvent> sortAndMap(final Set<Event> events) {
        return events.stream().map(event -> {
            return new ViewMovieEvent(DateUtils.eventFormat(event.getEventDateTime()),
                                      event.getBookingLink(),
                                      event.getAttributeIds());
        }).collect(Collectors.toCollection(supplier));
    }
}
