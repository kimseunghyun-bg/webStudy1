package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class WebAppInit implements ServletContextListener{
	private String pathname="/WEB-INF/count.txt";

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// 어플리케이션이 종료되는 시점
		saveFile();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// 어플리케이션이 시작되는 시점
		pathname=event.getServletContext().getRealPath(pathname);
		
		loadFile();
	}
	
	protected void loadFile(){
		try {
			long toDay=0, yesterDay=0, total=0;
			
			File f=new File(pathname);
			if(!f.exists())
				return;
			
			//바이트스트림을 문자로 바꿔주는 놈 = > 리더객체,
			BufferedReader br=new BufferedReader(new FileReader(pathname));
			String s;
			s=br.readLine();
			if(s!=null){
				String []ss=s.split(":");
				if(ss.length==3){
					toDay=Long.parseLong(ss[0].trim());
					yesterDay=Long.parseLong(ss[1].trim());
					total=Long.parseLong(ss[2].trim());
				}
			}
			
			CountManager.init(toDay, yesterDay, total);
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	protected void saveFile(){
		try {
			long toDay, yesterDay, total;
			
			toDay=CountManager.getToDayCount();
			yesterDay=CountManager.getYesterDayCount();
			total=CountManager.getTotalCount();
			
			String s=toDay+":"+yesterDay+":"+total;
			
			PrintWriter out=new PrintWriter(pathname);
			out.println(s);
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
