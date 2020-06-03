package co.edu.icesi.fi.tics.tssc.dao;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.fi.tics.tssc.model.TsscGame;

@Repository
@Scope("singleton")
@Transactional
public class TsscGameDao implements ITsscGameDao{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(TsscGame game) {
		
		if(game.getTsscStories()!= null) {
			for(int i = 0; i<game.getTsscStories().size();i++)
			{
				entityManager.remove(game.getTsscStories().get(i));

			}
		}
		entityManager.remove(game);
	}

	@Override
	public void save(TsscGame entity) {
		entityManager.persist(entity);
	}

	@Override
	public void update(TsscGame entity) {
		entityManager.merge(entity);
	}

	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public TsscGame findById(long id) {
		TsscGame game =  entityManager.find(TsscGame.class,id);
		System.out.println("ENTITY "+game);
		return game;
	}
	


	@Override
	public List<TsscGame> findAll() {
		String jpql = "SELECT a FROM TsscGame a";
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public List<TsscGame> findByName(String name) {
		String jpql = "SELECT a FROM TsscGame a WHERE a.name = '"+ name+"'";
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public List<TsscGame> findByDescription(String description) {
		String jpql = "SELECT a FROM TsscGame a WHERE a.description = '"+description+"'";
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public List<TsscGame> findByTopic(long idTopic) {
		String jpql = "SELECT a FROM TsscGame a WHERE a.tsscTopic.id = '"+idTopic+"'";
		return entityManager.createQuery(jpql).getResultList();
	}

	/*
	 * El(los) temas (es) con sus datos y cantidad de juegos programados (para una fecha dada) ordenados por hora
	 * . Recibe como parÃ¡metro la fecha dada y muestra todos los temas que cumplen.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findTopicsByDate(LocalDate date) {
		
		String jpql = ""
				+ "SELECT b.tsscTopic.id , count(b.tsscTopic) FROM TsscGame b "
				+ "WHERE b.id IN (SELECT a.id from TsscGame a WHERE a.scheduledDate = :date"
				+ ") GROUP BY b.tsscTopic.id ORDER BY MAX(b.scheduledTime)";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("date", date);
		List<Object[]> results = query.getResultList();

		return results;
	}
	
	
	@Override
	public List<TsscGame> findByDateRange(LocalDate date1, LocalDate date2) {
		String jpql = "SELECT a FROM TsscGame a WHERE a.scheduledDate <= :date2 AND a.scheduledDate >= :date1";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("date1", date1);
		query.setParameter("date2", date2);

		List<TsscGame> results = query.getResultList();
		return results;
	}

	@Override
	public List<TsscGame> findByDateAndTimeRange(LocalDate date, LocalTime time1, LocalTime time2) {
		String jpql = "SELECT a FROM TsscGame a WHERE a.scheduledDate ='"+date+"' AND a.scheduledTime >= '"+time1+
				"' AND a.scheduledTime <= '"+time2+"'";
		Query query = entityManager.createQuery(jpql);

		@SuppressWarnings("unchecked")
		List<TsscGame> results = query.getResultList();
		return results;
	}
	
	
	/**
	 * Devuelve los juegos en la fecha dada con menos de 10 historias o sin ningun cronometro
	 * @param date, fecha en la que esta programado el juego
	 */
	@Override
	public List<TsscGame> findByNoStoriesNoTimeControls(LocalDate date)
	{
		String jpql = "SELECT a FROM TsscGame a WHERE a.scheduledDate ='"+date+"' AND (size(a.tsscStories)<10 "
				+ "OR size(a.tsscTimecontrols) = 0)";
		Query query = entityManager.createQuery(jpql);


		@SuppressWarnings("unchecked")
		List<TsscGame> results = query.getResultList();
		return results;
	}

	
	
}
