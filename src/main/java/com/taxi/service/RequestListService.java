package com.taxi.service;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxi.dao.DAO;
import com.taxi.domain.Request_record;

public class RequestListService {
	private DAO dao= new DAO();

	public String requestList(){
		ArrayList<Request_record> requestList = new ArrayList<Request_record>();
		requestList = dao.getRequestList();
		
		ObjectMapper om = new ObjectMapper();
		String jsonString = "";
		try {
			jsonString = om.writeValueAsString(requestList);
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return jsonString;
	}
}