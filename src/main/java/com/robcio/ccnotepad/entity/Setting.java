package com.robcio.ccnotepad.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
//TODO no need for DB, remove in favor of simple bean
public class Setting {

    @Id
    private String name;

    private String value;

}
