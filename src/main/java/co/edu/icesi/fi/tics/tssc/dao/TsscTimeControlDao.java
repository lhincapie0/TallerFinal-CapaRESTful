package co.edu.icesi.fi.tics.tssc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.model.TsscTimecontrol;


@Repository
@Scope("singleton")
@Transactional
public class TsscTimeControlDao implements ITsscTimeControlDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(TsscTimecontrol timecontrol) {
		entityManager.persist(timecontrol);
		entityManager.flush();
	}

	@Override
	public void update(TsscTimecontrol timecontrol) {
		entityManager.merge(timecontrol);
	}

	@Override
	public void delete(TsscTimecontrol timecontrol) {
		entityManager.remove(timecontrol);
	}

	@Override
	public List<TsscTimecontrol> findAll() {
		String jpql = "SELECT a FROM TsscTimecontrol a";
		return entityManager.createQuery(jpql).getResultList();
	
	}

	@Override
	public TsscTimecontrol findById(long id) {
		return entityManager.find(TsscTimecontrol.class, id);
	}

	@Override
	public List<TsscTimecontrol> findByGame(long idGame) {
		String jpql = "SELECT a FROM TsscTimecontrol a WHERE a.tsscGame.id = '"+idGame+"'";
		return entityManager.createQuery(jpql).getResultList();

	}

}
