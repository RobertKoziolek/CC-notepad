package com.robcio.ccnotepad.controller;

import com.robcio.ccnotepad.model.JsonWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/movies")
public class GetMovieInfoForDateController {
    private static final Logger logger = LoggerFactory.getLogger(GetMovieInfoForDateController.class);

    @Autowired
    private RestTemplate restTemplate;
    private final String urlFormatString = "https://www.cinema-city.pl/pl/data-api-service/v1/quickbook/10103/film-events/in-cinema/1087/at-date/%s?attr=&lang=pl_PL";

    @GetMapping("/today")
    public String getMovie() {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        final String dateString = format.format(new Date());

        String url = String.format(urlFormatString, dateString);
        ResponseEntity<JsonWrapper> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<JsonWrapper>() {
                });
        final JsonWrapper jsonWrapper = response.getBody();

        return jsonWrapper.getBody().toString();

    }
}
