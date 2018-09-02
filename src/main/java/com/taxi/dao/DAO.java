package com.taxi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.taxi.domain.Request_record;
import com.taxi.domain.Taxi_user;

public class DAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public DAO(){
		try {
			String dbURL = "jdbc:mysql://localhost:3306/taxi";//db url 저장
			String dbId = "root";//db 아이디 저장
			String dbPw = "1111";//db 패스워드 저장
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL,dbId,dbPw);//연결 저장
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	public int insertRequest(String address,String user_id){//배차 요청 insert
		String SQL = "insert into taxi.request_record(address,marked,request_time,marked_time,user_id) "
				+ "values(?,?,(select now()),?,?)";//쿼리 저장
		try{
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,address);//주소
			pstmt.setBoolean(2, false);//배차 여부
			pstmt.setString(3,"");//완료 시간 비우기
			pstmt.setString(4,user_id);//요청한 아이디 저장

			return pstmt.executeUpdate();//쿼리 실행
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return -1;//insert 되지 않았을 경우에 -1 리턴
	}
	public ArrayList<Request_record> getRequestList(){//새로고침을 클릭했을 때, 새로운 요청 목록 가져오기
		ArrayList<Request_record> requestRecordVOArrayList = new ArrayList<Request_record>();
		String SQL = "select * from taxi.request_record order by request_id desc";//쿼리 저장
		try{
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();//쿼리 실행

			while (rs.next()){
				int request_id = rs.getInt("request_id");//request_id 저장
				String address = rs.getString("address");//주소 저장
				boolean marked = rs.getBoolean("marked");//배차 여부 저장
				String request_time = rs.getString("request_time");//요청 시간 저장
				String marked_time = rs.getString("marked_time");//배차 시간 저장
				String user_id = rs.getString("user_id");//사용자 아이디 저장

				Request_record requestRecordVO = new Request_record(request_id,address,marked,request_time,marked_time,user_id);
				requestRecordVOArrayList.add(requestRecordVO);//객체 생성 후 리스트에 추가
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return requestRecordVOArrayList;//요청 목록 반환
	}
	public int markRequest(int requestId){//기사가 배차 요청을 보냈을때 목록 업데이트
		String SQL = "update taxi.request_record set marked = true where request_id = ?";//쿼리 저장
		String SQL2 = "update taxi.request_record set marked_time = (select now()) where request_id = ?";//쿼리 저장
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,requestId);//쿼리에 request_id 지정
			pstmt.executeUpdate();//쿼리 실행

			pstmt = conn.prepareStatement(SQL2);//두번째 쿼리문 실행 준비
			pstmt.setInt(1, requestId);//쿼리에 request_id 지정
			return pstmt.executeUpdate();//쿼리 실행
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return -1;//요청이 insert 되지 않았을 경우 -1 리턴
	}
	public boolean isMarked(int requestId){//배차된 요청인지 확인
		String SQL = "select marked from taxi.request_record where request_id = ?";//쿼리 저장
		boolean isMarked = false;//isMarked 초기화
		try {
			pstmt = conn.prepareStatement(SQL);//쿼리 실행 준비
			pstmt.setInt(1,requestId);//쿼리에 request_id 지정
			rs = pstmt.executeQuery();//쿼리 실행
			if(rs.next()){
				return rs.getBoolean("marked");//request_id에 해당하는  marked 컬럼 값 저장
			}
			else{//request_id가 잘못된 경우 false 저장
				isMarked = false;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return isMarked;//isMarked 리턴
	}
	public int joinUser(Taxi_user userVO){//회원가입
		String SQL = "insert into taxi.taxi_user(user_id,user_pw,user_type) values(?,?,?)";//쿼리 저장
		try{
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,userVO.getUser_id());//user_id 쿼리에 지정
			pstmt.setString(2,userVO.getUser_pw());//user_pw 쿼리에 지정
			pstmt.setString(3,userVO.getUser_type());//user_type 쿼리에 지정
			return pstmt.executeUpdate();//쿼리 실행 , executeUpdate 가 정상 동작 했다면 update된 행의 개수가 나온다. 그러면 -1이 나올수가 없다. 나오게 되면 오류 
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return -1;//insert 되지 않았을 경우 -1리턴
	}
	public Taxi_user loginUser(String user_id,String user_pw){//사용자 로그인
		String SQL = "select * from taxi.taxi_user where user_id = ? and user_pw = ?";
		Taxi_user userVO = null;//userVO 변수 초기화
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,user_id);//쿼리에 user_id 지정
			pstmt.setString(2,user_pw);//쿼리에 user_pw 지정
			rs = pstmt.executeQuery();

			if(rs.next()){
				if(rs.getObject(1)!=null){
					String id = rs.getString("user_id");//id 저장
					String pw = rs.getString("user_pw");//pw 저장
					String type = rs.getString("user_type");//type 저장

					userVO = new Taxi_user(id, pw, type);//user 객체 생성
				}
				else{//로그인 실패한 경우
					userVO = null;//null저장
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return userVO;//Taxi_user 객체 리턴
	}

	public String idCheck(String user_id) {
		String SQL = "select user_id from taxi.taxi_user where user_id = ?";//쿼리 저장
		String idCheck = null;//idCheck 초기화
		try {
			pstmt = conn.prepareStatement(SQL);//쿼리 실행 준비
			pstmt.setString(1,user_id);//쿼리에 user_id 지정
			rs = pstmt.executeQuery();//쿼리 실행

			if(rs.next()){
				if(rs.getObject(1)!=null){
					idCheck = rs.getString("user_id");//user_id 컬럼 값 저장
					return idCheck;//idCheck 리턴
				}
				else{//아이디를 사용 할 수 있는 경우
					return idCheck;//idCheck 리턴
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return idCheck;//idCheck 리턴
	}
}