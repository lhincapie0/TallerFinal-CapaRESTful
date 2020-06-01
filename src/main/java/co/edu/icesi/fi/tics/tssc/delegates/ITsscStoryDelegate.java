package co.edu.icesi.fi.tics.tssc.delegates;

import org.springframework.web.client.RestTemplate;

import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;

public interface ITsscStoryDelegate {

	public RestTemplate getRestTemplate();
	public TsscStory saveStory(TsscStory story);
	public void setRestTemplate(RestTemplate restTemplate);
	public TsscStory findById(long id);
	public void deleteStory(long id);
	public Iterable<TsscStory> findAll();
	void editStory(TsscStory story);
	Iterable<TsscStory> findByGameId(long id);
}
