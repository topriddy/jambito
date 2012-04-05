package com.topriddy.jambito.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * Course.java
 * Purpose: Represents Course Embedded Entity 
 *
 * @author topriddy
 * @version 1.0
 */
@Data
public class Course implements Serializable{
    private Long id;
    private String courseTitle;
    private int scores;
}
