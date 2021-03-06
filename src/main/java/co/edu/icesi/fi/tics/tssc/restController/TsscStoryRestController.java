package co.edu.icesi.fi.tics.tssc.restController;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.edu.icesi.fi.tics.tssc.exceptions.BusinessValueException;
import co.edu.icesi.fi.tics.tssc.exceptions.InitialSprintException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingStory;
import co.edu.icesi.fi.tics.tssc.exceptions.NullGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullStoryException;
import co.edu.icesi.fi.tics.tssc.exceptions.PriorityException;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.services.StoryServiceImpl;

@RestController
public class TsscStoryRestController {
	
	@Autowired
	private StoryServiceImpl storyService;
	
	@GetMapping("api/stories/findAll")
	public Iterable<TsscStory> findAll()
	{
		return storyService.findAll();
	}
	@GetMapping("api/stories/findByGameId/{id}")
	public Iterable<TsscStory> findByGameId(@PathVariable long id)
	{
		return storyService.findByGame(id);
	}


	@GetMapping("api/stories/findById/{id}")
	public TsscStory findById(@PathVariable long id)
	{
		return storyService.findStoryById(id);
	}
	
	@PostMapping("api/stories/add")
	public TsscStory addStory(@RequestBody TsscStory story)
	{
		try {
			return storyService.saveStory(story, story.getTsscGame());
		} catch (NullStoryException | BusinessValueException | InitialSprintException | PriorityException
				| NullGameException | NotExistingGameException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@DeleteMapping("api/stories/delete/{id}")
	public void deleteStory(@PathVariable long id)
	{
		storyService.deleteStory(storyService.findStoryById(id));
	}
	
	
	
	@PutMapping("/api/stories/edit")
	public TsscStory editStory(@RequestBody TsscStory story)
	{
		try {
			System.out.println("entra al server" + story);
			System.out.println(storyService);

			return storyService.editStory(story);
		} catch (NullStoryException | NotExistingStory | BusinessValueException | PriorityException
				| InitialSprintException | NotExistingGameException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping("api/stories/findByDate/{date1}/{date2}")
	public Iterable<TsscStory> findByRange( @PathVariable(name = "date1") @DateTimeFormat(iso = ISO.DATE) LocalDate date1,
			@PathVariable(name = "date2") @DateTimeFormat(iso = ISO.DATE) LocalDate date2)
	{
		return storyService.findByDate(date1, date2);
	}
	
	
}
