package co.edu.icesi.fi.tics.tssc.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoggingAccessDeniedHandler accessDeniedHandler;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.csrf().disable().authorizeRequests().antMatchers("/index/**").hasAnyRole("super","admin","user")
		.antMatchers("/topics/**").hasRole("super")

		.antMatchers("/games/").hasAnyRole("super","admin","user")
		.antMatchers("/games/add-topic").hasAnyRole("super","admin")

		.antMatchers("/stories/").hasAnyRole("admin","super","user")
		.antMatchers("/api/**").permitAll()

		.antMatchers("/stories/add-story").hasAnyRole("admin","super")
		.antMatchers("/h2-console/**").hasRole("super").antMatchers("/console/**").hasRole("super")
		
		.anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll().and().logout()
		.invalidateHttpSession(true).clearAuthentication(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
		.permitAll().and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);

		
		
	}
	
   
	
	
}