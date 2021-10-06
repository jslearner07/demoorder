package com.ak.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "USER_MST")
public class User {

		@Id
	    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_TKSK_SURVEY_GEN")
	    @SequenceGenerator(name="SEQ_TKSK_SURVEY_GEN", sequenceName="SEQ_TKSK_SURVEY_ID", allocationSize = 1)
	    @Column(name = "USER_ID", updatable = false, nullable = false)
	    private Long id;
	 
	    @Column(name = "USER_NAME")
	    @NotEmpty(message = "Username is required.")
	    @Size(min = 2, max = 35, message = "The length of username must be between 2 and 35 characters.")
	    private String userName;
	    
	    @NotNull(message = "Password is required.")
	    @Column(name = "PASSWORD")
	    private String password;
	    
	    @Column(name = "ACTIVE")
	    private String active;
	    
	    @Column(name = "ROLES")
	    private String roles;
	    
	    @Column(name = "CREATION_DATE", updatable = false)
	    private Date creationDate;


		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getActive() {
			return active;
		}

		public void setActive(String active) {
			this.active = active;
		}

		public String getRoles() {
			return roles;
		}

		public void setRoles(String roles) {
			this.roles = roles;
		}

		public Date getCreationDate() {
			return creationDate;
		}

		public void setCreationDate(Date creationDate) {
			this.creationDate = creationDate;
		}
	    
		
	    
}
