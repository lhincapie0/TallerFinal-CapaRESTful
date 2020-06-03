package co.icesi.edu.fi.tics.tssc.delegate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import co.edu.icesi.fi.tics.tssc.delegates.TsscStoryDelegate;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;

@RunWith(MockitoJUnitRunner.class)
public class TsscStoryDelegateTest {

	

	public static final String URL = "http://localhost:8080/api/stories/";

	@Mock
	private RestTemplate restTemplate;
	
	@InjectMocks
	private TsscStoryDelegate storyDelegate;
	
	private TsscStory story;
	
	@Before
	public void setup()
	{
		story = new TsscStory();
		story.setId(10);
		story.setBusinessValue(new BigDecimal(100));
		story.setPriority(new BigDecimal(30));
		story.setInitialSprint(new BigDecimal(300));
	}
	
	@Test
	@DisplayName("Find story by id test")
	public void test1()
	{
		Mockito.when(restTemplate.getForObject(URL+"findById/10", TsscStory.class)).thenReturn(story);
		assertEquals(story,storyDelegate.findById(10));
	}
	
	@Test
	@DisplayName("Add Story")
	public void test2()
	{
		Mockito.when(restTemplate.postForObject(URL+"add", story, TsscStory.class)).thenReturn(story);
		assertEquals(story,storyDelegate.saveStory(story));

	}
	
	
}
