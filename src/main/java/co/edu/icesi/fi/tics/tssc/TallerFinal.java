package co.edu.icesi.fi.tics.tssc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import co.edu.icesi.fi.tics.tssc.exceptions.BusinessValueException;
import co.edu.icesi.fi.tics.tssc.exceptions.InitialSprintException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughGroupsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughSprintsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingTopic;
import co.edu.icesi.fi.tics.tssc.exceptions.NullAdminException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullStoryException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullTopicException;
import co.edu.icesi.fi.tics.tssc.exceptions.PriorityException;
import co.edu.icesi.fi.tics.tssc.model.TsscAdmin;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.services.AdminServiceImpl;
import co.edu.icesi.fi.tics.tssc.services.GameServiceImpl;
import co.edu.icesi.fi.tics.tssc.services.StoryServiceImpl;
import co.edu.icesi.fi.tics.tssc.services.TopicServiceImpl;

@SpringBootApplication
public class TallerFinal {


	
	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}
	public static void main(String[] args) throws NullTopicException, NotEnoughGroupsException, NotEnoughSprintsException, NullGameException, NotExistingTopic, NullAdminException, NullStoryException, BusinessValueException, InitialSprintException, PriorityException, NotExistingGameException {

		ConfigurableApplicationContext c = SpringApplication.run(TallerFinal.class, args);
		AdminServiceImpl adminService = c.getBean(AdminServiceImpl.class);
		TopicServiceImpl topicService = c.getBean(TopicServiceImpl.class);
		GameServiceImpl gameService = c.getBean(GameServiceImpl.class);
		StoryServiceImpl storyService = c.getBean(StoryServiceImpl.class);
		
	/**	TsscAdmin admin1 = new TsscAdmin();
		admin1.setPassword("{noop}123");
		admin1.setUsername("Super Administrador");
		admin1.setUser("Super Administrador");
		admin1.setSuperAdmin("super");
		
		adminService.saveAdmin(admin1);
		
		
		TsscAdmin admin2 = new TsscAdmin();
		admin2.setPassword("{noop}123");
		admin2.setUsername("Administrador");
		admin2.setUser("Administrador");
		admin2.setSuperAdmin("admin");
		
		adminService.saveAdmin(admin2);
		TsscAdmin user = new TsscAdmin();
		user.setPassword("{noop}123");
		user.setUsername("Usuario");
		user.setUser("Usuario");
		user.setSuperAdmin("user");
		adminService.saveAdmin(user);**/
		


	}
	
	public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
}
