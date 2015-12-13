package il.co.topq.report.controller.resource;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.internal.MultiPartWriter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import il.co.topq.difido.model.remote.ExecutionDetails;
import il.co.topq.report.Configuration;
import il.co.topq.report.Configuration.ConfigProps;
import il.co.topq.report.model.AbstractResourceTestCase;
import il.co.topq.report.model.ExecutionManager.ExecutionMetaData;

public class TestReportResource extends AbstractResourceTestCase {

	private Client jerseyClient;
	private WebTarget baseTarget;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		String baseUri = Configuration.INSTANCE.readString(ConfigProps.BASE_URI);
		jerseyClient = ClientBuilder.newClient();
		jerseyClient.register(JacksonFeature.class);
		jerseyClient.register(MultiPartWriter.class);
		this.baseTarget = jerseyClient.target(baseUri);
		jerseyClient.target(baseUri);
		AddExecutions();

	}

	private void AddExecutions() throws Exception {
		for (int i = 0; i < 10 ; i++){
			Thread.sleep(1000);
		}
	}

	@After
	public void tearDown() {
		super.tearDown();
		if (jerseyClient != null) {
			jerseyClient.close();
		}
	}

	private ExecutionMetaData[] getExecutionMetaData() {
		WebTarget executionsTarget = baseTarget.path("/reports");
		ExecutionMetaData[] reports = executionsTarget.request(MediaType.APPLICATION_JSON).get(ExecutionMetaData[].class);
		return reports;
	}

	@Test
	public void testGetReports() throws Exception {
		ExecutionDetails execution = new ExecutionDetails();
		execution.setShared(false);
		difidoClient.addExecution(execution);	
		ExecutionMetaData[] reports = getExecutionMetaData();
		Assert.assertNotNull(reports);
	}

	
}