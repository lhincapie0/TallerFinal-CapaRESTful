package co.edu.icesi.fi.tics.tssc.delegates;

import org.springframework.web.client.RestTemplate;

import co.edu.icesi.fi.tics.tssc.model.TsscTopic;

public interface ITsscTopicDelegate {

	public RestTemplate getRestTemplate();
	public void setRestTemplate (RestTemplate restTemplate);
	public TsscTopic saveTopic(TsscTopic topic);
	public TsscTopic findById(long id);
	public void deleteTopic(long id);
	public void editTopic(TsscTopic topic);
	public Iterable<TsscTopic> findAll();





}
