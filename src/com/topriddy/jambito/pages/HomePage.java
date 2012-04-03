package com.topriddy.jambito.pages;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;

import com.topriddy.jambito.entity.Candidate;
import com.topriddy.jambito.util.ResultEngine;

@Log
public final class HomePage extends BasePage {
	public HomePage() {
		add(new HomePageForm("form"));
	}

	private class HomePageForm extends Form{
		private static final long serialVersionUID = 7227857375118094686L;
		@Getter
		@Setter
		private String pinCode;

		public HomePageForm(String id) {
			super(id);

			add(new FeedbackPanel("feedback"));
			add(new TextField<String>("pinCode", new PropertyModel<String>(
					this, "pinCode")).setRequired(true));
		}

		@Override
		public void onSubmit() {
			ResultEngine resultEngine = new ResultEngine();
			Candidate candidate = resultEngine.checkResult(pinCode);
			
			if (candidate != null) {
				setResponsePage(new ResultPage(candidate));
			} else {
				HomePageForm.this.error("Candidate Not Found");
			}
		}
	}
}
