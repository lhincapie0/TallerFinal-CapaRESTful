package co.edu.icesi.fi.tics.tssc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;

@Repository
@Scope("singleton")
@Transactional
public class TsscTopicDao implements ITsscTopicDao{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<TsscTopic> findByName(String name) {
		
		String jpql = "SELECT a FROM TsscTopic a WHERE a.name = '"+name+"'";
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public List<TsscTopic> findByDescription(String description) {
		
		String jpql = "SELECT a FROM TsscTopic a WHERE a.description = '"+description+"'";
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public List<TsscTopic> findAll() {
		String jpql = "SELECT a FROM TsscTopic a";
		return entityManager.createQuery(jpql).getResultList();
	}


	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(TsscTopic topic) {
		
		unsubscribeTopic(topic);
		entityManager.remove(topic);
	}

	@Override
	public void update(TsscTopic topic) {
		entityManager.merge(topic);
		
	}

	@Override
	public void save(TsscTopic entity) {
		entityManager.persist(entity);
	}

	@Override
	public TsscTopic findById(long id) {
		
		return entityManager.find(TsscTopic.class,id);
	}
	
	public void unsubscribeTopic(TsscTopic topic)
	{
		List<TsscGame> gamesSubscribed = topic.getTsscGames();
		if(gamesSubscribed.size() > 0)
		{
			for(int i = 0; i<gamesSubscribed.size();i++)
			{
				gamesSubscribed.get(i).setTsscTopic(null);
				for(int j = 0; j< gamesSubscribed.get(i).getTsscStories().size();j++)
				{
					gamesSubscribed.get(i).getTsscStories().get(j).setTsscTopic(null);
				}
			}
		}
	}
	
}
