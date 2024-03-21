package AppRun.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import AppRun.models.UserDetailsServiceEntity;
import AppRun.models.WalletUser;

import AppRun.daos.UserRepository;

@Service
public class UserDetailsDBService implements UserDetailsService {
	
	@Autowired
	UserRepository repo;
	
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		WalletUser user=repo.getUserByUserName(username);
		return new UserDetailsServiceEntity(user);
	}

}
