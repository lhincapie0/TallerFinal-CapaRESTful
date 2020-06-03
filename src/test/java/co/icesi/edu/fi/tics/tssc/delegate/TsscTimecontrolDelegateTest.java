package co.icesi.edu.fi.tics.tssc.delegate;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import co.edu.icesi.fi.tics.tssc.model.TsscTimecontrol;

@RunWith(MockitoJUnitRunner.class)
public class TsscTimecontrolDelegateTest {


	public static final String URL = "http://localhost:8080/api/timecontrols/";

	@Mock
	private RestTemplate restTemplate;
	
	@InjectMocks
	private TsscTimeControlDelegate timecontrolDelegate;
	
	private TsscTimecontrol timecontrol;
	
	@Before
	public void setup()
	{
		
		timecontrol = new TsscTimecontrol();
		timecontrol.setId(20);
		timecontrol.setName("Cronograma de prueba");
	}
	
	@Test
	@DisplayName("Find timecontrol by id test")
	public void test1()
	{
		Mockito.when(restTemplate.getForObject(URL+"findById/20",TsscTimecontrol.class)).thenReturn(timecontrol);
		assertEquals(timecontrol, timecontrolDelegate.findById(20));
	}
	

}
