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

import co.edu.icesi.fi.tics.tssc.delegates.TsscTopicDelegate;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughGroupsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughSprintsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingTopic;
import co.edu.icesi.fi.tics.tssc.exceptions.NullTopicException;
import co.edu.icesi.fi.tics.tssc.model.RangeParams;
import co.edu.icesi.fi.tics.tssc.model.TopicValidation;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.services.TopicServiceImpl;
import lombok.extern.java.Log;


///BORRAR TOPICSERVICE
@Controller
public class TopicController {
	
	
	private TsscTopicDelegate topicDelegate;
	
	@Autowired
	public TopicController(TsscTopicDelegate topicDelegate)
	{
		this.topicDelegate = topicDelegate;
	}
	

	
	@GetMapping("/topics/")
	public String indexTopic(Model model) {
		model.addAttribute("topics", topicDelegate.findAll());
	
		return "topics/index";
	}

	@GetMapping("topics/filterByDate")
	public String filterByDateShow(Model model) {
		model.addAttribute("params", new RangeParams());
		return "topics/filterByDate";
	}
	

	
	@PostMapping("/topics/filterByDate")
	public String filt(@ModelAttribute("params")  RangeParams params, Model model )
	{
		Iterable<TsscTopic> topics = topicDelegate.findByDate(params.getDate1());
		model.addAttribute("topics",topicDelegate.findByDate(params.getDate1()));

		return "index";

	}


	@GetMapping("topics/add")
	public String addTopic(Model model)
	
	{
		model.addAttribute("topic", new TsscTopic());
		return "topics/add-topic";
	}
	
	@PostMapping("topics/add")
	public String saveTopic(@RequestParam(value = "action", required = true) String action, @Validated(TopicValidation.class) @ModelAttribute("topic") TsscTopic topic,
			BindingResult bindingResult, Model model) throws NullTopicException, NotEnoughGroupsException, NotEnoughSprintsException
	{
		if(!action.equals("Cancelar"))
		{
			if(bindingResult.hasErrors())
			{
				return "topics/add-topic";
			}else
			{
				topicDelegate.saveTopic(topic);
			}
		}else {
			return "topics/index";
		}
		return "redirect:/topics/";
	}
	

	@GetMapping("/topics/edit/{id}")
	public String editTopicShowView(@PathVariable("id") long id, Model model) {
		System.out.println(id);

		TsscTopic topic = topicDelegate.findById(id);
		if (topic == null)
			throw new IllegalArgumentException("Invalid Topic Id:" + id);
		model.addAttribute("topic", topic);
		model.addAttribute("mode", "edit1");
		return "topics/edit-topic";
	}
	
	@GetMapping("/topics/edit2/{id}")
	public String editTopic2ShowView(@PathVariable("id") long id, Model model) {
		System.out.println(id);
		TsscTopic topic = topicDelegate.findById(id);
		if (topic == null)
			throw new IllegalArgumentException("Invalid Topic Id:" + id);
		model.addAttribute("topic", topic);
		model.addAttribute("mode", null);
		return "topics/edit-topic";
	}
	
	@PostMapping("/topics/edit/{id}")
	public String editTopic( @PathVariable("id") long id,
			@RequestParam(value = "action", required = true) String action, @Validated(TopicValidation.class) @ModelAttribute("topic") TsscTopic topic, BindingResult bindingResult,
			Model model) throws NullTopicException, NotExistingTopic, NotEnoughSprintsException, NotEnoughGroupsException {
		if (action != null && !action.equals("Cancelar")) {
			if (bindingResult.hasErrors()) {
				return "/topics/edit-topic";
			} else {
				topicDelegate.editTopic(topic);
			}
		}
		if(action.equals("Actualizar Tema"))
		{
			return "redirect:/topics/";
		}else {
			return "redirect:/games/";

		}
	}

	
	@GetMapping("/topics/del/{id}")
	public String deleteTopic(@PathVariable("id") long id)
	{
		TsscTopic topic = topicDelegate.findById(id);
		if(topic == null) {
		 new IllegalArgumentException("Invalidad Topic id: " + id);
		}
		topicDelegate.deleteTopic(topic.getId());
		return "redirect:/topics/";
	}
	

}
