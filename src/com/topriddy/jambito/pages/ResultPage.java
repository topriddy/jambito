package com.topriddy.jambito.pages;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

import com.topriddy.jambito.entity.Candidate;
import com.topriddy.jambito.entity.Course;

public class ResultPage extends BasePage{
	public ResultPage(Candidate candidate){
		add(new Label("candidateName", Model.of(candidate.getCandidateName())));
		add(new Label("totalScore", Model.of(candidate.getTotalScores())));
		add(new ListView<Course>("courseListView", candidate.getCourses()){
			@Override
			protected void populateItem(ListItem<Course> courseItem) {
				Course course = courseItem.getModelObject();
				courseItem.add(new Label("snos", String.valueOf(courseItem.getIndex() + 1)));
				courseItem.add(new Label("courseTitle", Model.of(course.getCourseTitle())));
				courseItem.add(new Label("courseScore", Model.of(course.getScores())));
			}
		});
	}
}
