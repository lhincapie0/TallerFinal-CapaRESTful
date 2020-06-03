package co.edu.icesi.fi.tics.tssc.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.icesi.fi.tics.tssc.delegates.TsscGameDelegate;
import co.edu.icesi.fi.tics.tssc.delegates.TsscTopicDelegate;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughGroupsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughSprintsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingTopic;
import co.edu.icesi.fi.tics.tssc.exceptions.NullGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullTopicException;
import co.edu.icesi.fi.tics.tssc.model.GameValidation;
import co.edu.icesi.fi.tics.tssc.model.RangeParams;
import co.edu.icesi.fi.tics.tssc.model.TopicValidation;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.services.AdminServiceImpl;
import co.edu.icesi.fi.tics.tssc.services.GameServiceImpl;
import co.edu.icesi.fi.tics.tssc.services.TopicServiceImpl;

@Controller
public class GameController {
	
	private TsscGameDelegate gameDelegate;
	private TsscTopicDelegate topicDelegate;
	
	@Autowired
	public GameController(	GameServiceImpl gameService, TopicServiceImpl topicService,
			TsscTopicDelegate topicDelegate, TsscGameDelegate gameDelegate)
	{
		this.topicDelegate = topicDelegate;
		this.gameDelegate = gameDelegate;
	}
	

	@GetMapping("/games/")
	public String indexUser(Model model) {
		model.addAttribute("games", gameDelegate.findAll());
		model.addAttribute("topics", topicDelegate.findAll());
		return "games/index";
	}
	
	

	@GetMapping("/games/filterByDate")
	public String filterByDate(Model model) {
		model.addAttribute("params", new RangeParams());
		return "games/filterByDate";
	}


	
	@PostMapping("/games/filterByDate")
	public String filt(@RequestParam(value = "action", required = true) String action,@ModelAttribute("params")  RangeParams params, Model model )
	{
		if (action != null && !action.equals("Cancel")) {
		model.addAttribute("games",gameDelegate.findByDate(params.getDate1(),params.getDate2()));
		model.addAttribute("topics", topicDelegate.findAll());
		return "games/games-range";
		}
		else return "redirect:/";

	}

	@GetMapping("games/add")
	public String addGame(Model model)
	{
		model.addAttribute("game", new TsscGame());
		TsscTopic mock = new TsscTopic();
		mock.setName("No relacionar tema");
		model.addAttribute("topics", topicDelegate.findAll());
		model.addAttribute("mock", mock);
		return "games/add-game";
	}
	
	@PostMapping("games/add")
	public String saveTopic(@RequestParam(value = "action", required = true) String action, @Validated(GameValidation.class) @ModelAttribute("game") TsscGame game,
			BindingResult bindingResult, Model model) throws NotEnoughGroupsException, NotEnoughSprintsException, NullGameException, NotExistingTopic 
	{
		System.out.println("TOPIC"+ game.getIdTopic());
		if(!action.equals("Cancelar"))
		{
			if(bindingResult.hasErrors())
			{
				TsscTopic mock = new TsscTopic();
				mock.setName("No relacionar tema");
				model.addAttribute("topics", topicDelegate.findAll());
				model.addAttribute("mock", mock);
				return "games/add-game";
			}else
			{
				if(game.getIdTopic() != 0)
				{
					game.setTsscTopic(topicDelegate.findById(game.getIdTopic()));
				}
				gameDelegate.saveGame(game);
			}
		}else {
			return "games/index";
		}
		return "redirect:/games/";
	}
	
	@GetMapping("/games/del/{id}")
	public String deleteGame(@PathVariable("id") long id) {
		TsscGame game =gameDelegate.findById(id);
		if (game == null)
			throw new IllegalArgumentException("Invalid Game Id: " + id);
		gameDelegate.deleteGame(id);

		return "redirect:/games/";
	}
	

	
	@GetMapping("/games/edit/{id}")
	public String editGameShowView(@PathVariable("id") long id, Model model) {
		TsscGame game = gameDelegate.findById(id);
		if (game == null)
			throw new IllegalArgumentException("Invalid Game Id: " + id);
		model.addAttribute("game", game);
		model.addAttribute("topics", topicDelegate.findAll());
		return "games/edit-game";
	}
	
	
	@PostMapping("/games/edit/{id}")
	public String editGame( @PathVariable("id") long id,
			@RequestParam(value = "action", required = true) String action, @Validated(GameValidation.class) @ModelAttribute("game") TsscGame game, BindingResult bindingResult,
			Model model) throws NotExistingGameException, NullGameException, NotEnoughGroupsException, NotEnoughSprintsException, NotExistingTopic {
		if (action != null && !action.equals("Cancelar")) {
			if (bindingResult.hasErrors()) {
				model.addAttribute("topics", topicDelegate.findAll());
				TsscTopic mock = new TsscTopic();
				model.addAttribute("mock", mock);
				return "/games/edit-game";
			} else {

				if(game.getIdTopic() != 0)
				{
					game.setTsscTopic(topicDelegate.findById(game.getIdTopic()));
				}
				gameDelegate.editGame(game);
			}
		}
		return "redirect:/games/";
	}

	
	@GetMapping("/games/getGames/{id}")
	public String getGamesByTopic(@PathVariable long id, Model model)
	{
		model.addAttribute("games", gameDelegate.findByIdTopic(id));
		model.addAttribute("topics", topicDelegate.findAll());
		return "games/indexGamesTopic";
	}

	
	
	
	

}
