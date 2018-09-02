package com.taxi.service;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxi.dao.DAO;
import com.taxi.domain.Request_record;

public class RequestListService {
	private DAO dao= new DAO();

	public String requestList(){
		ArrayList<Request_record> requestList = new ArrayList<Request_record>();//목록 list 생성
		requestList = dao.getRequestList();//목록 조회 후 저장
		
		ObjectMapper om = new ObjectMapper();
		String jsonString = "";
		try {
			jsonString = om.writeValueAsString(requestList);//목록 조회 결과를 json으로 작성
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return jsonString;//작성한 json 리턴
	}
}