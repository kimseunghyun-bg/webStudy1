<%@page import="com.util.CountManager"%>
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

</head>
<body>

<div class="layoutMain">
	<div class="layoutHeader">
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</div>
	
	<div class="layoutBody">
	    <div class="bodyFrame1" style="min-height: 450px;">
					메인 화면 입니다.<br>
				현재 접속자 수: <%=CountManager.getCurrentCount()%><br>
				오늘 접속자 수: <%=CountManager.getToDayCount()%><br>
				어제 접속자 수: <%=CountManager.getYesterDayCount()%><br>
				전체 접속자 수: <%=CountManager.getTotalCount()%><br>
         </div>
    </div>
	
	<div class="layoutFooter">
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</div>
</div>

</body>
</html>