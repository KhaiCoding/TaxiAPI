package com.taxi.service;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxi.dao.DAO;

public class IdCheckService {
	private DAO dao= new DAO();


	public String idCheck(Map<String, Object> map) {
		String user_id = (String)map.get("user_id");
		
		int result = dao.idCheck(user_id);
		
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
