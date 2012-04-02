package com.topriddy.jambito.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class Course implements Serializable{
    private Long id;
    private String courseTitle;
    private int scores;
}
