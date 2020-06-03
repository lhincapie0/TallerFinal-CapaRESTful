package co.icesi.edu.fi.tics.tssc.delegate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import co.edu.icesi.fi.tics.tssc.delegates.TsscTopicDelegate;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;

public class TsscTopicDelegateTest {

	
	public final static String URI = "http://localhost:8080/api/topics/";
	
	@InjectMocks
	private TsscTopicDelegate delegate;
	
	@Mock
	private RestTemplate restTemplate;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		restTemplate = new RestTemplate();
		delegate.setRestTemplate(restTemplate);

	}
	
	@Test
	@DisplayName("Add Topic")
	public void test1()
	{
		TsscTopic topic = new TsscTopic();
		topic.setId(100);
		topic.setName("Tema de prueba");
		topic.setDescription("Tema para probar el agregar nuevo tema");
		topic.setDefaultSprints(8);
		topic.setDefaultGroups(4);
		System.out.println(restTemplate.toString());
		ResponseEntity<TsscTopic> response = new ResponseEntity<TsscTopic>(topic, HttpStatus.ACCEPTED);

		when(restTemplate.postForEntity(URI+"add", topic, TsscTopic.class).getBody()).thenReturn(topic);
		
		assertEquals(delegate.saveTopic(topic), topic);
		}
	
}
