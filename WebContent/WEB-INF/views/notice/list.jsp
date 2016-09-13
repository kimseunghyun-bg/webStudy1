<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
   String cp = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>study</title>

<link rel="stylesheet" href="<%=cp%>/res/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/res/css/layout/layout.css" type="text/css">

<script type="text/javascript" src="<%=cp%>/res/js/util.js"></script>
<script type="text/javascript">
	function searchList() {
		var f=document.searchForm;
		f.action="<%=cp%>/notice/list.do";
		f.submit();
	}
</script>

</head>
<body>

<div class="layoutMain">
	<div class="layoutHeader">
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</div>
	
	<div class="layoutBody">

		<div style="min-height: 450px;">
				<div style="width:100%;	height: 40px; line-height:40px;clear: both; border-top: 1px solid #DAD9FF;border-bottom: 1px solid #DAD9FF;">
				    <div style="width:700px; height:30px; line-height:30px; margin:5px auto;">
				        <img src="<%=cp%>/res/images/arrow.gif" alt="" style="padding-left: 5px; padding-right: 5px;">
				        <span style="font-weight: bold;font-size:13pt;font-family: 나눔고딕, 맑은 고딕, 굴림;">공지사항</span>
				    </div>
				</div>
			
				<div style="margin: 10px auto; margin-top: 20px; width:700px; min-height: 400px;">
		
					<table style="width: 700px; margin: 0px auto; border-spacing: 0px;">
					   <tr height="35">
					      <td align="left" width="50%">
					          <form name="searchForm" action="" method="post">
					              <select name="searchKey" class="selectField">
									<option value="subject">제목</option>
									<option value="userName">작성자</option>
									<option value="content">내용</option>
									<option value="created">등록일</option>
					            </select>
					            <input type="text" name="searchValue" class="boxTF">
					            <input type="button" value=" 검 색 " class="btn" onclick="searchList()">
					        </form>
					      </td>
					      <td align="right">
					      	<c:if test="${sessionScope.member.userId=='admin'}">
					          <input type="image" src="<%=cp%>/res/images/btn_write.gif" onclick="javascript:location.href='<%=cp%>/notice/created.do';">
					      	</c:if>
					      </td>
					   </tr>
					</table>
					
					<table style="width: 700px; margin: 0px auto; border-spacing: 0px;">
					  <tr align="center" bgcolor="#507CD1" height="30"> 
					      <td width="60" style="color: #ffffff;">번호</td>
					      <td width="350" style="color: #ffffff;">제목</td>
					      <td width="100" style="color: #ffffff;">작성자</td>
					      <td width="80" style="color: #ffffff;">작성일</td>
					      <td width="60" style="color: #ffffff;">조회수</td>
					      <td width="50" style="color: #ffffff;">첨부</td>
					  </tr>
					 
					 <c:forEach var="dto" items="${listNotice}">
					  <tr align="center" bgcolor="#ffffff" height="30"> 
					      <td><span style="display: inline-block; width: 28px; height: 18px; line-height: 18px; background: #ED4C00; color: #FFFFFF">공지</span></td>
					      <td align="left" style="padding-left: 10px;">
					          <a href='${articleUrl}&num=${dto.num}'>${dto.subject}</a>
					      </td>
					      <td align="center">${dto.userName}</td>
					      <td align="center">${dto.created}</td>
					      <td align="center">${dto.hitCount}</td>
					      <td align="center">
								<c:if test="${not empty dto.saveFilename}">
									<a href="<%=cp%>/notice/download.do?num=${dto.num}"><img src="<%=cp%>/res/images/disk.gif"></a>								
								</c:if>
							</td>
					  </tr>
					  <tr><td height="1" colspan="6" bgcolor="#e4e4e4"></td></tr> 
					 </c:forEach>
					 
					 <c:forEach var="dto" items="${list}">
					  <tr align="center" bgcolor="#ffffff" height="30"> 
					      <td align="center">${dto.listNum}</td>
					      <td align="left" style="padding-left: 10px;">
					          <a href='${articleUrl}&num=${dto.num}'>${dto.subject}</a>
					      </td>
					      <td align="center">${dto.userName}</td>
					      <td align="center">${dto.created}</td>
					      <td align="center">${dto.hitCount}</td>
					      <td align="center">
								<c:if test="${not empty dto.saveFilename}">
									<a href="<%=cp%>/notice/download.do?num=${dto.num}"><img src="<%=cp%>/res/images/disk.gif"></a>
								</c:if>
							</td>
					  </tr>
					  <tr><td height="1" colspan="6" bgcolor="#e4e4e4"></td></tr> 
					 </c:forEach>
					</table>
					 
					<table style="width: 700px; margin: 0px auto; border-spacing: 0px;">
					   <tr height="35">
						<td align="center">
					        <c:if test="${dataCount==0 }">
			                       등록된 게시물이 없습니다.
			                </c:if>
			                <c:if test="${dataCount!=0 }">
			                    ${paging}
			                </c:if>
						</td>
					   </tr>
					</table>
				</div>
		</div>

    </div>
	
	<div class="layoutFooter">
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</div>
</div>

</body>
</html>