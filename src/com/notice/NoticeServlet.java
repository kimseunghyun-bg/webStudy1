package com.notice;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/notice/*")
public class NoticeServlet extends MyServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		NoticeDAO dao=new NoticeDAO();
		MyUtil util=new MyUtil();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		String root=session.getServletContext().getRealPath("/");
		String pathname=root+File.separator+"uploads"+File.separator+"notice";
		File f=new File(pathname);
		if(! f.exists())
			f.mkdirs();
		
		String uri=req.getRequestURI();
		String cp=req.getContextPath();
		
		if(uri.indexOf("list.do")!=-1){
			
			forward(req, resp, "/WEB-INF/views/notice/list.jsp");
		}else if(uri.indexOf("created.do")!=-1){
			if(info==null||!info.getUserId().equals("admin")){
				resp.sendRedirect(cp+"/notice/list.do");
				return;
			}
			
			req.setAttribute("mode", "created");
			forward(req, resp, "/WEB-INF/views/notice/created.jsp");
		}else if(uri.indexOf("created_ok.do")!=-1){
			if(info==null||!info.getUserId().equals("admin")){
				resp.sendRedirect(cp+"/notice/list.do");
				return;
			}

			NoticeDTO dto=new NoticeDTO();
			String enctype="UTF-8";
			int maxFilesize=5*1024*1024;
			
			MultipartRequest mreq=null;
			mreq=new MultipartRequest(req, pathname, maxFilesize, enctype, new DefaultFileRenamePolicy());
			
			dto.setUserId(info.getUserId());
			dto.setSubject(mreq.getParameter("subject"));
			dto.setContent(mreq.getParameter("content"));
			if(mreq.getParameter("notice")!=null)
				dto.setNotice(Integer.parseInt(mreq.getParameter("notice")));
			File file=mreq.getFile("upload");
			if(file!=null){
				dto.setSaveFilename(mreq.getFilesystemName("upload"));
				dto.setOriginalFilename(mreq.getOriginalFileName("upload"));
				dto.setFilesize(file.length());
			}
			
			dao.insertNotice(dto);			
			
			resp.sendRedirect(cp+"/notice/list.do");
		}
	
	}

}
