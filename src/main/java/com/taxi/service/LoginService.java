package com.taxi.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxi.dao.DAO;
import com.taxi.domain.Taxi_user;

public class LoginService {
	
	private DAO dao= new DAO();
	
	public String login(Map<String, Object> map) {
		
		String user_id = (String)map.get("user_id");
		String user_pw = (String)map.get("user_pw");
		
		Taxi_user taxi_user = dao.loginUser(user_id, user_pw);
		
		ObjectMapper om = new ObjectMapper();
		String jsonString = "";
		try {
			jsonString = om.writeValueAsString(taxi_user);
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
				
		return jsonString;
	}
}