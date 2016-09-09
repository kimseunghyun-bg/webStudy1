package com.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.MyServlet;

@WebServlet("/member/*")
public class MemberServlet extends MyServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		String cp=req.getContextPath();
		
		MemberDAO dao=new MemberDAO();
		HttpSession session=req.getSession();
		
		if(uri.indexOf("insert.do")!=-1){
			//ȸ������ ��

			req.setAttribute("mode", "created");
			req.setAttribute("title", "ȸ�� ����");
			
			forward(req, resp, "/WEB-INF/views/member/member.jsp");
		}else if(uri.indexOf("insert_ok.do")!=-1){
			
			MemberDTO dto=new MemberDTO();
			
			dto.setUserId(req.getParameter("userId"));
			dto.setUserPwd(req.getParameter("userPwd"));
			dto.setUserName(req.getParameter("userName"));
			dto.setBirth(req.getParameter("birth"));
			dto.setEmail1(req.getParameter("email1"));
			dto.setEmail2(req.getParameter("email2"));
			dto.setTel1(req.getParameter("tel1"));
			dto.setTel2(req.getParameter("tel2"));
			dto.setTel3(req.getParameter("tel3"));
			dto.setZip(req.getParameter("zip"));
			dto.setAddr1(req.getParameter("addr1"));
			dto.setAddr2(req.getParameter("addr2"));
			dto.setJob(req.getParameter("job"));
			
			int result=dao.inserMember(dto);
			if(result!=1){
				String message="ȸ�� ������ ���� �߽��ϴ�.";
				
				req.setAttribute("title", "ȸ�� ����");
				req.setAttribute("mode", "created");
				req.setAttribute("message", message);
				forward(req, resp, "/WEB-INF/views/member/member.jsp");
				return;
			}
			
			StringBuffer sb=new StringBuffer();
			sb.append("<b>"+dto.getUserName()+"</b>�� ȸ�������� �Ǿ����ϴ�.<br>");
			sb.append("����ȭ������ �̵��Ͽ� �α��� �Ͻñ� �ٶ��ϴ�.<br>");
			
			req.setAttribute("title", "ȸ�� ����");
			req.setAttribute("message", sb.toString());
			
			forward(req, resp, "/WEB-INF/views/member/complete.jsp");
			
			//resp.sendRedirect(cp+"/");
		} else if(uri.indexOf("login.do")!=-1){
			
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
		} else if(uri.indexOf("login_ok.do")!=-1){
			String userId=req.getParameter("userId");
			String userPwd=req.getParameter("userPwd");
			
			MemberDTO dto=dao.readMember(userId);
			
			if(dto!=null && dto.getUserPwd().equals(userPwd) && dto.getEnabled()==1){
				//�α��� ����
				// session.setMaxInactiveInterval(20*60);	// ���������ð� ����
				
				SessionInfo info=new SessionInfo();
				info.setUserId(dto.getUserId());
				info.setUserName(dto.getUserName());
				
				session.setAttribute("member", info);
				
				resp.sendRedirect(cp+"/");
				return;
			}
			
			req.setAttribute("message", "���̵� �Ǵ� �н����尡 ��ġ���� �ʽ��ϴ�.");
			
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			
		} else if(uri.indexOf("logout.do")!=-1){
			//�α׾ƿ�
			session.removeAttribute("member");
			session.invalidate();
			
			resp.sendRedirect(cp+"/");
		}
					
	} 

}
