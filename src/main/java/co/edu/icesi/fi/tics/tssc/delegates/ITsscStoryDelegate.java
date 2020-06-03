package co.edu.icesi.fi.tics.tssc.delegates;

import java.time.LocalDate;

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
	public void editStory(TsscStory story);
	public Iterable<TsscStory> findByGameId(long id);
	Iterable<TsscStory> findByDate(LocalDate date1, LocalDate date2);
}
