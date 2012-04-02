package com.topriddy.jambito.pages;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

import com.topriddy.jambito.entity.Candidate;

public class ResultPage extends BasePage{
	public ResultPage(Candidate candidate){
		add(new Label("name", Model.of(candidate.getCandidateName())));
		add(new Label("scores", Model.of(candidate.getTotalScores())));
	}
}
