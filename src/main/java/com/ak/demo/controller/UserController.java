package com.ak.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ak.demo.exception.BadRequestException;
import com.ak.demo.model.User;
import com.ak.demo.repository.UserRepository;
import com.ak.demo.service.GroupUserDetailsService;
import com.ak.demo.so.LoginSO;
import com.ak.demo.util.JwtUtil;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
    private JwtUtil jwtUtil;
	
    @Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
    private UserRepository repository;
	
	@Autowired
    private GroupUserDetailsService userDetailService;

    
    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
    	return userDetailService.signup(user);
    }
    
    
    @GetMapping("/loadUsers")
    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> loadUsers() {
        return (List<User>) repository.findAll();
    }

    @GetMapping("/test")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String testUserAccess() {
        return "user can only access this !";
    }
    
    @PostMapping("/login")
    public String generateToken(@RequestBody LoginSO loginSO) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginSO.getUserName(), loginSO.getPassword())
            );
        } catch (Exception ex) {
            throw new BadRequestException("inavalid username/password");
        }
        return jwtUtil.generateToken(loginSO.getUserName());
    }
    
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to orchestration service !!";
    }
    

}
