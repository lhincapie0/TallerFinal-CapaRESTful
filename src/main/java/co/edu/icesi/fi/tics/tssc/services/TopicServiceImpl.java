package co.edu.icesi.fi.tics.tssc.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.icesi.fi.tics.tssc.dao.TsscGameDao;
import co.edu.icesi.fi.tics.tssc.dao.TsscTopicDao;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughGroupsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughSprintsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingTopic;
import co.edu.icesi.fi.tics.tssc.exceptions.NullTopicException;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;

@Service
public class TopicServiceImpl implements TopicService{

	@Autowired
	private TsscTopicDao topicDao;
	@Autowired 
	private TsscGameDao gameDao;
	
	@Transactional
	@Override
	public TsscTopic saveTopic(TsscTopic topic) throws NullTopicException, NotEnoughGroupsException, NotEnoughSprintsException{
		if(topic != null)
		{
			if(topic.getDefaultGroups()>0)
			{
				if(topic.getDefaultSprints()>0)
				{
					topicDao.save(topic);
					return topic;
				}else throw new NotEnoughSprintsException();
			}else throw new NotEnoughGroupsException();
				
		}else throw new NullTopicException();
			
	}

	@Override
	@Transactional
	public TsscTopic editTopic(TsscTopic topic) throws NullTopicException, NotExistingTopic, NotEnoughSprintsException, NotEnoughGroupsException{
		if(topic != null)
		{
			if(topicDao.findById((topic.getId()))!= null)
			{
				if(topic.getDefaultGroups()>0)
				{
					if(topic.getDefaultSprints()>0)
					{
						topicDao.update(topic);
						
					}else throw new NotEnoughSprintsException();
				}else throw new NotEnoughGroupsException();
				
				return topic;
			}else throw new NotExistingTopic();
		}else throw new NullTopicException();
	}
	
	@Override
	public Iterable<TsscTopic> findAll(){

		return topicDao.findAll();
	}

	
	@Override
	public List<TsscTopic> findTopicsByDate(LocalDate date){
		System.out.println("AL SERVICIO ENTRA" );

		List<Object[]> ans =gameDao.findTopicsByDate(date);
		List<TsscTopic> ret;
		
		if(ans.size()>0)
		{	TsscTopic[] topics = new TsscTopic[ans.size()];

			for(int i = 0; i<ans.size();i++)
			{
				long id = Long.parseLong(ans.get(i)[0].toString());
				TsscTopic topic = topicDao.findById(id);
				ans.get(i)[0] = topic;
				int scheduledGames = Integer.parseInt(ans.get(i)[1].toString());
				topic.setScheduledGames(scheduledGames);
				topics[i]= topic;
			}
			ret = Arrays.asList(topics);
			return ret;
		}else
		{
			return null;
		}
		
	}


	@Override
	public TsscTopic findTopicById(long id) {

		return topicDao.findById(id);
	}
	
	@Override 
	public void deleteTopic(TsscTopic topic)
	{
		topicDao.delete(topic);
	}

}
