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
				        <span style="font-weight: bold;font-size:13pt;font-family: 나눔고딕, 맑은 고딕, 굴림;">공지사항</span>
				    </div>
				</div>
				
				<div style="margin: 10px auto; margin-top: 20px; width:600px; min-height: 400px;">
					<table style="width: 600px; margin: 0px auto; border-spacing: 0px;">
					<tr><td colspan="4" height="3" bgcolor="#507CD1"></td></tr>
					
					<tr height="35">
					    <td colspan="4" align="center">
						   테스트 ...
					    </td>
					</tr>
					<tr><td colspan="4" height="1" bgcolor="#507CD1" ></td></tr>
					
					<tr height="30">
					    <td width="80" bgcolor="#EEEEEE" align="center">작성자</td>
					    <td width="220" align="left" style="padding-left:10px;">
					         홍길동
					    </td>
					    <td width="80" height="30" bgcolor="#EEEEEE" align="center">줄&nbsp;&nbsp;수</td>
					    <td width="220" align="left" style="padding-left:10px;">
					         10
					     </td>
					</tr>
					<tr><td colspan="4" height="1" bgcolor="#DBDBDB"></td></tr>
					
					<tr height="30" >
					    <td width="80" bgcolor="#EEEEEE" align="center">등록일</td>
					    <td width="220" align="left" style="padding-left:10px;">
					        2010-10-10
					    </td>
					    <td width="80" bgcolor="#EEEEEE" align="center">조회수</td>
					    <td width="220" align="left" style="padding-left:10px;">
					        10
					    </td>
					</tr>
					<tr><td colspan="4" height="1" bgcolor="#507CD1"></td></tr>
					
					<tr>
					  <td colspan="4" align="left" style="padding: 15px 30px 15px 30px;" valign="top" height="150">
					        테스트 내용 ...
					   </td>
					</tr>
					<tr><td colspan="4" height="1" bgcolor="#507CD1"></td></tr>
					
					<tr height="30">
					     <td width="80" bgcolor="#EEEEEE" align="center">첨 부</td>
					     <td width="520" align="left" style="padding-left:10px;" colspan="3">
                             파일
					     </td>
					</tr>
					<tr><td colspan="4" height="1" bgcolor="#DBDBDB"></td></tr>

					<tr height="30">
					    <td width="80" bgcolor="#EEEEEE" align="center">이전글</td>
					    <td width="520" align="left" style="padding-left:10px;" colspan="3">
							이전글 테스트 ...			
						</td>
					</tr>
					<tr><td colspan="4" height="1" bgcolor="#DBDBDB"></td></tr>
					
					<tr height="30">
					    <td width="80" bgcolor="#EEEEEE" align="center">다음글</td>
					    <td width="520" align="left" style="padding-left:10px;" colspan="3">
							다음글 테스트 ...	
					    </td>
					</tr>
					<tr><td colspan="4" height="3" bgcolor="#507CD1" align="center"></td></tr>
					</table>
					
					<table style="width: 600px; margin: 0px auto; border-spacing: 0px;">
					<tr height="35">
					    <td width="50%" align="left">
		          
					          <input type="image" src="<%=cp%>/res/images/btn_modify.gif" onclick="">
     
					          <input type="image" src="<%=cp%>/res/images/btn_delete.gif" onclick="">
					    </td>
					
					    <td align="right">
					           <input type="image" src="<%=cp%>/res/images/btn_list.gif" onclick="javascript:location.href='<%=cp%>/notice/list.do';">
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