package com.taxi.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxi.dao.DAO;
import com.taxi.domain.Taxi_user;

public class JoinService {
	
	private DAO dao= new DAO();

	public String join(Map<String, Object> map) {
		
		String user_id = (String)map.get("user_id");
		String user_pw = (String)map.get("user_pw");
		String user_type = (String)map.get("user_type");
		
		Taxi_user userVO = new Taxi_user(user_id, user_pw, user_type);
		ObjectMapper om = new ObjectMapper();
		
		String resultString = "";
		try {
			int result = dao.joinUser(userVO);
			resultString = om.writeValueAsString(result);
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return resultString;
	}
}
/*String user_id = request.getParameter("user_id");
String user_pw = request.getParameter("user_pw");
String user_type = request.getParameter("user_type");*/

//Taxi_user userVO = new Taxi_user(userVO.getUser_id(), userVO.getUser_pw(), userVO.getUser_type());