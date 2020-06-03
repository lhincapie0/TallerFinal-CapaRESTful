package co.edu.icesi.fi.tics.tssc.delegates;

import java.time.LocalDate;

import org.springframework.web.client.RestTemplate;

import co.edu.icesi.fi.tics.tssc.model.TsscGame;

public interface ITsscGameDelegate {
	
	public RestTemplate getRestTemplate();
	public TsscGame saveGame(TsscGame game);
	public void setRestTemplate(RestTemplate restTemplate);
	public TsscGame findById(long id);
	public void deleteGame(long id);
	public Iterable<TsscGame> findAll();
	public void editGame(TsscGame game);
	public Iterable<TsscGame> findByDate(LocalDate date1, LocalDate date2);
	


}
