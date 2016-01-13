package com.example.matrix_calculator.server;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class User {
	String userName;
	int password;
	String email;
	String phone;
	String gender;

	
	public User(String user, int password){
		
		this.userName=user;
		this.password=password;
		
		
		
	}
	
	public int getPassword(){
		
		return password;
		
	}
	
	public String getUserName(){
		return userName;
		
		
	}
	
	public String getPhone(){
		
		return phone;
		
	}
	
	
	public String getGender(){
		
		return gender;
		
	}
	
	
	public String getEmail(){
		
		return email;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
