package co.edu.icesi.fi.tics.tssc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.icesi.fi.tics.tssc.dao.TsscGameDao;
import co.edu.icesi.fi.tics.tssc.dao.TsscTimeControlDao;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingTimecontrolException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullTimeControlException;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.model.TsscTimecontrol;

@Service
public class TimeControlServiceImpl implements TimeControlService {

	@Autowired
	private TsscTimeControlDao timecontrolDao;
	@Autowired
	private TsscGameDao gameDao;
	
	@Override
	public TsscTimecontrol saveTimecontrol(TsscTimecontrol timecontrol, TsscGame game) throws NotExistingGameException, NullTimeControlException, NullGameException {
		if(timecontrol != null) {
			
		if(game != null)
		{
			if(gameDao.findById(game.getId())!= null)
			{
				timecontrol.setTsscGame(game);
				timecontrolDao.save(timecontrol);
				return timecontrol;		
			}else throw new NotExistingGameException();
		}else throw new NullGameException();
		}else throw new NullTimeControlException();
	
		
		
	}

	@Override
	public TsscTimecontrol editTimecontrol(TsscTimecontrol timecontrol) throws NullTimeControlException, NotExistingTimecontrolException {
		if(timecontrol != null)
		{
			if(timecontrolDao.findById(timecontrol.getId())!= null){
				timecontrolDao.update(timecontrol);
				return timecontrol;
			}else throw new NotExistingTimecontrolException();
		}
		else throw new NullTimeControlException();
	}

	@Override
	public Iterable<TsscTimecontrol> findAll() {
		return timecontrolDao.findAll();
	}

	@Override
	public TsscTimecontrol findTimecontrolById(long id) {
		return timecontrolDao.findById(id);
	}

	@Override
	public void deleteTimeControl(TsscTimecontrol timecontrol) {
		timecontrolDao.delete(timecontrol);
	}

	@Override
	public Iterable<TsscTimecontrol> findByGame(long id) {
		return timecontrolDao.findByGame(id);
	}

}
