package com.robcio.ccnotepad.service;

import com.robcio.ccnotepad.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmailService {
    @Autowired
    public JavaMailSender emailSender;

    private void sendSimpleMessage(final String to, final String subject, final String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendMonitoredMoviesNotification(final Map<String, SortedSet<Date>> nameDatesMap,
                                                Set<String> phrases) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Movies you searched for are available for booking:\n\n");
        for (final Map.Entry<String, SortedSet<Date>> entry : nameDatesMap.entrySet()) {
            final SortedSet<Date> dateSet = entry.getValue();
            stringBuilder.append(String.format("%s (%d events):\n", entry.getKey(), dateSet.size()));
            final Set<String> dateStringSet = dateSet.stream()
                                                     .map(DateUtils::shortFormat)
                                                     .collect(Collectors.toCollection(HashSet::new));
            for (final String date : dateStringSet) {
                stringBuilder.append(String.format("\t%s\n", date));
            }
        }
        stringBuilder.append(String.format("\n\nSearched for phrases: %s", phrases.toString()));
        //TODO email to be configured from app
        sendSimpleMessage("", "Movies available for booking", stringBuilder.toString());
    }
}
