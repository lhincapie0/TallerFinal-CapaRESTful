package co.edu.icesi.fi.tics.tssc.web;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughGroupsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughSprintsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingTopic;
import co.edu.icesi.fi.tics.tssc.exceptions.NullGameException;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.services.GameServiceImpl;

@RestController
public class TsscGameRestController {
	
	@Autowired
	private GameServiceImpl gameService;
	
	@GetMapping("api/games/findAll")
	public Iterable<TsscGame> findAll()
	{
		return gameService.findAll();
	}
	
	
	@GetMapping("api/games/findByDate/{date1}/{date2}")
	public Iterable<TsscGame> findByRange( @PathVariable(name = "date1") @DateTimeFormat(iso = ISO.DATE) LocalDate date1,
			@PathVariable(name = "date2") @DateTimeFormat(iso = ISO.DATE) LocalDate date2)
	{
		return gameService.findByDate(date1, date2);
	}
	
	@GetMapping("api/games/findByIdTopic/{id}")
	public Iterable<TsscGame> findByIdTopic(@PathVariable long id)
	{
		return gameService.findByIdTopic(id);
	}
	
	@GetMapping("/api/games/findById/{id}")
	public TsscGame findById(@PathVariable long id)
	{
		return gameService.findGameById(id);
	}
	
	@PostMapping("/api/games/add")
	public TsscGame addGame(@RequestBody TsscGame game) {
	
		try {
			return gameService.saveGame(game, game.getTsscTopic());
		} catch (NotEnoughGroupsException | NotEnoughSprintsException | NullGameException | NotExistingTopic e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@DeleteMapping("api/games/delete/{id}")
	public void deleteGame(@PathVariable long id)
	{
		gameService.deleteGame(gameService.findGameById(id));
	}
	
	@PutMapping("/api/games/edit")
	public TsscGame editGame(@RequestBody TsscGame game)
	{
		try {
			return gameService.editGame(game);
		} catch (NotExistingGameException | NullGameException | NotEnoughGroupsException | NotEnoughSprintsException
				| NotExistingTopic e) {
			e.printStackTrace();
			return null;
		}
	}

}
