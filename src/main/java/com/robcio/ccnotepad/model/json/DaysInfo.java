package com.robcio.ccnotepad.model.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class DaysInfo implements BodyInfo {

    private Set<Date> dates;

}
