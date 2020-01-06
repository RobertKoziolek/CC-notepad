package com.robcio.ccnotepad.enumeration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum PackConfig {
    ORIGINAL_2D("PL", new String[]{"original-lang-pl", "2d"}),
    ORIGINAL_3D("PL 3D", new String[]{"original-lang-pl", "3d"}),
    SUBBED_2D("Subs", new String[]{"subbed", "2d"}),
    SUBBED_3D("Subs 3D", new String[]{"subbed", "3d"}),
    DUBBED_2D("Dub", new String[]{"dubbed", "2d"}),
    DUBBED_3D("Dub 3D", new String[]{"dubbed", "3d"});

    private String viewName;
    private String[] attributes;
}
