package com.taxi.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxi.dao.DAO;

public class RequestService {
	
	private DAO dao= new DAO();
	
	public String requestService(Map<String, Object> map) {
		String user_id = (String)map.get("user_id");//클라이언트에서 보낸 user_id 값 찾기
		String address = (String)map.get("address");//클라이언트에서 보낸 address 값 찾기
		int result = -1;
		
		if (user_id==null || address==null || address.length()>100 || address.length()==0 ) {
			//주소가 100자 이상 , address 입력 안한경우, 키 값 입력 안 한 경우
			result = -1;
		}
		else {//100자 이하이면
			result = dao.insertRequest(address,user_id);//배차 요청, 결과 저장			
		}

		ObjectMapper om = new ObjectMapper();
		String jsonString = "";
		try {
			jsonString = om.writeValueAsString(result);//배차 요청 결과를 json으로 작성
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return jsonString;//작성한 json 리턴
	}

}
