package com.taxi.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NON_PRIVATE)
public class Request_record {
	@JsonProperty("request_id")
	private int request_id;
	
	@JsonProperty("address")
	private String address;
	
	@JsonProperty("marked")
	private boolean marked;
	
	@JsonProperty("request_time")
	private String request_time;
	
	@JsonProperty("marked_time")
	private String marked_time;
	
	@JsonProperty("user_id")
	private String user_id;
	
	public Request_record(int request_id,String address,boolean marked,
			String request_time,String marked_time,String user_id) {
		this.request_id = request_id;
		this.address = address;
		this.marked = marked;
		this.request_time = request_time;
		this.marked_time = marked_time;
		this.user_id = user_id;
	}
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getRequest_id() {
		return request_id;
	}
	public void setRequest_id(int request_id) {
		this.request_id = request_id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public boolean isMarked() {
		return marked;
	}
	public void setMarked(boolean marked) {
		this.marked = marked;
	}
	public String getRequest_time() {
		return request_time;
	}
	
	@JsonFormat(pattern ="yyyy-MM-dd / hh:mm:ss")
	public void setRequest_time(String request_time) {
		this.request_time = request_time;
	}
	public String getMarked_time() {
		return marked_time;
	}
	public void setMarked_time(String marked_time) {
		this.marked_time = marked_time;
	}
}