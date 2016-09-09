package com.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.util.DBConn;

public class MemberDAO {
	private Connection conn=DBConn.getConnection();
	
	public int inserMember(MemberDTO dto){
		int result=0;
		PreparedStatement pstmt=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("INSERT INTO member1 (userId, userName, userPwd)");
			sb.append(" VALUES(?,?,?)");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getUserName());
			pstmt.setString(3, dto.getUserPwd());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt=null;
			
			if(dto.getTel1().length()!=0&&dto.getTel2().length()!=0&&dto.getTel3().length()!=0)
				dto.setTel(dto.getTel1()+"-"+dto.getTel2()+"-"+dto.getTel3());
			if(dto.getEmail1().length()!=0&&dto.getEmail2().length()!=0)
				dto.setEmail(dto.getEmail1()+"@"+dto.getEmail2());
			
			sb.delete(0, sb.length());
			sb.append("INSERT INTO member2(userId, birth, email, tel, job, zip, addr1, addr2)");
			sb.append(" VALUES(?,?,?,?, ?,?,?,?)");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getBirth());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, dto.getTel());
			pstmt.setString(5, dto.getJob());
			pstmt.setString(6, dto.getZip());
			pstmt.setString(7, dto.getAddr1());
			pstmt.setString(8, dto.getAddr2());

			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt=null;
			
			result=1;
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	public MemberDTO readMember(String userId){
		MemberDTO dto=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT m1.userId, userName, userPwd, enabled, created_date,");
			sb.append(" modify_date, birth, email, tel, job, zip, addr1, addr2");
			sb.append(" FROM member1 m1");
			sb.append(" LEFT OUTER JOIN member2 m2 ON m1.userId=m2.userId");
			sb.append(" WHERE m1.userId=?");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				dto=new MemberDTO();
				
				dto.setUserId(userId);
				dto.setUserName(rs.getString("userName"));
				dto.setUserPwd(rs.getString("userPwd"));
				dto.setEnabled(rs.getInt("enabled"));
				dto.setCreated_date(rs.getString("created_date"));
				dto.setModify_date(rs.getString("modify_date"));
				
				//이메일 split & dto삽입
				dto.setTel(rs.getString("email"));
				if(dto.getEmail()!=null){
					String[] email=dto.getTel().split("@");
					
					if(email.length==2){
						dto.setEmail1(email[0]);
						dto.setEmail2(email[1]);
					}
				}
				
				//전화번호 split & dto삽입
				dto.setTel(rs.getString("tel"));
				if(dto.getTel()!=null){
					String[] tel=dto.getTel().split("-");
					
					if(tel.length==3){
						dto.setTel1(tel[0]);
						dto.setTel2(tel[1]);
						dto.setTel3(tel[2]);
					}
				}
				
				dto.setJob(rs.getString("job"));
				dto.setAddr1(rs.getString("addr1"));
				dto.setAddr2(rs.getString("addr2"));
			}
			
			pstmt.close();
			rs.close();
			pstmt=null;
			rs=null;
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return dto;
	}
	//아우터 조인
	
}
