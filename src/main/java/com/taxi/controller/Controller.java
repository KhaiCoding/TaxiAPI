package com.taxi.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taxi.service.IdCheckService;
import com.taxi.service.JoinService;
import com.taxi.service.LoginService;
import com.taxi.service.RequestListService;
import com.taxi.service.RequestService;
import com.taxi.service.ResponseService;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {
    
	@RequestMapping(value = "/api/user/join",method=RequestMethod.POST)//회원가입
	public String join(@RequestBody Map<String, Object> map) {
		JoinService joinService = new JoinService();
		return joinService.join(map);
	}
	
	@RequestMapping(value = "/api/user/idCheck",method=RequestMethod.POST)//아이디 중복확인
	public String idCheck(@RequestBody Map<String, Object> map) {
		IdCheckService idCheckService = new IdCheckService();
		return idCheckService.idCheck(map);
	}
	
    @RequestMapping(value="/api/user/login",method=RequestMethod.POST)//로그인
    public String login(@RequestBody Map<String, Object> map) {
    	LoginService loginService = new LoginService();
    	return loginService.login(map);
    }
    
    @RequestMapping(value="api/taxi/allList",method=RequestMethod.GET)//목록조회
    public String allList(){
    	RequestListService requestListService = new RequestListService();
    	return requestListService.requestList();
    }
    
    @RequestMapping(value="api/taxi/request",method=RequestMethod.POST)//택시 배차 요청
    public String request(@RequestBody Map<String, Object> map){
    	RequestService requestService = new RequestService();
    	return requestService.requestService(map);
    }
    
    @RequestMapping(value="api/taxi/response",method=RequestMethod.GET)//기사 배차
    public String response(HttpServletRequest request){
    	ResponseService responseService = new ResponseService();
    	return responseService.response(request);
    }
    
}