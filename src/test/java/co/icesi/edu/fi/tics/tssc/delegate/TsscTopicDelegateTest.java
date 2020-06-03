package co.icesi.edu.fi.tics.tssc.delegate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import co.edu.icesi.fi.tics.tssc.delegates.TsscTopicDelegate;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;

@RunWith(MockitoJUnitRunner.class)
public class TsscTopicDelegateTest {
	
	public static final String URL = "http://localhost:8080/api/topics/";

	@Mock
	private RestTemplate restTemplate;
	
	@InjectMocks
	private TsscTopicDelegate topicDelegate;
	
	private TsscTopic topic;
	
	private TsscTopic[] topics;
	
	@Before
	public void setup1()
	{
		topic = new TsscTopic();
		topic.setId(20);
		topic.setDefaultGroups(10);
		topic.setDefaultSprints(20);
		topic.setDescription("Tema para probar");
		topic.setName("Tema");
		topicDelegate.setRestTemplate(restTemplate);
		
		topics = new TsscTopic[3];
		topics[0] = topic;
		TsscTopic topic2 = new TsscTopic();
		topic2.setId(30);
		topics[1] =topic2;
		TsscTopic topic3 = new TsscTopic();
		topic3.setId(40);
		topics[2] = topic3;
		
	}
	
	
	
	@Test
	@DisplayName("Find topic by id test")
	public void test1()
	{
			Mockito.when(restTemplate.getForObject(URL+"findById/20", TsscTopic.class)).
			thenReturn(topic);
			assertEquals(topic, topicDelegate.findById(20));
		
		
	}
	
	@Test
	@DisplayName("Add topic test")
	public void test2() throws Exception
	{

		when(restTemplate.postForObject(URL+"add", topic, TsscTopic.class)).thenReturn(topic);
		assertEquals(topic, topicDelegate.saveTopic(topic));
	}
	
		
	@Test
	@DisplayName("Find all topics test")
	public void test3() {
		when(restTemplate.getForObject(URL+"findAll", TsscTopic[].class)).thenReturn(topics);
		List<Object> expectedList = Arrays.asList(topics);
		assertEquals(expectedList, topicDelegate.findAll());
	}
	
	@Test
	@DisplayName("Delete Topic test")
	public void test4()
	{
		topicDelegate.saveTopic(topic);
		topicDelegate.deleteTopic(topic.getId());
		Mockito.when(restTemplate.getForObject(URL+"findById/20", TsscTopic.class)).thenReturn(null);
		assertEquals(null, topicDelegate.findById(20));
	}
	
	@Test
	@DisplayName("Edit topic test")
	public void test5()
	{
		Mockito.when(restTemplate.getForObject(URL+"findById/20", TsscTopic.class)).thenReturn(topic);
		assertEquals(topic, topicDelegate.findById(topic.getId()));
		topic.setName("Nombre nuevo");
		topic.setDefaultGroups(100);
		///EDIT
		topicDelegate.editTopic(topic);
		Mockito.when(restTemplate.getForObject(URL+"findById/20", TsscTopic.class)).thenReturn(topic);
		assertEquals(topic, topicDelegate.findById(topic.getId()));

	}
	
	

	
}
