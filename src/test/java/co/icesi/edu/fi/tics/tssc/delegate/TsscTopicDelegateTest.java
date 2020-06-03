package co.icesi.edu.fi.tics.tssc.delegate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import co.edu.icesi.fi.tics.tssc.delegates.TsscTopicDelegate;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.restController.TsscTopicRestController;

public class TsscTopicDelegateTest {

	
	public static final String URL = "http://localhost:8080/api/topics/";
	@InjectMocks
	@Spy
	private TsscTopicDelegate topicDelegate;
	
	@Mock
	private RestTemplate restTemplate;
	
	public TsscTopic topic;
	
	
	@BeforeEach
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);

		restTemplate = new RestTemplate();
		//MockRestServiceServer.createServer(restTemplate);
		//topicDelegate.setRestTemplate(restTemplate);
		
	}
	
	@Test
	@DisplayName("ADD GAME")
	public void test1() {
		buildTopic();
		when(restTemplate.postForEntity("localhost:8080/api/topics/add", topic, TsscTopic.class).getBody()).thenReturn(topic);
		//TsscTopic result = topicDelegate.saveTopic(topic);
		//assertEquals(topic, result);
	}
   
	@Test
	@DisplayName("GET BY ID")
	public void test2()
	{
		when(restTemplate.getForObject("http://localhost:8080/api/topics/findById/20", TsscTopic.class)).thenReturn(topic);
		topicDelegate.findById(20);

	}
	
	@Test
	@DisplayName("DELETE GAME")
	public void test3()
	{
		
	}
	

	@Test
	@DisplayName("EDIT GAME")
	public void test4()
	{
		
	}
	
	public void buildTopic()
	{
		 topic = new TsscTopic();
		topic.setId(20);
		topic.setDefaultGroups(10);
		topic.setDefaultSprints(20);
		topic.setDescription("hola");
		topic.setName("tema");
	}
	
	
	
}
