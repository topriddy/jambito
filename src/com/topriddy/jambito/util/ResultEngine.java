package com.topriddy.jambito.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import lombok.extern.java.Log;

import com.gargoylesoftware.htmlunit.AlertHandler;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.topriddy.jambito.dao.JambitoDao;
import com.topriddy.jambito.entity.Candidate;
import com.topriddy.jambito.entity.Course;
import com.topriddy.jambito.entity.Institution;
import com.topriddy.jambito.exception.ExceededLimitException;

/**
 * ResultEngine.java
 * Purpose: contains logic for retrieving result from jamb site 
 *
 * @author topriddy
 * @version 1.0
 */
@Log
public class ResultEngine {
	private final String JAMB_URL = "http://www.jamb.org.ng/Unifiedtme/";
	JambitoDao dao = new JambitoDao();

	public Candidate checkResult(String registrationNumber) {
		Candidate candidate = null;
		candidate = dao.findCandidateByRegistrationNumber(registrationNumber);
		if (candidate != null)
			return candidate;
		
		try {
			final WebClient client = new WebClient();
			client.setThrowExceptionOnFailingStatusCode(false);
			client.setThrowExceptionOnScriptError(false);

			HtmlPage homePage = client.getPage(JAMB_URL);

			HtmlTextInput pinCodeInput = (HtmlTextInput) homePage
					.getElementById("ctl00_ContentPlaceHolder1_txtPINRegNumber");
			pinCodeInput.setText(registrationNumber);

			HtmlSubmitInput checkResultButton = (HtmlSubmitInput) homePage
					.getElementById("ctl00_ContentPlaceHolder1_btnCheckResult");
			HtmlPage resultPage = (HtmlPage) checkResultButton.click();
			candidate = extractCandidate(resultPage);
			client.closeAllWindows();
		} catch (Exception ex) {
			log.severe(ex.getMessage());
			ex.printStackTrace();
		}
		
		if(candidate != null)
			dao.createCandidate(candidate);
		
		return candidate;
	}

	private Candidate extractCandidate(HtmlPage resultPage) {
		Candidate candidate = null;
		try {
			if (resultPage.getElementById("txtRegNumber") == null) {
				return null;
			}
			String registrationNumber = resultPage
					.getElementById("txtRegNumber").asText().trim();
			if (registrationNumber == null || registrationNumber.equals("")) {
				return null;
			}
			candidate = new Candidate();
			candidate.setRegistrationNumber(registrationNumber.toUpperCase());
			candidate.setCandidateName(resultPage.getElementById("txtNames")
					.asText().trim());
			candidate.setGender(resultPage.getElementById("txtGender").asText()
					.trim());
			candidate.setStateOfOrigin(resultPage.getElementById("txtState")
					.asText().trim());
			candidate.setLocalGovernment(resultPage.getElementById("txtLga")
					.asText().trim());
			candidate.setExaminationNumber(resultPage
					.getElementById("txtExamNo").asText().trim());
			candidate.setExaminationCenter(resultPage
					.getElementById("txtExamCentre").asText().trim());

			// read all courses and add to course list
			List<Course> courses = new ArrayList<Course>();

			for (int i = 1; i <= 4; i++) {
				String courseToken = resultPage.getElementById(
						"txtTMESubjects" + i).asText();
				courses.add(parseCourseString(courseToken));
			}
			candidate.setCourses(courses);
			// read all institutions available and add to institution list
			List<Institution> institutions = getInstitutions(resultPage);
			candidate.setInstitutions(institutions);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.severe(ex.toString());
		}
		return candidate;
	}

	private List<Institution> getInstitutions(HtmlPage resultPage) {
		List<Institution> institutions = new ArrayList<Institution>();

		Institution institution = new Institution();
		String name;
		String course;
		String faculty;

		// 1
		institution.setType("University");
		institution.setChoice("First Choice");
		institution.setDescription("Most Preferred Choice");
		name = resultPage.getElementById("txtFirstInstitution").asText();
		name = name.replace("Institution:", "").trim();
		institution.setName(name);
		course = resultPage.getElementById("txtFirstCourse").asText();
		course = course.replace("Course: ", "").trim();
		institution.setCourse(course);
		faculty = resultPage.getElementById("txtFirstFaculty").asText();
		faculty = faculty.replace("Faculty: ", "").trim();
		institution.setFaculty(faculty);
		institutions.add(institution);

		// 2
		institution = new Institution();
		institution.setType("University");
		institution.setChoice("Second Choice");
		institution.setDescription("Most Preferred Choice");
		name = resultPage.getElementById("txtForthInstitution").asText();
		name = name.replace("Institution:", "").trim();
		institution.setName(name);
		course = resultPage.getElementById("txtForthCourse").asText();
		course = course.replace("Course: ", "").trim();
		institution.setCourse(course);
		faculty = resultPage.getElementById("txtForthFaculty").asText();
		faculty = faculty.replace("Faculty: ", "").trim();
		institution.setFaculty(faculty);
		institutions.add(institution);

		// 3
		institution = new Institution();
		institution.setType("Polytechnic/Monotechnic");
		institution.setChoice("First Choice");
		institution.setDescription("More Preferred Choice");
		name = resultPage.getElementById("txtSecondInstitution").asText();
		name = name.replace("Institution:", "").trim();
		institution.setName(name);
		course = resultPage.getElementById("txtSecondCourse").asText();
		course = course.replace("Course: ", "").trim();
		institution.setCourse(course);
		institution.setCourse(course);
		institutions.add(institution);

		// 4
		institution = new Institution();
		institution.setType("Polytechnic/Monotechnic");
		institution.setChoice("Second Choice");
		institution.setDescription("More Preferred Choice");
		name = resultPage.getElementById("txtFifthInstitution").asText();
		name = name.replace("Institution:", "").trim();
		institution.setName(name);
		course = resultPage.getElementById("txtFifthCourse").asText();
		course = course.replace("Course: ", "").trim();
		institution.setCourse(course);
		institution.setCourse(course);
		institutions.add(institution);

		// 5
		institution = new Institution();
		institution.setType("College of Education");
		institution.setChoice("First Choice");
		institution.setDescription("Preferred Choice");
		name = resultPage.getElementById("txtThirdInstitution").asText();
		name = name.replace("Institution:", "").trim();
		institution.setName(name);
		course = resultPage.getElementById("txtThirdCourse").asText();
		course = course.replace("Course: ", "").trim();
		institution.setCourse(course);
		institution.setCourse(course);
		institutions.add(institution);

		// 6
		institution = new Institution();
		institution.setType("College of Education");
		institution.setChoice("Second Choice");
		institution.setDescription("Preferred Choice");
		name = resultPage.getElementById("txtSixthInstitution").asText();
		name = name.replace("Institution:", "").trim();
		institution.setName(name);
		course = resultPage.getElementById("txtSixthCourse").asText();
		course = course.replace("Course: ", "").trim();
		institution.setCourse(course);
		institution.setCourse(course);
		institutions.add(institution);
		return institutions;
	}

	private Course parseCourseString(String token) {
		Course course = null;
		try {
			StringTokenizer tokenizer = new StringTokenizer(token, "=");
			String courseTitle = tokenizer.nextToken();
			String scoreString = tokenizer.nextToken();
			scoreString = scoreString.replace(" ", "");
			scoreString = scoreString.replace(";", "");
			int scores = Integer.parseInt(scoreString);
			course = new Course();
			course.setCourseTitle(courseTitle.trim());
			course.setScores(scores);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.severe(ex.getMessage());
		}
		return course;
	}

	public Candidate getDefaultCandidate() {
		Candidate candidate = new Candidate();

		String candidateName = "CIROMA ADEOLA";
		String registrationNumber = "7373744888AE";
		String examinationNumber = "C573733";
		String examinationCenter = "FESTAC";
		String gender = "MALE";
		String stateOfOrigin = "LAGOS";
		String localGovernment = "LAGOS ISLAND";
		String phoneNumber = "08056725432";
		String email = "cadeola@gmail.com";
		List<Course> courses = getDefaultCourseList();
		List<Institution> institutions = getDefaultInstitutionList();

		candidate.setRegistrationNumber(registrationNumber);
		candidate.setCandidateName(candidateName);
		candidate.setExaminationNumber(examinationNumber);
		candidate.setExaminationCenter(examinationCenter);
		candidate.setGender(gender);
		candidate.setStateOfOrigin(stateOfOrigin);
		candidate.setLocalGovernment(localGovernment);
		candidate.setPhoneNumber(phoneNumber);
		candidate.setEmail(email);
		candidate.setCourses(courses);
		candidate.setInstitutions(institutions);

		return candidate;
	}

	public List<Course> getDefaultCourseList() {
		List<Course> courseList = new ArrayList<Course>();

		Course course1 = new Course();
		course1.setCourseTitle("Mathematics");
		course1.setScores(80);
		courseList.add(course1);

		Course course2 = new Course();
		course2.setCourseTitle("Use of English");
		course2.setScores(65);
		courseList.add(course2);

		Course course3 = new Course();
		course3.setCourseTitle("Physics");
		course3.setScores(56);
		courseList.add(course3);

		Course course4 = new Course();
		course4.setCourseTitle("Chemistry");
		course4.setScores(60);
		courseList.add(course4);

		return courseList;
	}

	public List<Institution> getDefaultInstitutionList() {
		List<Institution> institutionList = new ArrayList<Institution>();

		Institution institution1 = new Institution();
		institution1.setName("Federal University of Technology, Akure");
		institution1.setType("University");
		institution1.setFaculty("Sciences");
		institution1.setChoice("First Choice");
		institution1.setCourse("Computer Science");
		institution1.setDescription("foo");
		institutionList.add(institution1);

		Institution institution2 = new Institution();
		institution2.setName("University of Ibadan");
		institution2.setType("University");
		institution2.setFaculty("Sciences");
		institution2.setChoice("Second Choice");
		institution2.setCourse("");
		institution2.setDescription("");
		institutionList.add(institution2);

		Institution institution3 = new Institution();
		institution3.setName("");
		institution3.setType("");
		institution3.setFaculty("");
		institution3.setChoice("");
		institution3.setCourse("");
		institution3.setDescription("");
		institutionList.add(institution3);

		Institution institution4 = new Institution();
		institution4.setName("");
		institution4.setType("");
		institution4.setFaculty("");
		institution4.setChoice("");
		institution4.setCourse("");
		institution4.setDescription("");
		institutionList.add(institution4);
		return institutionList;
	}
}
