package co.edu.icesi.fi.tics.tssc.delegates;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;

@Component
public class TsscTopicDelegate implements ITsscTopicDelegate{
	
	public static final String URL = "http://localhost:8080/api/topics/";
	
	private RestTemplate restTemplate ;
	
	
	public TsscTopicDelegate()
	{
		this.restTemplate =  new RestTemplate();

	}
	

	@Override
	public RestTemplate getRestTemplate()
	{
		return this.restTemplate;
	}
	
	@Override
	public TsscTopic saveTopic(TsscTopic topic)
	{
		return restTemplate.postForObject(URL+"add", topic, TsscTopic.class);
	}
	
	@Override
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@Override
	public TsscTopic findById(long id)
	{
		TsscTopic topic = restTemplate.getForObject(URL+"findById/"+id, TsscTopic.class);
		return topic;
	}

	@Override
	public void deleteTopic(long id)
	{
		restTemplate.delete(URL+"delete/"+id);
	}
	
	
	@Override
	public void editTopic(TsscTopic topic)
	{
		restTemplate.put(URL + "edit", topic, TsscTopic.class);
	}
	
	
	public Iterable<TsscTopic> findByDate(LocalDate date1) {
		TsscTopic[] topics = restTemplate.getForObject(URL +"findByDate/"+date1, TsscTopic[].class);
		List<TsscTopic> listTopics;
		try {
			listTopics = Arrays.asList(topics);
			return listTopics;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	


	@Override
	public Iterable<TsscTopic> findAll()
	{
		TsscTopic[] topics = restTemplate.getForObject(URL+"findAll", TsscTopic[].class);

		List<TsscTopic> listTopics;
		try {
			listTopics = Arrays.asList(topics);
			return listTopics;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	

}
