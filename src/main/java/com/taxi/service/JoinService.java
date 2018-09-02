package com.taxi.service;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxi.dao.DAO;
import com.taxi.domain.Taxi_user;

public class JoinService {
	
	private DAO dao= new DAO();

	public String join(Map<String, Object> map) {
		
		String user_id = (String)map.get("user_id");//클라이언트에서 보낸 user_id 값 찾기
		String user_pw = (String)map.get("user_pw");//클라이언트에서 보낸 user_pw 값 찾기
		String user_type = (String)map.get("user_type");//클라이언트에서 보낸 user_type 값 찾기
		
		Taxi_user userVO = new Taxi_user(user_id, user_pw, user_type);//user 객체 생성
		ObjectMapper om = new ObjectMapper();
		
		String resultString = "";
		try {
			int result = dao.joinUser(userVO);//회원 가입, 결과 저장
			resultString = om.writeValueAsString(result);//회원 가입 결과를 json으로 작성
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return resultString;//작성한 json 리턴
	}
}