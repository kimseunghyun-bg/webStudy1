package com.board;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/board/*")
public class BoardServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		String uri=req.getRequestURI();
		String cp=req.getContextPath();
		BoardDAO dao=new BoardDAO();
		MyUtil util=new MyUtil();
		
		if(uri.indexOf("list.do")!=-1) {
			// 글 리스트
			String page=req.getParameter("page");
			int current_page=1;
			if(page!=null)
				current_page=Integer.parseInt(page);
			
			// 검색
			String searchKey=req.getParameter("searchKey");
			String searchValue=req.getParameter("searchValue");
			if(searchKey==null) {
				searchKey="subject";
				searchValue="";
			}
			// GET 방식인 경우 디코딩
			if(req.getMethod().equalsIgnoreCase("GET")) {
				searchValue=URLDecoder.decode(searchValue, "utf-8");
			}
			
			// 전체 데이터 개수
			int dataCount;
			if(searchValue.length()==0)
				dataCount=dao.dataCount();
			else
				dataCount=dao.dataCount(searchKey, searchValue);
			
			// 전체 페이지 수
			int numPerPage=10;
			int total_page=util.pageCount(numPerPage, dataCount);
			
			if(current_page>total_page)
				current_page=total_page;
			
			// 게시물 가져올 시작과 끝
			int start=(current_page-1)*numPerPage+1;
			int end=current_page*numPerPage;
			
			// 게시물 가져오기
			List<BoardDTO> list=null;
			if(searchValue.length()==0)
				list=dao.listBoard(start, end);
			else
				list=dao.listBoard(start, end, searchKey, searchValue);
			
			// 리스트 글번호 만들기
	        Date endDate = new Date();
	        long gap;
			
			int listNum, n=0;
			Iterator<BoardDTO>it=list.iterator();
			while(it.hasNext()) {
				BoardDTO dto=it.next();
				listNum=dataCount-(start+n-1);
				dto.setListNum(listNum);
				
				try {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date beginDate = formatter.parse(dto.getCreated());
	/*            
	            	// 날짜차이(일)
	            	gap=(endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60* 1000);
	            	dto.setGap(gap);
	*/
					// 날짜차이(시간)
					gap=(endDate.getTime() - beginDate.getTime()) / (60*60* 1000);
					dto.setGap(gap);
				}catch(Exception e) {
				}
	            
	            dto.setCreated(dto.getCreated().substring(0, 10));
				
				n++;
			}
			
			String params="";
			if(searchValue.length()!=0) {
				// 검색인 경우 검색값 인코딩
				searchValue=URLEncoder.encode(searchValue, "utf-8");
				params="searchKey="+searchKey+
						 "&searchValue="+searchValue;
			}
			
			// 페이징 처리
			String listUrl=cp+"/board/list.do";
			String articleUrl=cp+"/board/article.do?page="+current_page;
			if(params.length()!=0) {
				listUrl+="?"+params;
				articleUrl+="&"+params;
			}
			
			String paging=util.paging(current_page, total_page, listUrl);
			
			// 포워딩할 JSP로 넘길 속성
			req.setAttribute("list", list);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("paging", paging);
			req.setAttribute("articleUrl", articleUrl);
			
			forward(req, resp, "/WEB-INF/views/board/list.jsp");
		} else if(uri.indexOf("created.do")!=-1) {
			// 글쓰기 폼
			
			req.setAttribute("mode", "created");
			forward(req, resp, "/WEB-INF/views/board/created.jsp");
		} else if(uri.indexOf("created_ok.do")!=-1) {
			// 글 저장
			BoardDTO dto=new BoardDTO();
			
			dto.setUserId(info.getUserId());
			dto.setSubject(req.getParameter("subject"));
			dto.setContent(req.getParameter("content"));
			
			dao.insertBoard(dto, "created");
			
			resp.sendRedirect(cp+"/board/list.do");
		} else if(uri.indexOf("article.do")!=-1){
			//글 보기
			
			//파라미터 받기
			int boardNum=Integer.parseInt(req.getParameter("boardNum"));
			String page=req.getParameter("page");
			String searchKey=req.getParameter("searchKey");
			String searchValue=req.getParameter("searchValue");
			if(searchKey==null){
				searchKey="subject";
				searchValue="";
			}
			searchValue=URLDecoder.decode(searchValue, "UTF-8");
			
			//조회수 증가
			dao.updateHitCount(boardNum);
			
			//게시물 가져오기
			BoardDTO dto=dao.readBoard(boardNum);
			if(dto==null){
				resp.sendRedirect(cp+"/board/list.do?page="+page);
				return;
			}
			
			int linesu=dto.getContent().split("\n").length;
			dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
			
			//이전글, 다음글
			int groupNum=dto.getGroupNum();
			int orderNo=dto.getOrderNo();
			BoardDTO preReadDTO=dao.preReadBoard(groupNum, orderNo, searchKey, searchValue);
			BoardDTO nextReadDTO=dao.nextReadBoard(groupNum, orderNo, searchKey, searchValue);
			
			// 글리스트, 이전글 다음글에서 사용할 파라미터
			String params="page="+page;
			if(searchValue.length()!=0){
				params+="&searchKey="+searchKey+"&searchValue="+URLEncoder.encode(searchValue, "UTF-8");
			}
			
			//포워딩 jsp에 넘길 데이터
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("preReadDTO", preReadDTO);
			req.setAttribute("nextReadDTO", nextReadDTO);
			req.setAttribute("linesu", linesu);
			req.setAttribute("params", params);
			
			forward(req, resp, "/WEB-INF/views/board/article.jsp");
		} else if(uri.indexOf("reply.do")!=-1){
			//답변 
			int boardNum=Integer.parseInt(req.getParameter("boardNum"));
			String page=req.getParameter("page");
			
			BoardDTO dto=dao.readBoard(boardNum);
			if(dto==null){
				resp.sendRedirect(cp+"/board/list.do?page="+page);
				return;
			}
			
			dto.setContent("["+dto.getContent()+"] 에 대한 답변입니다.");
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("mode", "reply");
			forward(req, resp, "/WEB-INF/views/board/created.jsp");
		} else if(uri.indexOf("reply_ok.do")!=-1){
			//답변 완료
			BoardDTO dto=new BoardDTO();
			dto.setUserId(info.getUserId());
			dto.setSubject(req.getParameter("subject"));
			dto.setContent(req.getParameter("content"));
			dto.setGroupNum(Integer.parseInt(req.getParameter("groupNum")));
			dto.setDepth(Integer.parseInt(req.getParameter("depth")));
			dto.setOrderNo(Integer.parseInt(req.getParameter("orderNo")));
			dto.setParent(Integer.parseInt(req.getParameter("parent")));
			
			String page=req.getParameter("page");
			
			dao.insertBoard(dto, "reply");
			
			resp.sendRedirect(cp+"/board/list.do?page="+page);
		}		
		
		
	}

}
