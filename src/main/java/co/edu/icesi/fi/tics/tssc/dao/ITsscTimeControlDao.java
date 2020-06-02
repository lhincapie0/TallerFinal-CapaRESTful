package co.edu.icesi.fi.tics.tssc.dao;

import java.util.List;

import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.model.TsscTimecontrol;

public interface ITsscTimeControlDao {
	
	

	public void save(TsscTimecontrol entity);
	public void update(TsscTimecontrol enity);
	public void delete(TsscTimecontrol entity);
	public List<TsscTimecontrol> findAll();
	public TsscTimecontrol findById(long id);
	public List<TsscTimecontrol> findByGame(long idGame);


}
