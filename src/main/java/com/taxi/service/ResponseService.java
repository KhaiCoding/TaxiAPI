package com.taxi.service;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxi.dao.DAO;

public class ResponseService {
	
	private DAO dao= new DAO();

	public String response(HttpServletRequest request) {
		String id_string = request.getParameter("request_id");
		ObjectMapper om = new ObjectMapper();
		String jsonString = "";
		if (id_string == null) {//request_id가 입력되지 않았으면
			try {
				jsonString = om.writeValueAsString(-1);	//-1을 json으로 작성		
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			int request_id = Integer.parseInt(id_string);//클라이언트에서 보낸 request_id 값 찾기
			
			boolean isMarked = dao.isMarked(request_id); //이미 배차 된 요청인지 확인
			//완료된 요청이면 isMarked에 true저장
			//대기중인 요청이면 isMarked에 false저장
		
			if (isMarked) {
				try {
					jsonString = om.writeValueAsString(-1);//완료된 요청이면 -1을 json으로 작성
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else {
				int result = dao.markRequest(request_id);//대기중인 요청이면 배차 진행 
				try {
					jsonString = om.writeValueAsString(result);//배차 결과를 json으로 작성
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}		
		}
			
		return jsonString;//작성한 json 리턴
	}
}