package co.edu.icesi.fi.tics.tssc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughGroupsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughSprintsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingTopic;
import co.edu.icesi.fi.tics.tssc.exceptions.NullTopicException;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.services.TopicService;
import co.edu.icesi.fi.tics.tssc.services.TopicServiceImpl;

@RestController
public class TsscTopicRestController {

	
		@Autowired
		private TopicServiceImpl topicService;
		
		@GetMapping("/api/topics/findAll")
		public Iterable<TsscTopic> findAll()
		{
			return topicService.findAll();
		}
		
		@GetMapping("/api/topics/findById/{id}")
		public TsscTopic findById(@PathVariable long id)
		{
			System.out.println("AQUI ENTRA");
			return topicService.findTopicById(id);
		}
	
		@PostMapping("/api/topics/add")
		public TsscTopic addTopic(@RequestBody TsscTopic topic)
		{
			try {
				return topicService.saveTopic(topic);
			} catch (NullTopicException | NotEnoughGroupsException | NotEnoughSprintsException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		
		@DeleteMapping("/api/topics/delete/{id}")
		public void deleteTopic(@PathVariable long id)
		{
			topicService.deleteTopic(topicService.findTopicById(id));
		}
		
		
		
		//UPDATE
		@PutMapping("/api/topics/edit")
		public TsscTopic editTopic(@RequestBody TsscTopic topic) {
			try {
				return topicService.editTopic(topic);
			} catch (NullTopicException | NotExistingTopic | NotEnoughSprintsException | NotEnoughGroupsException e) {
				e.printStackTrace();
				return null;
			}
		}
	
		
	
}

