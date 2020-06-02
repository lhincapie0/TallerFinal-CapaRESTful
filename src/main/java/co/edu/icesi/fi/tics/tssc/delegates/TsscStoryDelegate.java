package co.edu.icesi.fi.tics.tssc.delegates;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;

@Component
public class TsscStoryDelegate implements ITsscStoryDelegate {

	public static final String URI = "http://localhost:8080/api/stories";
	
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

		return restTemplate.postForEntity("http://localhost:8080/api/stories/add", story, TsscStory.class).getBody();
	}

	@Override
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public TsscStory findById(long id) {
		return restTemplate.getForObject(URI+"/findById/"+id, TsscStory.class);
	}

	@Override
	public void deleteStory(long id) {
		restTemplate.delete(URI+"/delete/"+id);
		
	}

	@Override
	public Iterable<TsscStory> findAll() {

		TsscStory[] stories = restTemplate.getForObject("http://localhost:8080/api/stories/findAll", TsscStory[].class);

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

		TsscStory[] stories = restTemplate.getForObject("http://localhost:8080/api/stories/findByGameId/"+id, TsscStory[].class);

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
		
		System.out.println("entra al delegado");
		restTemplate.put(URI + "/edit", story, TsscStory.class);
		
	}

}
