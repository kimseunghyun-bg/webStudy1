package com.util;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class CountManager implements HttpSessionListener{
	private static int currentCount;
	private static long toDayCount, yesterDayCount, totalCount;
	
	public CountManager() {
		// ������ �Ǹ� ���� �ο��� ���� �ο��� �Ҵ��ϰ�, ���� �ο��� 0���� ����
		TimerTask task=new TimerTask() {
			@Override
			public void run() {
				yesterDayCount=toDayCount;
				toDayCount=0;
			}
		};
		
		Timer timer = new Timer();
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		timer.schedule(task, cal.getTime(), 1000*60*60*24);
	}
	
	public static void init(long toDay, long yesterDay, long total){
		toDayCount=toDay;
		yesterDayCount=yesterDay;
		totalCount=total;
	}
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		//������ ����� ����
		HttpSession session=event.getSession();
		session.getServletContext().log(session.getId()+" : ���ǻ��� ...");
		
		synchronized (this) {
			currentCount++;
			toDayCount++;
			totalCount++;
		}
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		//������ �Ҹ� �� ��
		synchronized (this) {
			currentCount--;
			if(currentCount<0)
				currentCount=0;
		}
	}
	
	public static int getCurrentCount() {
		return currentCount;
	}
	public static long getToDayCount() {
		return toDayCount;
	}
	public static long getYesterDayCount() {
		return yesterDayCount;
	}
	public static long getTotalCount() {
		return totalCount;
	}
	
}
