package com.taxi.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonIgnore;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NON_PRIVATE)
public class Taxi_user {
	@JsonProperty("user_id")
	private String user_id;
	
	@JsonProperty("user_pw")
	private String user_pw;
	
	@JsonProperty("user_type")
	private String user_type;
	
	@JsonIgnore
	private List<Request_record> requestList = new ArrayList<>();
	
	public Taxi_user(String user_id,String user_pw,String user_type) {
		this.user_id = user_id;
		this.user_pw = user_pw;
		this.user_type = user_type;
	}
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public List<Request_record> getRequestList() {
		return requestList;
	}
	
	public void setRequestList(List<Request_record> requestList) {
		this.requestList = requestList;
	}

	
}