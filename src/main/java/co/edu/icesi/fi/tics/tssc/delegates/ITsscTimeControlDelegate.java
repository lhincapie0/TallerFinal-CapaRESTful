package co.edu.icesi.fi.tics.tssc.delegates;

import org.springframework.web.client.RestTemplate;

import co.edu.icesi.fi.tics.tssc.model.TsscTimecontrol;

public interface ITsscTimeControlDelegate {
	
	public RestTemplate getRestTemplate();
	public TsscTimecontrol saveTimeControl(TsscTimecontrol timecontrol);
	public void setRestTemplate(RestTemplate restTemplate);
	public TsscTimecontrol findById(long id);
	public void deleteTimeControl(long id);
	public Iterable<TsscTimecontrol> findAll();
	public void editTimeControl(TsscTimecontrol timecontrol);
	public Iterable<TsscTimecontrol> findTimeControlsByGame(long id);
}
