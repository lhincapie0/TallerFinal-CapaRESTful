package co.icesi.edu.fi.tics.tssc.delegate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
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

import co.edu.icesi.fi.tics.tssc.delegates.TsscGameDelegate;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;

@RunWith(MockitoJUnitRunner.class)
public class TsscGameDelegateTest {
	
	
	public static final String URL = "http://localhost:8080/api/games/";

	@Mock
	private RestTemplate restTemplate;
	
	@InjectMocks
	private TsscGameDelegate gameDelegate;
	
	private TsscGame game;
	private TsscGame[] games;
	
	@Before
	public void setup()
	{
		game = new TsscGame();
		game.setId(10);
		game.setDescription("Juego a probar");
		game.setName("Game test");
		games = new TsscGame[3];
		games[0] = game;
		TsscGame game1 = new TsscGame();
		game1.setId(30);
		games[1] = game1;
		
		TsscGame game2 = new TsscGame();
		game2.setId(40);
		games[2] = game2;
	}
	

	@Test
	@DisplayName("Find game by id test")
	public void test1()
	{
			Mockito.when(restTemplate.getForObject(URL+"findById/10", TsscGame.class)).
			thenReturn(game);
			assertEquals(game, gameDelegate.findById(game.getId()));
		
		
	}
	
	
	@Test
	@DisplayName("Add game test")
	public void test2()
	{
		Mockito.when(restTemplate.postForObject(URL+"add", game, TsscGame.class)).thenReturn(game);
		assertEquals(game,gameDelegate.saveGame(game));
	}
	
	 @Test
	 @DisplayName("Find all games test")
	 public void test3()
	 {
			when(restTemplate.getForObject(URL+"findAll", TsscGame[].class)).thenReturn(games);
			List<Object> expectedList = Arrays.asList(games);
			assertEquals(expectedList, gameDelegate.findAll());
		
	 }
	 
	 @Test
	 @DisplayName("Delete Game Test")
	 public void test4()
	 {
		 gameDelegate.saveGame(game);
		 gameDelegate.deleteGame(game.getId());
		Mockito.when(restTemplate.getForObject(URL+"findById/10", TsscGame.class)).thenReturn(null);
		assertEquals(null, gameDelegate.findById(game.getId()));
	 }

	 @Test
	 @DisplayName("Edit Topic Test")
	 public void test5()
	 {
			Mockito.when(restTemplate.getForObject(URL+"findById/10", TsscGame.class)).thenReturn(game);
			assertEquals(game, gameDelegate.findById(game.getId()));
			game.setName("Nuevo nombre");
			game.setDescription("Nuevos cambios");
			gameDelegate.editGame(game);
			Mockito.when(restTemplate.getForObject(URL+"findById/10", TsscGame.class)).thenReturn(game);
			assertEquals(game, gameDelegate.findById(game.getId()));
	 }
	 
	 @Test
	 @DisplayName("Find by IdTopic Test")
	 public void test6()
	 {
		 games[2] = null;
		 games[1] = null;
		List<Object> expectedList = Arrays.asList(games);

		Mockito.when(restTemplate.getForObject(URL+"findByIdTopic/1", TsscGame[].class)).thenReturn(games);
		assertEquals(expectedList, gameDelegate.findByIdTopic(1));
		 
	 }
	 
	 @Test
	 @DisplayName("Find by Date Range Test")
	 public void test7() throws ParseException
	 {
		 games[2] = null;
		List<Object> expectedList = Arrays.asList(games);
		LocalDate date1 = LocalDate.parse("2019-12-27");
		LocalDate date2 = LocalDate.parse("2020-12-27");

		Mockito.when(restTemplate.getForObject(URL+"findByDate/"+date1+"/"+date2, TsscGame[].class)).thenReturn(games);
		assertEquals(expectedList, gameDelegate.findByDate(date1, date2));
		 
	 }
	 
	 
	 
}
