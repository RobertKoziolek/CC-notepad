package com.robcio.ccnotepad.beans;

import com.robcio.ccnotepad.enumeration.CollectRange;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Component
@Getter
@Setter
public class Settings {

    private CollectRange collectRange;

}
