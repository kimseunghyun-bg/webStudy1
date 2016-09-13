package com.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import com.util.DBConn;

public class NoticeDAO {
	private Connection conn=DBConn.getConnection();
	
	public int insertNotice(NoticeDTO dto){
		int result=0;
		PreparedStatement pstmt=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("INSERT INTO notice (num, notice, userId, subject, content, saveFilename, originalFilename, filesize)");
			sb.append(" VALUES (notice_seq.NEXTVAL,?,?,?,?,?,?,?)");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, dto.getNotice()); 
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getSubject());
			pstmt.setString(4, dto.getContent());
			pstmt.setString(5, dto.getSaveFilename());
			pstmt.setString(6, dto.getOriginalFilename());
			pstmt.setLong(7, dto.getFilesize());
			
			result=pstmt.executeUpdate();
			
			pstmt.close();
			pstmt=null;
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	public int dataCount(){
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT NVL(COUNT(*), 0) FROM notice";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			if(rs.next())
				result=rs.getInt(1);
			
			pstmt.close();
			rs.close();
			pstmt=null;
			rs=null;
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	public int dataCount(String searchKey, String searchValue){
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
        	if(searchKey.equals("created"))
        		sql="SELECT NVL(COUNT(*), 0) FROM notice n JOIN member1 m ON n.userid=m.userid WHERE TO_CHAR(created, 'YYYY-MM-DD') = ?  ";
        	else if(searchKey.equals("userName"))
        		sql="SELECT NVL(COUNT(*), 0) FROM notice n JOIN member1 m ON n.userid=m.userid WHERE INSTR(userName, ?) = 1 ";
        	else
        		sql="SELECT NVL(COUNT(*), 0) FROM notice n JOIN member1 m ON n.userid=m.userid WHERE INSTR(" + searchKey + ", ?) >= 1 ";

        	pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, searchValue);
			rs=pstmt.executeQuery();
			
			if(rs.next())
				result=rs.getInt(1);
			
			pstmt.close();
			rs.close();
			pstmt=null;
			rs=null;
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	public List<NoticeDTO> listNotice(int start, int end){
		List<NoticeDTO> list=new LinkedList<>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT * FROM(");
			sb.append("	SELECT ROWNUM rnum, tb.* FROM(");
			sb.append("		SELECT num, n.userId, username, subject, saveFilename,");
			sb.append("		hitCount, TO_CHAR(created, 'YYYY-MM-DD') created FROM notice n");
			sb.append("		JOIN member1 m ON n.userid=m.userid");
			sb.append(" 	ORDER BY num DESC");
			sb.append("	) tb WHERE ROWNUM<=?");
			sb.append(") WHERE rnum>=?");
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				NoticeDTO dto=new NoticeDTO();
				dto.setNum(rs.getInt("num"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setSaveFilename(rs.getString("saveFilename"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
				
				list.add(dto);
			}
			
			pstmt.close();
			rs.close();
			pstmt=null;
			rs=null;
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return list;
	}
	
	public List<NoticeDTO> listNotice(int start, int end, String searchKey, String searchValue){
		List<NoticeDTO> list=new LinkedList<>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT * FROM(");
			sb.append("	SELECT ROWNUM rnum, tb.* FROM(");
			sb.append("		SELECT num, n.userId, username, subject, saveFilename,");
			sb.append("		hitCount, TO_CHAR(created, 'YYYY-MM-DD') created FROM notice n");
			sb.append("		JOIN member1 m ON n.userid=m.userid");
			if(searchKey.equals("created"))
				sb.append("	WHERE TO_CHAR(created, 'YYYY-MM-DD') = ? ");
			else if(searchKey.equals("userName"))
				sb.append("	WHERE INSTR(userName, ?) = 1 ");
			else
				sb.append("	WHERE INSTR(" + searchKey + ", ?) >= 1 ");
			sb.append(" 	ORDER BY num DESC");
			sb.append("	) tb WHERE ROWNUM<=?");
			sb.append(") WHERE rnum>=?");
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, searchValue);
			pstmt.setInt(2, end);
			pstmt.setInt(3, start);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				NoticeDTO dto=new NoticeDTO();
				dto.setNum(rs.getInt("num"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setSaveFilename(rs.getString("saveFilename"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
				
				list.add(dto);
			}
			
			pstmt.close();
			rs.close();
			pstmt=null;
			rs=null;
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return list;
	}
	
	public List<NoticeDTO> topNotice(){
		List<NoticeDTO> list=new LinkedList<>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT num, n.userID, username, subject, hitCount,");
			sb.append("		TO_CHAR(created, 'YYYY-MM-DD') created, saveFilename FROM notice n");
			sb.append("		JOIN member1 m ON n.userid=m.userid");
			sb.append("		WHERE notice=1");
			sb.append(" 	ORDER BY num DESC");
			
			pstmt=conn.prepareStatement(sb.toString());
			
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				NoticeDTO dto=new NoticeDTO();
				dto.setNum(rs.getInt("num"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setSaveFilename(rs.getString("saveFilename"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
				
				list.add(dto);
			}
			
			pstmt.close();
			rs.close();
			pstmt=null;
			rs=null;
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return list;
	}
	
	public NoticeDTO readNotice(int num){
		NoticeDTO dto=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT num, notice, n.userId, userName, saveFilename, content, subject,");
			sb.append("	originalFilename, fileSize, hitCount, TO_CHAR(created,'YYYY-MM-DD') created");
			sb.append("	FROM notice n JOIN member1 m ON n.userId=m.userId");
			sb.append(" WHERE num=?");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, num);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				dto=new NoticeDTO();
				
				dto.setNum(rs.getInt("num"));
				dto.setNotice(rs.getInt("notice"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setSaveFilename(rs.getString("saveFilename"));
				dto.setOriginalFilename(rs.getString("originalFilename"));
				dto.setFilesize(rs.getLong("fileSize"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
			}
			rs.close();
			pstmt.close();
			rs=null;
			pstmt=null;
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return dto;
	}
	
}
