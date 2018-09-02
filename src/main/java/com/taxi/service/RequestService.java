package com.taxi.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxi.dao.DAO;

public class RequestService {
	
	private DAO dao= new DAO();
	
	public String requestService(Map<String, Object> map) {
		String user_id = (String)map.get("user_id");
		String address = (String)map.get("address");
		
		int result = dao.insertRequest(address,user_id);
		
		ObjectMapper om = new ObjectMapper();
		String jsonString = "";
		try {
			jsonString = om.writeValueAsString(result);
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return jsonString;
	}

}
