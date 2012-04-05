package com.topriddy.jambito.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * Institution.java
 * Purpose: Represents Institution Embedded Entity 
 *
 * @author topriddy
 * @version 1.0
 */
@Data
public class Institution implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    
    private String choice;
    private String description;
    private String name;
    private String type;
    private String course;
    private String faculty;
}
