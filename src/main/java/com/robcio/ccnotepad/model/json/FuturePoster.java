package com.robcio.ccnotepad.model.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
@ToString
public class FuturePoster {

    private String name;
    private String link;
    private String posterLink;
    private String videoLink;
    private String releaseDate;
    private Boolean isMonitored;

    @JsonCreator
    public FuturePoster(@JsonProperty("featureTitle") String name,
                        @JsonProperty("posterSrc") String posterLink,
                        @JsonProperty("url") String link,
                        @JsonProperty("dateStarted") String releaseDate,
                        @JsonProperty("mediaList") List<Map<String, String>> mediaList) {
        this.name = name;
        this.posterLink = posterLink;
        this.link = link;
        this.releaseDate = releaseDate.substring(0, 10);
        final Optional<Map<String, String>> first = mediaList.stream()
                                                             .filter(kv -> (kv.containsValue("Video") && kv.containsValue(
                                                                     "trailer")))
                                                             .findFirst();
        first.ifPresent(stringStringMap -> this.videoLink = stringStringMap.get("url"));
    }
}
