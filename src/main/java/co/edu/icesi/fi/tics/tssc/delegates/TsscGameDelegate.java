package co.edu.icesi.fi.tics.tssc.delegates;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import lombok.extern.apachecommons.CommonsLog;

@Component
public class TsscGameDelegate implements ITsscGameDelegate{
	
public static final String URI = "http://localhost:8080/api/games";
	
	private RestTemplate restTemplate ;
	
	

	public TsscGameDelegate()
	{
		this.restTemplate =  new RestTemplate();
	}

	
	@Override
	public RestTemplate getRestTemplate()
	{
		return this.restTemplate;
	}
	
	@Override
	public TsscGame saveGame(TsscGame game)
	{
		return restTemplate.postForEntity("http://localhost:8080/api/games/add", game, TsscGame.class).getBody();
	}
	
	@Override
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@Override
	public TsscGame findById(long id)
	{
		TsscGame game = restTemplate.getForObject(URI+"/findById/"+id, TsscGame.class);
		return game;
	}

	@Override
	public void deleteGame(long id)
	{
		restTemplate.delete(URI+"/delete/"+id);
	}
	
	@Override
	public void editGame(TsscGame game)
	{
		restTemplate.put(URI + "/edit", game, TsscGame.class);
	}
	
	public Iterable<TsscGame> findAll()
	{
		TsscGame[] games = restTemplate.getForObject("http://localhost:8080/api/games/findAll", TsscGame[].class);

		List<TsscGame> listGames;
		try {
			listGames = Arrays.asList(games);
			return listGames;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
	

	public Iterable<TsscGame> findByIdTopic(long idTopic)
	{
		TsscGame[] games = restTemplate.getForObject("http://localhost:8080/api/games/findByIdTopic/"+idTopic, TsscGame[].class);

		List<TsscGame> listGames;
		try {
			listGames = Arrays.asList(games);
			return listGames;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	


}
