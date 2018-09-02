package com.taxi.service;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxi.dao.DAO;
import com.taxi.domain.Request_record;

public class ResponseService {
	
	private DAO dao= new DAO();

	public String response(HttpServletRequest request) {
		int request_id = Integer.parseInt(request.getParameter("request_id"));
		
		boolean isMarked = dao.isMarked(request_id); 
		
		
		ObjectMapper om = new ObjectMapper();
		String jsonString = "";
		if (isMarked) {
			try {
				jsonString = om.writeValueAsString(-1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			int result = dao.markRequest(request_id);
			try {
				jsonString = om.writeValueAsString(result);
				
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
		
		
		return jsonString;
	}
}