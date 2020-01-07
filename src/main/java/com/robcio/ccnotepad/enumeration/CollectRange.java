package com.robcio.ccnotepad.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CollectRange {

    TODAY("Today"), TOMORROW("Tomorrow");

    final private String label;
}
