package com.taxi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.taxi.domain.Request_record;
import com.taxi.domain.Taxi_user;

public class DAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public DAO(){
		try {
			String dbURL = "jdbc:mysql://localhost:3306/taxi";
			String dbId = "root";
			String dbPw = "1111";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL,dbId,dbPw);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	public int insertRequest(String address,String user_id){//승객이 요청을 보냄
		String SQL = "insert into taxi.request_record(address,marked,request_time,marked_time,user_id) "
				+ "values(?,?,(select now()),?,?)";
		try{
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,address);
			pstmt.setBoolean(2, false);
			pstmt.setString(3,"");
			pstmt.setString(4,user_id);

			return pstmt.executeUpdate();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return -1;
	}
	public ArrayList<Request_record> getRequestList(){//새로고침을 클릭했을 때, 새로운 요청 목록 가져오기
		ArrayList<Request_record> requestRecordVOArrayList = new ArrayList<Request_record>();
		String SQL = "select * from taxi.request_record order by request_id desc";
		try{
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();

			while (rs.next()){
				int request_id = rs.getInt("request_id");
				String address = rs.getString("address");
				boolean marked = rs.getBoolean("marked");
				String request_time = rs.getString("request_time");
				String marked_time = rs.getString("marked_time");
				String user_id = rs.getString("user_id");

				Request_record requestRecordVO = new Request_record(request_id,address,marked,request_time,marked_time,user_id);
				requestRecordVOArrayList.add(requestRecordVO);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return requestRecordVOArrayList;
	}
	public int markRequest(int requestId){//기사가 마크 요청을 보냈을때 요청을 업데이트
		String SQL = "update taxi.request_record set marked = true where request_id = ?";
		String SQL2 = "update taxi.request_record set marked_time = (select now()) where request_id = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,requestId);
			pstmt.executeUpdate();

			pstmt = conn.prepareStatement(SQL2);
			pstmt.setInt(1, requestId);
			return pstmt.executeUpdate();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return -1;
	}
	public boolean isMarked(int requestId){//요청 1개 가져오기
		String SQL = "select marked from taxi.request_record where request_id = ?";
		boolean isMarked = false;
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,requestId);
			rs = pstmt.executeQuery();
			if(rs.next()){
				return rs.getBoolean("marked");
				//userVO = (Taxi_user) rs.getObject(1);//로그인 성공
			}
			else{//비밀번호 불일치
				isMarked = false;
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return isMarked;
	}
	public int joinUser(Taxi_user userVO){//회원가입
		String SQL = "insert into taxi.taxi_user(user_id,user_pw,user_type) values(?,?,?)";
		try{
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,userVO.getUser_id());
			pstmt.setString(2,userVO.getUser_pw());
			pstmt.setString(3,userVO.getUser_type());
			return pstmt.executeUpdate();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return -1;//executeUpdate 가 정상 동작 했다면 update된 행의 개수가 나온다. 그러면 -1이 나올수가 없다. 나오게 되면 오류
	}
	public Taxi_user loginUser(String user_id,String user_pw){
		String SQL = "select * from taxi.taxi_user where user_id = ? and user_pw = ?";
		Taxi_user userVO = null;
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,user_id);
			pstmt.setString(2,user_pw);
			rs = pstmt.executeQuery();

			if(rs.next()){
				if(rs.getObject(1)!=null){
					String id = rs.getString("user_id");
					String pw = rs.getString("user_pw");
					String type = rs.getString("user_type");

					userVO = new Taxi_user(id, pw, type);

					//userVO = (Taxi_user) rs.getObject(1);//로그인 성공
				}
				else{//비밀번호 불일치
					userVO = null;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return userVO;
	}

	public int idCheck(String user_id) {
		String SQL = "select * from taxi.taxi_user where user_id = ?";
		Taxi_user userVO = null;
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,user_id);
			rs = pstmt.executeQuery();

			if(rs.next()){
				if(rs.getObject(1)!=null){
					String id = rs.getString("user_id");
					String pw = rs.getString("user_pw");
					String type = rs.getString("user_type");

					return -1;
				}
				else{
					return 1;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return 1;
	}

}

//	private DAO dao= new DAO();

