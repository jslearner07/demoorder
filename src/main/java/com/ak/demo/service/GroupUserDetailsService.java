package com.ak.demo.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ak.demo.common.UserRoleConstant;
import com.ak.demo.exception.BadRequestException;
import com.ak.demo.model.User;
import com.ak.demo.repository.UserRepository;
import com.ak.demo.so.GroupUserDetailsSO;

@Service
public class GroupUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = repository.findByUserName(username);
		return user.map(GroupUserDetailsSO::new)
				.orElseThrow(() -> new UsernameNotFoundException(username + " Not Found"));
	}

	public String signup(User user) {
		if (user != null && ObjectUtils.isEmpty(user.getUserName())) {
			if (repository.findByUserName(user.getUserName()).isPresent()) {
				throw new BadRequestException("User already exist " + user.getUserName());
			}
		}
		user.setRoles(UserRoleConstant.DEFAULT_ROLE);// USER
		String encryptedPwd = passwordEncoder.encode(user.getPassword());
		user.setPassword(encryptedPwd);
		user.setCreationDate(new Date());
		repository.save(user);
		return "Hi " + user.getUserName() + " welcome to group !";
	}

}
