package co.edu.icesi.fi.tics.tssc.controller;

import java.util.List;

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
import co.edu.icesi.fi.tics.tssc.delegates.TsscTimeControlDelegate;
import co.edu.icesi.fi.tics.tssc.exceptions.BusinessValueException;
import co.edu.icesi.fi.tics.tssc.exceptions.InitialSprintException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingStory;
import co.edu.icesi.fi.tics.tssc.exceptions.NullGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullStoryException;
import co.edu.icesi.fi.tics.tssc.exceptions.PriorityException;
import co.edu.icesi.fi.tics.tssc.model.StoryValidation;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.model.TsscTimecontrol;

@Controller
public class TimeControlController {
	
	

	private TsscTimeControlDelegate timecontrolDelegate;
	private TsscGameDelegate gameDelegate;
	private long actualIdGame;
	
	@Autowired
	public TimeControlController(TsscTimeControlDelegate timecontrolDelegate, TsscGameDelegate gameDelegate)
	{
		this.timecontrolDelegate = timecontrolDelegate;
		this.gameDelegate = gameDelegate;
	}
	
	@GetMapping("/timecontrols/")
	public String indexTimecontrol(Model model) {
		model.addAttribute("timecontrols", timecontrolDelegate.findAll());

		return "timecontrols/index";
	}
	
	@GetMapping("/timecontrols/getTimecontrols/{id}")
	public String showTimecontrolsForGame(@PathVariable("id")long id, Model model)
	{

		Iterable<TsscTimecontrol> timecontrols = timecontrolDelegate.findTimeControlsByGame(id);
		model.addAttribute("timecontrols", timecontrolDelegate.findTimeControlsByGame(id));
		actualIdGame = id;
		return "timecontrols/index";
		
	}
	
	@GetMapping("timecontrols/add")
	public String addTimeControl(Model model)
	{
		model.addAttribute("timecontrol", new TsscTimecontrol());
		return "timecontrols/add-timecontrol";
	}
	

	@PostMapping("timecontrols/add")
	public String saveStory(@RequestParam(value = "action", required = true) String action, @ModelAttribute("timecontrol") TsscTimecontrol timecontrol,
			BindingResult bindingResult, Model model) 
	{
		if(!action.equals("Cancelar"))
		{
			System.out.println(actualIdGame);
			TsscGame game = gameDelegate.findById(actualIdGame);
			timecontrol.setTsscGame(game);
			System.out.println(game);
			//timecontrolDelegate.saveTimeControl(timecontrol);
			
		}else {
			return "timecontrols/index";
		}
		return "redirect:/timecontrols/";
	}
	
	@GetMapping("/timecontrols/edit/{id}")
	public String editTimecontrolShow(@PathVariable("id") long id, Model model) {
		TsscTimecontrol timecontrol =timecontrolDelegate.findById(id);
		if(timecontrol == null)
			{
			 new IllegalArgumentException("Invalidad Story Id: "+id);
			}
		model.addAttribute("timecontrol", timecontrol);
		return "timecontrols/edit-timecontrol";
	}
	
	@PostMapping("/timecontrols/edit/{id}")
	public String editTimecontrolShow( @PathVariable("id") long id,
			@RequestParam(value = "action", required = true) String action, @ModelAttribute("timecontrol") TsscTimecontrol timecontrol,
			Model model) throws NullStoryException, NotExistingStory, BusinessValueException, PriorityException, InitialSprintException, NotExistingGameException  {
		if (action != null && !action.equals("Cancelar")) {
			
				timecontrolDelegate.editTimeControl(timecontrol);
			
		}
		return "redirect:/stories/";
	}

}
