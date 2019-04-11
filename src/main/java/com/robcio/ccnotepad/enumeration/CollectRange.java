package com.robcio.ccnotepad.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CollectRange {

    TODAY("Today"), TOMORROW("Tomorrow"), TODAY_AND_TOMORROW("Today and tomorrow");

    final private String label;
}
