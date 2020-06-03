package co.icesi.edu.fi.tics.tssc.delegate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.assertj.core.util.Arrays;
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
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;

@RunWith(MockitoJUnitRunner.class)
public class TsscStoryDelegateTest {

	

	public static final String URL = "http://localhost:8080/api/stories/";

	@Mock
	private RestTemplate restTemplate;
	
	@InjectMocks
	private TsscStoryDelegate storyDelegate;
	
	private TsscStory story;
	
	private TsscStory[] stories;
	
	@Before
	public void setup()
	{
		story = new TsscStory();
		story.setId(10);
		story.setBusinessValue(new BigDecimal(100));
		story.setPriority(new BigDecimal(30));
		story.setInitialSprint(new BigDecimal(300));
		
		stories = new TsscStory[3];
		
		TsscStory story1 = new TsscStory();
		story1.setId(20);
		TsscStory story2 = new TsscStory();
		story2.setId(20);
		
		stories[0] = story;
		stories[1] = story1;
		stories[2] = story2;
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
	
	@Test
	@DisplayName("Find All Stories Test")
	public void test3()
	{
		Mockito.when(restTemplate.getForObject(URL+"findAll", TsscStory[].class)).thenReturn(stories);
		List<Object> expectedList = Arrays.asList(stories);
		assertEquals(expectedList,storyDelegate.findAll());
	}
	
	@Test
	@DisplayName("Delete Story Test")
	public void test4()
	{
		storyDelegate.saveStory(story);
		storyDelegate.deleteStory(story.getId());
		Mockito.when(restTemplate.getForObject(URL+"findById/10", TsscStory.class)).thenReturn(null);
		assertEquals(null, storyDelegate.findById(story.getId()));
	}
	
	@Test
	@DisplayName("Edit Story Test")
	public void test5()
	{
		Mockito.when(restTemplate.getForObject(URL+"findById/10", TsscStory.class)).thenReturn(story);
		assertEquals(story, storyDelegate.findById(story.getId()));
		story.setDescription("New Description");
		storyDelegate.editStory(story);
		Mockito.when(restTemplate.getForObject(URL+"findById/10", TsscStory.class)).thenReturn(story);
		assertEquals(story, storyDelegate.findById(story.getId()));
		
	}
	
	@Test
	@DisplayName("Find by idGame")
	public void test6()
	{
		Mockito.when(restTemplate.getForObject(URL+"findByGameId/1", TsscStory[].class)).thenReturn(stories);
		List<Object> expectedList = Arrays.asList(stories);
		assertEquals(expectedList,storyDelegate.findByGameId(1));
	}
}
