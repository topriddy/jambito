package com.topriddy.jambito;

import org.apache.wicket.pageStore.memory.DataStoreEvictionStrategy;
import org.apache.wicket.pageStore.memory.MemorySizeEvictionStrategy;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.lang.Bytes;

import com.topriddy.jambito.dao.JambitoDao;
import com.topriddy.jambito.entity.Candidate;
import com.topriddy.jambito.pages.BasePage;
import com.topriddy.jambito.pages.HomePage;
import com.topriddy.jambito.util.ResultEngine;

public class JambitoApplication extends WebApplication {
	protected void init() {
		super.init();
		// BasicConfigurator.configure();
		getResourceSettings().setResourcePollFrequency(null);
		mountPackage("p", BasePage.class);
		mountPage("home", HomePage.class);
		initWithTestData();
	}

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<HomePage> getHomePage() {
		return HomePage.class;
	}

	/**
	 * Setup custom eviction strategy for this application
	 */
	public DataStoreEvictionStrategy getEvictionStrategy() {
		return new MemorySizeEvictionStrategy(Bytes.megabytes(2));
	}

	public void initWithTestData() {
		JambitoDao dao = new JambitoDao();
		ResultEngine engine = new ResultEngine();

		Candidate candidate = engine.getDefaultCandidate();
		if (dao.findCandidateByRegistrationNumber(candidate
				.getRegistrationNumber()) == null) {
			dao.createCandidate(candidate);
		}
	}
}
