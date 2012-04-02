package com.topriddy.jambito.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Candidate implements Serializable{
    private Long id;
    private String candidateName;
    private String gender;
    private String stateOfOrigin;
    private String localGovernment;
    private String registrationNumber;
    private String examinationNumber;
    private String examinationCenter;
    private String phoneNumber;
    private String email;
    private String fileName;
    
    List<Course> courses;
    List<Institution> institutions;

    public int getTotalScores() {
        int sum = 0;
        for (Course course : courses) {
            sum += course.getScores();
        }
        return sum;
    }
}
