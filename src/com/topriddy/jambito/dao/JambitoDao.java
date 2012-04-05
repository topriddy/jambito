package com.topriddy.jambito.dao;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;
import com.topriddy.jambito.entity.Candidate;

public class JambitoDao extends DAOBase {
	static {
		ObjectifyService.register(Candidate.class);
	}

	public boolean createCandidate(Candidate candidate) {
		boolean success = false;
		//check needed cos user might have used a different search term
		if (candidate != null && findCandidateByRegistrationNumber(candidate.getRegistrationNumber()) == null) {
			candidate.setRegistrationNumber(candidate.getRegistrationNumber().toUpperCase());
			ofy().put(candidate);
			success = true;
		}
		return success;
	}

	public Candidate findCandidateByRegistrationNumber(String registrationNumber) {
		Candidate candidate = ofy().query(Candidate.class)
				.filter("registrationNumber", registrationNumber.toUpperCase()).get();
		return candidate;
	}
}
