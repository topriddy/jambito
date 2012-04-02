package com.topriddy.jambito;

import org.apache.log4j.BasicConfigurator;
import org.apache.wicket.pageStore.memory.DataStoreEvictionStrategy;
import org.apache.wicket.pageStore.memory.MemorySizeEvictionStrategy;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.lang.Bytes;

import com.topriddy.jambito.pages.HomePage;

public class JambitoApplication extends WebApplication {
	protected void init() {
		super.init();
		BasicConfigurator.configure();
		getResourceSettings().setResourcePollFrequency(null);
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
}
