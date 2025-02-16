package com.ucsy.ams.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.ucsy.ams.entity.Account;
import com.ucsy.ams.repository.AccountRepo;

@Component
public class CustomAccountDetailsService implements UserDetailsService {

	@Autowired
	private AccountRepo accRepo;
	
	@Autowired
	private BCryptPasswordEncoder bcpwd;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Account user = accRepo.findByEmail(email);
		System.out.println(user);
		if (user == null) {
			throw new UsernameNotFoundException("user not found");
		} else {
			if(user.getRole().equals("ROLE_ADMIN")) {
				user.setPassword(bcpwd.encode(user.getPassword()));
			}
			
			return new CustomAccount(user);
		}

	}

}
