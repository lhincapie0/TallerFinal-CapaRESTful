package co.edu.icesi.fi.tics.tssc.controller;

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
import co.edu.icesi.fi.tics.tssc.delegates.TsscStoryDelegate;
import co.edu.icesi.fi.tics.tssc.exceptions.BusinessValueException;
import co.edu.icesi.fi.tics.tssc.exceptions.InitialSprintException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughGroupsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughSprintsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingStory;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingTopic;
import co.edu.icesi.fi.tics.tssc.exceptions.NullGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullStoryException;
import co.edu.icesi.fi.tics.tssc.exceptions.PriorityException;
import co.edu.icesi.fi.tics.tssc.model.GameValidation;
import co.edu.icesi.fi.tics.tssc.model.RangeParams;
import co.edu.icesi.fi.tics.tssc.model.StoryValidation;
import co.edu.icesi.fi.tics.tssc.model.StoryValidation2;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.services.GameServiceImpl;
import co.edu.icesi.fi.tics.tssc.services.StoryServiceImpl;

@Controller
public class StoryController {

	private TsscStoryDelegate storyDelegate;
	private TsscGameDelegate gameDelegate;
	
	@Autowired
	public StoryController(TsscStoryDelegate storyDelegate, TsscGameDelegate gameDelegate)
	{
		this.storyDelegate = storyDelegate;
		this.gameDelegate = gameDelegate;
	}
	
	@GetMapping("/stories/")
	public String indexStory(Model model) {
		model.addAttribute("stories", storyDelegate.findAll());
		model.addAttribute("games", gameDelegate.findAll());

		return "stories/index";
	}
	

	@GetMapping("/stories/list/{id}")
	public String indexStoriesOfGame(@PathVariable("id") long id,Model model) {
		model.addAttribute("stories", storyDelegate.findAll());
		model.addAttribute("games", gameDelegate.findAll());

		return "stories/index";
	}
	

	@GetMapping("stories/add")
	public String addStory(Model model)
	{
		model.addAttribute("story", new TsscStory());
		model.addAttribute("games", gameDelegate.findAll());
		return "stories/add-story";
	}
	

	@PostMapping("stories/add")
	public String saveStory(@RequestParam(value = "action", required = true) String action, @Validated(StoryValidation.class) @ModelAttribute("story") TsscStory story,
			BindingResult bindingResult, Model model) throws NullStoryException, BusinessValueException, InitialSprintException, PriorityException, NullGameException, NotExistingGameException 
	{
		if(!action.equals("Cancelar"))
		{
			if(bindingResult.hasErrors())
			{
				System.out.println("STORY "+story.getIdGame());
				System.out.println("STORY "+story.getTsscGame());


				model.addAttribute("games", gameDelegate.findAll());
				return "stories/add-story";
			}else
			{
				System.out.println(story.toString());

				storyDelegate.saveStory(story);
			}
		}else {
			return "stories/index";
		}
		return "redirect:/stories/";
	}
	
	@GetMapping("/stories/del/{id}")
	public String deleteStory(@PathVariable("id") long id) {
		TsscStory story =storyDelegate.findById(id);
		if(story == null)
			{
			 new IllegalArgumentException("Invalidad Story Id: "+id);
			}
		storyDelegate.deleteStory(id);
		return "redirect:/stories/";
	}
	

	
	@GetMapping("/stories/edit/{id}")
	public String editStoryShowView(@PathVariable("id") long id, Model model) {
		TsscStory story =storyDelegate.findById(id);
		if(story == null)
			{
			 new IllegalArgumentException("Invalidad Story Id: "+id);
			}
		model.addAttribute("story", story);
		model.addAttribute("games", gameDelegate.findAll());

		return "stories/edit-story";
	}
	
	@PostMapping("/stories/edit/{id}")
	public String editStory( @PathVariable("id") long id,
			@RequestParam(value = "action", required = true) String action, @Validated(StoryValidation2.class) @ModelAttribute("story") TsscStory story, BindingResult bindingResult,
			Model model) throws NullStoryException, NotExistingStory, BusinessValueException, PriorityException, InitialSprintException, NotExistingGameException  {
		if (action != null && !action.equals("Cancelar")) {
		
			story.setIdGame(2);
			story.setTsscGame(gameDelegate.findById(2));
			if (bindingResult.hasErrors()) {
				System.out.println(story.getIdGame());

				model.addAttribute("story", story);
				model.addAttribute("games", gameDelegate.findAll());
		
				return "/stories/edit-story";
			} else {
				System.out.println("AQUI ENTRA");
				System.out.println(story.toString());

				storyDelegate.editStory(story);
			}
		}
		return "redirect:/stories/";
	}
	
	
	@GetMapping("/stories/getStories/{id}")
	public String showStoriesForGames(@PathVariable("id")long id, Model model)
	{
		model.addAttribute("stories", storyDelegate.findByGameId(id));
		return "stories/indexStoriesGame";
		
	}
	

	@GetMapping("/stories/filterByDate")
	public String filterByDate(Model model) {
		model.addAttribute("params", new RangeParams());
		return "stories/filterByDate";
	}


	
	@PostMapping("/stories/filterByDate")
	public String filt(@ModelAttribute("params")  RangeParams params, Model model )
	{
		model.addAttribute("stories",storyDelegate.findByDate(params.getDate1(),params.getDate2()));
		model.addAttribute("games", gameDelegate.findAll());

		return "stories/stories-range";

	}

}
