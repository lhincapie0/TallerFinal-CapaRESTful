package co.icesi.edu.fi.tics.tssc.delegate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import co.edu.icesi.fi.tics.tssc.delegates.TsscTimeControlDelegate;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.model.TsscTimecontrol;

@RunWith(MockitoJUnitRunner.class)
public class TsscTimecontrolDelegateTest {


	public static final String URL = "http://localhost:8080/api/timecontrols/";

	@Mock
	private RestTemplate restTemplate;
	
	@InjectMocks
	private TsscTimeControlDelegate timecontrolDelegate;
	
	private TsscTimecontrol timecontrol;
	private TsscTimecontrol[] timecontrols;
	
	@Before
	public void setup()
	{
		
		timecontrol = new TsscTimecontrol();
		timecontrol.setId(20);
		timecontrol.setName("Cronograma de prueba");
		TsscTimecontrol timecontrol1 = new TsscTimecontrol();
		timecontrol1.setId(30);
		TsscTimecontrol timecontrol2 = new TsscTimecontrol();
		timecontrol2.setId(40);
		timecontrols = new TsscTimecontrol[3];
		timecontrols[0] = timecontrol;
		timecontrols[1]  = timecontrol1;
		timecontrols[2] = timecontrol2;
	}
	
	@Test
	@DisplayName("Find timecontrol by id test")
	public void test1()
	{
		Mockito.when(restTemplate.getForObject(URL+"findById/20",TsscTimecontrol.class)).thenReturn(timecontrol);
		assertEquals(timecontrol, timecontrolDelegate.findById(20));
	}

	@Test
	@DisplayName("Add Timecontrol Test")
	public void test2()
	{
		Mockito.when(restTemplate.postForObject(URL+"add", timecontrol, TsscTimecontrol.class)).thenReturn(timecontrol);
		assertEquals(timecontrol,timecontrolDelegate.saveTimeControl(timecontrol));

	}
	
	@Test
	@DisplayName("Find All Timecontrols Test")
	public void test3()
	{
		when(restTemplate.getForObject(URL+"findAll", TsscTimecontrol[].class)).thenReturn(timecontrols);
		List<Object> expectedList = Arrays.asList(timecontrols);
		assertEquals(expectedList, timecontrolDelegate.findAll());
	}
	
	@Test
	@DisplayName("Delete Timecontrol Test")
	public void test4()
	{
		timecontrolDelegate.saveTimeControl(timecontrol);
		timecontrolDelegate.deleteTimeControl(timecontrol.getId());
		Mockito.when(restTemplate.getForObject(URL+"findById/20",TsscTimecontrol.class)).thenReturn(null);
		assertEquals(null,timecontrolDelegate.findById(timecontrol.getId()));
	}
	
	@Test
	@DisplayName("Edit Timecontrol Test")
	public void test5(){
		Mockito.when(restTemplate.getForObject(URL+"findById/20",TsscTimecontrol.class)).thenReturn(timecontrol);
		assertEquals(timecontrol, timecontrolDelegate.findById(20));
		timecontrol.setIntervalRunning(new BigDecimal(10));
		timecontrolDelegate.editTimeControl(timecontrol);
		Mockito.when(restTemplate.getForObject(URL+"findById/20",TsscTimecontrol.class)).thenReturn(timecontrol);
		assertEquals(timecontrol, timecontrolDelegate.findById(20));
	}
	
	
	@Test
	@DisplayName("Find  Timecontrols by IdGame Test")
	public void test6()
	{
		when(restTemplate.getForObject(URL+"findByGameId/1", TsscTimecontrol[].class)).thenReturn(timecontrols);
		List<Object> expectedList = Arrays.asList(timecontrols);
		assertEquals(expectedList, timecontrolDelegate.findTimeControlsByGame(1));
	}
	
	

}
