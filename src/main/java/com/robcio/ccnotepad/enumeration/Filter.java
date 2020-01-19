package com.robcio.ccnotepad.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Filter {

    ANIMATION("Animation", "animation"),
    MOVIE2D("2D", "2d"),
    MOVIE3D("3D", "3d"),
    ORIGINAL_POLISH("Polish", "original-lang-pl"),
    DUBBED("Dubbed", "dubbed"),
    SUBBED("Subbed", "subbed");

    private String label;
    private String attribute;

}
