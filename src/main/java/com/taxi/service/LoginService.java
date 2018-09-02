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
		
		String user_id = (String)map.get("user_id");//클라이언트에서 보낸 user_id 값 찾기
		String user_pw = (String)map.get("user_pw");//클라이언트에서 보낸 user_pw 값 찾기
		
		Taxi_user taxi_user = dao.loginUser(user_id, user_pw);//로그인 , 결과 객체 저장
		
		ObjectMapper om = new ObjectMapper();
		String jsonString = "";
		try {
			if (taxi_user != null) {//로그인 성공
				jsonString = om.writeValueAsString(taxi_user);//user 객체를 json으로 작성
			}
			else {
				jsonString = om.writeValueAsString(-1);//로그인 실패한 경우 -1을 json으로 작성
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return jsonString;//작성한 json 리턴
	}
}