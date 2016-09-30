package com.util;

import java.sql.Connection;
import javax.sql.DataSource;
import javax.naming.InitialContext;
//import javax.naming.NamingException;
import javax.naming.Context;

public class DBCPConn {
	
	private static Connection conn = null;

	private DBCPConn() {
	}

	public static Connection getConnection() {
		try{
			if(conn == null) {
				// context.xml 파일의 내용을 읽어서 Context 객체를 생성
				Context ctx = new InitialContext();
				// java:/comp/env : 이름에 바인딩 된 객체를 찾아 리턴
				Context context  = (Context)ctx.lookup("java:/comp/env");
				DataSource ds = (DataSource)context.lookup("jdbc/myoracle");
				// DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/myoracle"); 

				conn = ds.getConnection();
			}
		}catch(Exception e) {
			System.out.println(e.toString());
		}
		
		return conn;
	}
	
	public static void close() {
		if(conn==null)
			return;
		
		try {
			if(!conn.isClosed())
				conn.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		conn=null;
	}
}
