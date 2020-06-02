package co.edu.icesi.fi.tics.tssc.delegates;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.model.TsscTimecontrol;

@Component
public class TsscTimeControlDelegate implements ITsscTimeControlDelegate{

	
	public static final String URI = "http://localhost:8080/api/timecontrols";
	
	private RestTemplate restTemplate ;
	
	public  TsscTimeControlDelegate() {
		this.restTemplate =  new RestTemplate();

	}
	
	@Override
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	@Override
	public TsscTimecontrol saveTimeControl(TsscTimecontrol timecontrol) {
		return restTemplate.postForEntity("http://localhost:8080/api/timecontrols/add", timecontrol, TsscTimecontrol.class).getBody();

	}

	@Override
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public TsscTimecontrol findById(long id) {
		return restTemplate.getForObject(URI+"/findById/"+id, TsscTimecontrol.class);

	}

	@Override
	public void deleteTimeControl(long id) {
		restTemplate.delete(URI+"/delete/"+id);
		
	}

	@Override
	public Iterable<TsscTimecontrol> findAll() {
		TsscTimecontrol[] timecontrols = restTemplate.getForObject("http://localhost:8080/api/timecontrols/findAll", TsscTimecontrol[].class);

		List<TsscTimecontrol> listtimecontrols;
		try {
			listtimecontrols = Arrays.asList(timecontrols);
			return listtimecontrols;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	

	@Override
	public void editTimeControl(TsscTimecontrol timecontrol) {
		restTemplate.put(URI + "/edit", timecontrol, TsscTimecontrol.class);
		
	}

	@Override
	public Iterable<TsscTimecontrol> findTimeControlsByGame(long id) {
		TsscTimecontrol[] timecontrols = restTemplate.getForObject("http://localhost:8080/api/timecontrols/findByGameId/"+id, TsscTimecontrol[].class);

		List<TsscTimecontrol> listtimecontrols;
		try {
			listtimecontrols = Arrays.asList(timecontrols);
			return listtimecontrols;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
