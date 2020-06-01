package co.edu.icesi.fi.tics.tssc.delegates;

import org.springframework.web.client.RestTemplate;

import co.edu.icesi.fi.tics.tssc.model.TsscGame;

public interface ITsscGameDelegate {
	
	public RestTemplate getRestTemplate();
	public TsscGame saveGame(TsscGame game);
	public void setRestTemplate(RestTemplate restTemplate);
	public TsscGame findById(long id);
	public void deleteGame(long id);
	public Iterable<TsscGame> findAll();
	void editGame(TsscGame game);
	


}
