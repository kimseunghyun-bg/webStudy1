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
function deleteBoard() {
<c:if test="${sessionScope.member.userId=='admin' || sessionScope.member.userId==dto.userId}">
    var boardNum = "${dto.boardNum}";
    var page = "${page}";
    var params = "boardNum="+boardNum+"&page="+page;
    var url = "<%=cp%>/board/delete.do?" + params;

    if(confirm("위 자료를 삭제 하시 겠습니까 ? "))
    	location.href=url;
</c:if>    
<c:if test="${sessionScope.member.userId!='admin' && sessionScope.member.userId!=dto.userId}">
    alert("게시물을 삭제할 수 없습니다.");
</c:if>
}

function updateBoard() {
<c:if test="${sessionScope.member.userId==dto.userId}">
    var boardNum = "${dto.boardNum}";
    var page = "${page}";
    var params = "boardNum="+boardNum+"&page="+page;
    var url = "<%=cp%>/board/update.do?" + params;

    location.href=url;
</c:if>

<c:if test="${sessionScope.member.userId!=dto.userId}">
   alert("게시물을 수정할 수  없습니다.");
</c:if>
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
				    <div style="width:600px; height:30px; line-height:30px; margin:5px auto;">
				        <img src="<%=cp%>/res/images/arrow.gif" alt="" style="padding-left: 5px; padding-right: 5px;">
				        <span style="font-weight: bold;font-size:13pt;font-family: 나눔고딕, 맑은 고딕, 굴림;">질문과 답변</span>
				    </div>
				</div>
				
				<div style="margin: 10px auto; margin-top: 20px; width:600px; min-height: 400px;">
					<table style="width: 600px; margin: 0px auto; border-spacing: 0px;">
					<tr><td colspan="4" height="3" bgcolor="#507CD1"></td></tr>
					
					<tr height="35">
					    <td colspan="4" align="center">
						   ${dto.subject}
					    </td>
					</tr>
					<tr><td colspan="4" height="1" bgcolor="#507CD1" ></td></tr>
					
					<tr height="30">
					    <td width="80" bgcolor="#EEEEEE" align="center">작성자</td>
					    <td width="220" align="left" style="padding-left:10px;">
					         ${dto.userName}
					    </td>
					    <td width="80" height="30" bgcolor="#EEEEEE" align="center">줄&nbsp;&nbsp;수</td>
					    <td width="220" align="left" style="padding-left:10px;">
					         ${linesu}
					     </td>
					</tr>
					<tr><td colspan="4" height="1" bgcolor="#DBDBDB"></td></tr>
					
					<tr height="30" >
					    <td width="80" bgcolor="#EEEEEE" align="center">등록일</td>
					    <td width="220" align="left" style="padding-left:10px;">
					        ${dto.created}
					    </td>
					    <td width="80" bgcolor="#EEEEEE" align="center">조회수</td>
					    <td width="220" align="left" style="padding-left:10px;">
					        ${dto.hitCount}
					    </td>
					</tr>
					<tr><td colspan="4" height="1" bgcolor="#507CD1"></td></tr>
					
					<tr>
					  <td colspan="4" align="left" style="padding: 15px 30px 15px 30px;" valign="top" height="150">
					        ${dto.content}
					   </td>
					</tr>
					<tr><td colspan="4" height="1" bgcolor="#507CD1"></td></tr>
					
					<tr height="30">
					    <td width="80" bgcolor="#EEEEEE" align="center">이전글</td>
					    <td width="520" align="left" style="padding-left:10px;" colspan="3">
							<c:if test="${not empty preReadDTO}">
								<a href="<%=cp%>/board/article.do?boardNum=${preReadDTO.boardNum}&${params}">${preReadDTO.subject}</a>
							</c:if>			
						</td>
					</tr>
					<tr><td colspan="4" height="1" bgcolor="#DBDBDB"></td></tr>
					
					<tr height="30">
					    <td width="80" bgcolor="#EEEEEE" align="center">다음글</td>
					    <td width="520" align="left" style="padding-left:10px;" colspan="3">
							<c:if test="${not empty nextReadDTO}">
								<a href="<%=cp%>/board/article.do?boardNum=${nextReadDTO.boardNum}&${params}">${nextReadDTO.subject}</a>
							</c:if>	
					    </td>
					</tr>
					<tr><td colspan="4" height="3" bgcolor="#507CD1" align="center"></td></tr>
					</table>
					
					<table style="width: 600px; margin: 0px auto; border-spacing: 0px;">
					<tr height="35">
					    <td width="50%" align="left">
					          <input type="image" src="<%=cp%>/res/images/btn_reply.gif" onclick="javascript:location.href='<%=cp%>/board/reply.do?boardNum=${dto.boardNum}&page=${page}';">
					          
					          <c:if test="${sessionScope.member.userId==dto.userId}">
					              <input type="image" src="<%=cp%>/res/images/btn_modify.gif" onclick="updateBoard();">
					          </c:if>
					          <c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">
					              <input type="image" src="<%=cp%>/res/images/btn_delete.gif" onclick="deleteBoard();">
					          </c:if>
					    </td>
					
					    <td align="right">
					           <input type="image" src="<%=cp%>/res/images/btn_list.gif" onclick="javascript:location.href='<%=cp%>/board/list.do';">
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