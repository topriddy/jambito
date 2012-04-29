package com.topriddy.jambito.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.topriddy.jambito.entity.Candidate;
import com.topriddy.jambito.util.ResultEngine;

public class CandidateServlet extends HttpServlet {
	private static final long serialVersionUID = -1233727069433275226L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String registrationNumber = req.getParameter("registrationNumber");
		ResultEngine engine = new ResultEngine();
		Candidate candidate = null;
		String jsonResp = null;

		try {
			if (registrationNumber != null
					&& !registrationNumber.trim().equals("")) {
				candidate = engine.checkResult(registrationNumber);	
			}
			jsonResp = constructResp(candidate);

			resp.setContentType("text/plain");
			PrintWriter writer = resp.getWriter();
			writer.println(jsonResp);
			writer.flush();
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private String constructResp(Candidate candidate) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean success = false;
		if (candidate != null) {
			map.put("candidate", candidate);
			success = true;
		}
		map.put("found", success);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(map);
	}
}
