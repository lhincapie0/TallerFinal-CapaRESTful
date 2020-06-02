package co.edu.icesi.fi.tics.tssc.services;

import co.edu.icesi.fi.tics.tssc.exceptions.BusinessValueException;
import co.edu.icesi.fi.tics.tssc.exceptions.InitialSprintException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingStory;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingTimecontrolException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullStoryException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullTimeControlException;
import co.edu.icesi.fi.tics.tssc.exceptions.PriorityException;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.model.TsscTimecontrol;

public interface TimeControlService {


	public TsscTimecontrol saveTimecontrol(TsscTimecontrol timecontrol, TsscGame game) throws NotExistingGameException, NullTimeControlException, NullGameException;
	public TsscTimecontrol editTimecontrol(TsscTimecontrol timecontrol) throws NullTimeControlException, NotExistingTimecontrolException;
	public Iterable<TsscTimecontrol> findAll();
	public TsscTimecontrol findTimecontrolById(long id);
	public void deleteTimeControl(TsscTimecontrol timecontrol);
	public Iterable<TsscTimecontrol> findByGame(long id);
}
