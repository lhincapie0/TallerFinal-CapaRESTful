package co.edu.icesi.fi.tics.tssc.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingTimecontrolException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullTimeControlException;
import co.edu.icesi.fi.tics.tssc.model.TsscTimecontrol;
import co.edu.icesi.fi.tics.tssc.services.TimeControlServiceImpl;

@RestController
public class TsscTimeControlRestController {
	
	@Autowired
	private TimeControlServiceImpl timecontrolService;
	
	@GetMapping("api/timecontrols/findAll")
	public Iterable<TsscTimecontrol> findAll()
	{
		return timecontrolService.findAll();
	}
	
	@GetMapping("api/timecontrols/findByGameId/{id}")
	public Iterable<TsscTimecontrol> findByGameId(@PathVariable long id)
	{
		return timecontrolService.findByGame(id);
	}
	
	@PostMapping("api/timecontrols/add")
	public TsscTimecontrol saveTimecontrol(@RequestBody TsscTimecontrol timecontrol)
	{
		try {
			return timecontrolService.saveTimecontrol(timecontrol, timecontrol.getTsscGame());
		} catch (NotExistingGameException | NullTimeControlException | NullGameException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PutMapping("api/timecontrols/edit")
	public TsscTimecontrol editTimecontrol(@RequestBody TsscTimecontrol timecontrol)
	{
		try {
			return timecontrolService.editTimecontrol(timecontrol);
		} catch (NullTimeControlException | NotExistingTimecontrolException e) {
			e.printStackTrace();
			return null;
		}
	}
	@GetMapping("api/timecontrols/findById/{id}")
	public TsscTimecontrol findById(@PathVariable long id)
	{
		return timecontrolService.findTimecontrolById(id);
	}

	@DeleteMapping("api/timecontrols/delete/{id}")
	public void deleteTimecontrol(@PathVariable long id) {
		timecontrolService.deleteTimeControl(timecontrolService.findTimecontrolById(id));
	}
}
