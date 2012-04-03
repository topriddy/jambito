package com.topriddy.jambito.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Id;

import lombok.Data;

@Data
public class Candidate implements Serializable{
    @Id
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
    
    @Embedded
    List<Course> courses;
    @Embedded
    List<Institution> institutions;

    public int getTotalScores() {
        int sum = 0;
        for (Course course : courses) {
            sum += course.getScores();
        }
        return sum;
    }
}
