<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
   String cp = request.getContextPath();
%>

<div id="header">
      <div id="headerTop">
      		<div id="headerLeft">
      		    <p style="margin: 2px;">
                     <a href="<%=cp%>/" style="text-decoration: none;">
                        <span style="width: 200px; height: 70; position: relative; left: 0; top:20px; color: #2984ff; filter: mask(color=red) shadow(direction=135) chroma(color=red);font-style: italic; font-family: arial black; font-size: 30px; font-weight: bold;">SPRING</span>
                     </a>
                </p>
      		</div>
      		<div id="headerRight">
      		      <div style="margin-top: 5px; float: right;">
					<c:choose>
					      <c:when test="${empty sessionScope.member}">
							<a href="<%=cp%>/member/login.do">로그인</a>
							&nbsp;|&nbsp;
							<a href="<%=cp%>/member/insert.do">회원가입</a>
						</c:when>
						<c:otherwise>
								<span style="color:blue;">${sessionScope.member.userName}</span>님
								&nbsp;|&nbsp;
								<a href="<%=cp%>/member/logout.do">로그아웃</a>
								&nbsp;|&nbsp;
								<a href="<%=cp%>/">정보수정</a>
						</c:otherwise>
					</c:choose>
      		      </div>
      		</div>
      </div>
      <div id="menu">
			<ul id="nav">
			<li><a href="#">회사소개</a>
				<ul>
				<li><a href="#">회사개요</a></li>
				<li><a href="#">회사연혁</a></li>
				<li><a href="#">사업분야</a></li>
				<li><a href="#">조직도</a></li>
				<li><a href="#">찾아오시는길</a></li>
				</ul>
			</li>
			
			<li><a href="#">커뮤니티</a>
				<ul>
				<li><a href="#">방명록</a></li>
				<li><a href="#">게시판</a></li>
				<li><a href="#">자료실</a></li>
				<li><a href="#">포토갤러리</a></li>
				</ul>
			</li>

			<li><a href="#">마이페이지</a>
				<ul>
				<li><a href="#" style="margin-left:150px; " onmouseover="this.style.marginLeft='150px';">쪽지</a></li>
				<li><a href="#">일정관리</a></li>
				<li><a href="#">친구관리</a></li>
				<li><a href="#">사진첩</a></li>
				<li><a href="#">채팅</a></li>
				</ul>
			</li>
			
			<li><a href="#">고객센터</a>
				<ul>
				<li><a href="#" style="margin-left:230px; " onmouseover="this.style.marginLeft='230px';">자주하는질문</a></li>
				<li><a href="<%=cp%>/notice/list.do">공지사항</a></li>
				<li><a href="<%=cp%>/board/list.do">질문과 답변</a></li>
				<li><a href="#">주요일정</a></li>
				<li><a href="#">이벤트</a></li>
				</ul>
			</li>
			
			<li style="float: right;"><a href="#">전체보기</a></li>
				
			</ul>      
      </div>
</div>
<div id="navigation">
	<div id="navBar">홈 &gt; 회사소개</div>
</div>
