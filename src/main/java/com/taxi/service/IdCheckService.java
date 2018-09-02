package com.taxi.service;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxi.dao.DAO;

public class IdCheckService {
	private DAO dao= new DAO();

	public String idCheck(Map<String, Object> map) {
		String user_id = (String)map.get("user_id");//클라이언트에서 보낸 user_id 값 찾기
		ObjectMapper om = new ObjectMapper();
		String jsonString = "";
		if(user_id == null) {//id를 적지 않았으면
			try {
				jsonString = om.writeValueAsString(-1);//중복확인 실패
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			String idCheck = dao.idCheck(user_id);//id 중복 체크, 결과 저장			
			
			try {
				if (idCheck==null) {//id를 사용 할 수 있는 경우
					jsonString = om.writeValueAsString(1);//중복 체크 결과를 json으로 작성
				}
				else {//아이디를 사용 할 수 없는 경우
					jsonString = om.writeValueAsString(-1);//중복 체크 결과를 json으로 작성
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}						
		return jsonString;//작성한 json 리턴
	}
}