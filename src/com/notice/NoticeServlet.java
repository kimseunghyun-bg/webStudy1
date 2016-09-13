package com.notice;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.util.FileManager;
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
			// �� ����Ʈ
			String page=req.getParameter("page");
			int current_page=1;
			if(page!=null)
				current_page=Integer.parseInt(page);

			// �˻�
			String searchKey=req.getParameter("searchKey");
			String searchValue=req.getParameter("searchValue");
			if(searchKey==null) {
				searchKey="subject";
				searchValue="";
			}
			// GET ����� ��� ���ڵ�
			if(req.getMethod().equalsIgnoreCase("GET")) {
				searchValue=URLDecoder.decode(searchValue, "utf-8");
			}

			// ��ü ������ ����
			int dataCount;
			if(searchValue.length()==0)
				dataCount=dao.dataCount();
			else
				dataCount=dao.dataCount(searchKey, searchValue);

			// ��ü ������ ��
			int numPerPage=10;
			int total_page=util.pageCount(numPerPage, dataCount);

			if(current_page>total_page)
				current_page=total_page;

			// �Խù� ������ ���۰� ��
			int start=(current_page-1)*numPerPage+1;
			int end=current_page*numPerPage;
			
			//������
			List<NoticeDTO> listNotice=null;
			if(current_page==1){
				listNotice=dao.topNotice();
			}
			
			// �Խù� ��������
			List<NoticeDTO> list=null;
			if(searchValue.length()==0)
				list=dao.listNotice(start, end);
			else
				list=dao.listNotice(start, end, searchKey, searchValue);

			// ����Ʈ �۹�ȣ �����			
			int listNum, n=0;
			Iterator<NoticeDTO> it=list.iterator();
			while(it.hasNext()){
				NoticeDTO dto=it.next();
				listNum=dataCount-(start+n-1);
				dto.setListNum(listNum);
				n++;
			}

			String params="";
			if(searchValue.length()!=0) {
				// �˻��� ��� �˻��� ���ڵ�
				searchValue=URLEncoder.encode(searchValue, "utf-8");
				params="searchKey="+searchKey+
						"&searchValue="+searchValue;
			}

			// ����¡ ó��
			String listUrl=cp+"/notice/list.do";
			String articleUrl=cp+"/notice/article.do?page="+current_page;
			if(params.length()!=0) {
				listUrl+="?"+params;
				articleUrl+="&"+params;
			}

			String paging=util.paging(current_page, total_page, listUrl);

			// �������� JSP�� �ѱ� �Ӽ�
			req.setAttribute("list", list);
			req.setAttribute("listNotice", listNotice);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("paging", paging);
			req.setAttribute("articleUrl", articleUrl);
			
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
		}else if(uri.indexOf("download.do")!=-1){
			//���� �ٿ�ε�
			int num=Integer.parseInt(req.getParameter("num"));
			NoticeDTO dto=dao.readNotice(num);
			
			if(dto!=null){
				boolean b=FileManager.doFiledownload(dto.getSaveFilename(), dto.getOriginalFilename(), pathname, resp);
				
				if(b)
					return;
			}
			
			resp.setContentType("text/html;charset=UTF-8");
			PrintWriter out=resp.getWriter();
			
			out.print("<script>");
			out.print("alert('���� �ٿ�ε� ��.��.');history.back();");
			out.print("</script>");
			
		}else if(uri.indexOf("article.do")!=-1){
			
			forward(req, resp, "/WEB-INF/views/notice/article.jsp");
		}
	
	}

}
