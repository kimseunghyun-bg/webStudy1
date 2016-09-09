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

<style type="text/css">
.loginButton {
	 font-size: 11pt; 
	 font-family: 나눔고딕, 맑은 고딕, 돋움;
	 background-color:#507CD1;
	 border:none;
	 color:#FFFFFF;
	 width: 360px;
	 height: 50px;
	 line-height: 50px;
}
.lbl {
   position:absolute; 
   margin-left:15px; margin-top: 17px;
   color: #999999; font-size: 11pt;
   font-family: 맑은 고딕;
}
.loginTF {
  width: 340px; height: 35px;
  padding: 5px;
  padding-left: 15px;
  border:1px solid #666666;
  margin-top:5px; margin-bottom:5px;
  font-family:돋움;
  font-size:10pt;
}
</style>

<script type="text/javascript" src="<%=cp%>/res/js/util.js"></script>
<script type="text/javascript">
function bgLabel(ob, id) {
    if(!ob.value) {
	    document.getElementById(id).style.display="";
    } else {
	    document.getElementById(id).style.display="none";
    }
}

function sendLogin() {
    var f = document.loginForm;

	var str = f.userId.value;
    if(!str) {
        alert("아이디를 입력하세요. ");
        f.userId.focus();
        return;
    }

    str = f.userPwd.value;
    if(!str) {
        alert("패스워드를 입력하세요. ");
        f.userPwd.focus();
        return;
    }

    f.action = "<%=cp%>/member/login_ok.do";
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
				<div style="width:100%;	height: 40px;  line-height:40px;clear: both; border-top: 1px solid #DAD9FF;border-bottom: 1px solid #DAD9FF;">
				    <div style="width:360px; height:30px; line-height:30px; margin:5px auto;">
				        <img src="<%=cp%>/res/images/arrow.gif" alt="" style="padding-left: 5px; padding-right: 5px;">
				        <span style="font-weight: bold;font-size:13pt;font-family: 나눔고딕, 맑은 고딕, 굴림;">회원 로그인</span>
				    </div>
				</div>
				
				<div style="margin: 10px auto; margin-top: 50px; width:360px; min-height: 350px;">
				
					<form name="loginForm" method="post" action="">
					  <table style="margin: 30px auto; width: 360px; border-spacing: 0px;">
					  <tr align="center" height="60"> 
					      <td> 
			                <label for="userId" id="lblUserId" class="lbl" >아이디</label>
					        <input type="text" name="userId" id="userId" class="loginTF" maxlength="15"
					                   tabindex="1"
			                           onfocus="document.getElementById('lblUserId').style.display='none';"
			                           onblur="bgLabel(this, 'lblUserId');"
					           >
					      </td>
					  </tr>
					  <tr align="center" height="60"> 
					      <td>
					        <label for="userPwd" id="lblUserPwd" class="lbl" >패스워드</label>
					        <input type="password" name="userPwd" id="userPwd" class="loginTF" maxlength="20" 
					                   tabindex="2"
			                           onfocus="document.getElementById('lblUserPwd').style.display='none';"
			                           onblur="bgLabel(this, 'lblUserPwd');"
					        >
					      </td>
					  </tr>
					  <tr align="center" height="65" > 
					      <td>
					        <input type="button" value=" 로그인 " onclick="sendLogin();" class="loginButton">
					      </td>
					  </tr>
		  			  <tr height="10"><td></td></tr>
		  			  <tr><td height="1" bgcolor="#DAD9FF"></td></tr>
					  <tr align="center" height="40">
					      <td>
					       		<a href="<%=cp%>/">아이디 찾기</a>&nbsp;|&nbsp; 
					       		<a href="<%=cp%>/">비밀번호 찾기</a>&nbsp;|&nbsp;
					       		<a href="<%=cp%>/member/insert.do">회원가입</a>
					      </td>
					  </tr>
					  
					  <tr align="center" height="40" >
					    	<td><span style="color: blue;">${message}</span></td>
					  </tr>
					  
					  </table>
					</form>           
				</div>
		</div>

    </div>
	
	<div class="layoutFooter">
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</div>
</div>

</body>
</html>