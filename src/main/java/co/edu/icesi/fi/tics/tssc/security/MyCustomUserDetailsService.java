package co.edu.icesi.fi.tics.tssc.security;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.edu.icesi.fi.tics.tssc.dao.TsscAdminDao;
import co.edu.icesi.fi.tics.tssc.model.TsscAdmin;



@Service
public class MyCustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private TsscAdminDao adminDao;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<TsscAdmin> resp = adminDao.findByUsername(username);
		 TsscAdmin tsscAdmin = resp.get(0);
		
		if (tsscAdmin != null) {
			User.UserBuilder builder = User.withUsername(username).password(tsscAdmin.getPassword()).roles(tsscAdmin.getSuperAdmin().toString());
			return builder.build();
		} else {
			throw new UsernameNotFoundException("User not found.");
		}
		
		
		
	}
}