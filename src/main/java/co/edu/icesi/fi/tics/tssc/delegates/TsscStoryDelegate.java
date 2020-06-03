package co.edu.icesi.fi.tics.tssc.delegates;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;

@Component
public class TsscStoryDelegate implements ITsscStoryDelegate {

	public static final String URL = "http://localhost:8080/api/stories/";
	
	private RestTemplate restTemplate ;
	
	

	public TsscStoryDelegate()
	{
		this.restTemplate =  new RestTemplate();
	}

	@Override
	public RestTemplate getRestTemplate() {
		return this.restTemplate;
	}

	@Override
	public TsscStory saveStory(TsscStory story) {

		return restTemplate.postForObject(URL+"add", story, TsscStory.class);
	}

	@Override
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public TsscStory findById(long id) {
		return restTemplate.getForObject(URL+"findById/"+id, TsscStory.class);
	}

	@Override
	public void deleteStory(long id) {
		restTemplate.delete(URL+"delete/"+id);
		
	}

	@Override
	public Iterable<TsscStory> findAll() {

		TsscStory[] stories = restTemplate.getForObject(URL+"findAll", TsscStory[].class);

		List<TsscStory> listStories;
		try {
			listStories = Arrays.asList(stories);
			return listStories;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

	@Override
	public Iterable<TsscStory> findByGameId(long id) {

		TsscStory[] stories = restTemplate.getForObject(URL+"findByGameId/"+id, TsscStory[].class);

		List<TsscStory> listStories;
		try {
			listStories = Arrays.asList(stories);
			return listStories;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	@Override
	public void editStory(TsscStory story) {
		
		restTemplate.put(URL + "edit", story, TsscStory.class);
		
	}
	

	@Override
	public Iterable<TsscStory> findByDate(LocalDate date1, LocalDate date2) {
		TsscStory[] stories = restTemplate.getForObject(URL +"findByDate/"+date1+"/"+date2, TsscStory[].class);
		List<TsscStory> listStories;
		try {
			listStories = Arrays.asList(stories);
			return listStories;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

}
