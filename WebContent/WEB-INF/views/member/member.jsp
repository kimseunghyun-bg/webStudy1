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
function memberOk() {
	var f = document.memberForm;
	var str;

	str = f.userId.value;
	str = str.trim();
	if(!str) {
		alert("아이디를 입력하세요. ");
		f.userId.focus();
		return;
	}
	if(!/^[a-z][a-z0-9_]{4,9}$/i.test(str)) { 
		alert("아이디는 5~10자이며 첫글자는 영문자이어야 합니다.");
		f.userId.focus();
		return;
	}

/*
	if(!/^((\w|[\_\!\$\#])+)$/.test(str)) {
		alert("\n아이디는 영숫자와 _ ! $ # %만 가능합니다. ");
		f.userId.focus();
		return;
	}
*/    
	f.userId.value = str;

	str = f.userPwd.value;
	str = str.trim();
	if(!str) {
		alert("패스워드를 입력하세요. ");
		f.userPwd.focus();
		return;
	}
	if(!/^(?=.*[a-z])(?=.*[!@#$%^*+=-]|.*[0-9]).{5,10}$/i.test(str)) { 
		alert("패스워드는 5~10자이며 하나 이상의 숫자나 특수문자가 포함되어야 합니다.");
		f.userPwd.focus();
		return;
	}
	f.userPwd.value = str;

	if(str!= f.userPwd1.value) {
        alert("패스워드가 일치하지 않습니다. ");
        f.userPwd1.focus();
        return;
	}
	
    str = f.userName.value;
	str = str.trim();
    if(!str) {
        alert("이름을 입력하세요. ");
        f.userName.focus();
        return;
    }
    f.userName.value = str;

    str = f.birth.value;
	str = str.trim();
    if(!str || !isValidDateFormat(str)) {
        alert("생년월일를 입력하세요[YYYY-MM-DD]. ");
        f.birth.focus();
        return;
    }
    
    str = f.tel1.value;
	str = str.trim();
    if(!str) {
        alert("전화번호를 입력하세요. ");
        f.tel1.focus();
        return;
    }

    str = f.tel2.value;
	str = str.trim();
    if(!str) {
        alert("전화번호를 입력하세요. ");
        f.tel2.focus();
        return;
    }
    if(!/^(\d+)$/.test(str)) {
        alert("숫자만 가능합니다. ");
        f.tel2.focus();
        return;
    }

    str = f.tel3.value;
	str = str.trim();
    if(!str) {
        alert("전화번호를 입력하세요. ");
        f.tel3.focus();
        return;
    }
    if(!/^(\d+)$/.test(str)) {
        alert("숫자만 가능합니다. ");
        f.tel3.focus();
        return;
    }
    
    str = f.email1.value;
	str = str.trim();
    if(!str) {
        alert("이메일을 입력하세요. ");
        f.email1.focus();
        return;
    }

    str = f.email2.value;
	str = str.trim();
    if(!str) {
        alert("이메일을 입력하세요. ");
        f.email2.focus();
        return;
    }

    var mode="${mode}";
    if(mode=="created") {
    	f.action = "<%=cp%>/member/insert_ok.do";
    } else if(mode=="update") {
    	f.action = "<%=cp%>/member/update_ok.do";
    }

    f.submit();
}

function changeEmail() {
    var f = document.memberForm;
    
 	var str = f.selectEmail.value;
    if(str!="direct") {
         f.email2.value=str; 
         f.email2.readOnly = true;
         f.email1.focus(); 
    }
    else {
        f.email2.value="";
        f.email2.readOnly = false;
        f.email1.focus();
    }
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
				    <div style="width:600px; height:30px; line-height:30px; margin:5px auto;">
				        <img src="<%=cp%>/res/images/arrow.gif" alt="" style="padding-left: 5px; padding-right: 5px;">
				        <span style="font-weight: bold;font-size:13pt;font-family: 나눔고딕, 맑은 고딕, 굴림;">${title}</span>
				    </div>
				</div>
				
				<div style="margin: 10px auto; margin-top: 20px; width:600px;min-height: 400px;">
				
		           	<form name="memberForm" method="post">
						<table style="margin: 0px auto; width: 600px; border-spacing: 0px;">
						
						<tr height="2"><td colspan="2" bgcolor="#507CD1"></td></tr>
						
						<tr height="40">
							<td width="100" align="left" bgcolor="#E6E6E6" style="padding-left: 10px;" >
							아&nbsp;이&nbsp;디
							</td>
							<td align="left" style="padding-left: 5px;">
								<input type="text" name="userId" id="userId"  size="25" maxlength="10"  class="boxTF" 
												value="${dto.userId}" ${mode=="update" ? "readonly='readonly' style='border:none;'":""}>
									  <span id="userIdState" style='display:none;'></span>
							</td>
						</tr>
						<tr height="1"><td colspan="2" bgcolor="#cccccc"></td></tr>
					
						<tr height="40">
							<td width="100" align="left" bgcolor="#E6E6E6" style="padding-left: 10px;" >
							패스워드
							</td>
							<td align="left" style="padding-left: 5px;">
								<input type="password" name="userPwd" class="boxTF" size="25" maxlength="10">
							</td>
						</tr>
						<tr height="1"><td colspan="2" bgcolor="#cccccc"></td></tr>
					
						<tr height="40">
							<td width="100" align="left" bgcolor="#E6E6E6" style="padding-left: 10px;" >
							패스워드 확인
							</td>
							<td align="left" style="padding-left: 5px;">
								<input type="password" name="userPwd1" class="boxTF" size="25" maxlength="10">
							</td>
						</tr>
						<tr height="1"><td colspan="2" bgcolor="#cccccc"></td></tr>
					
						<tr height="40">
							<td width="100" align="left" bgcolor="#E6E6E6" style="padding-left: 10px;" >
							이&nbsp;&nbsp;&nbsp;&nbsp;름
							</td>
							<td align="left" style="padding-left: 5px;">
								<input type="text" name="userName" size="25" maxlength="20"  class="boxTF" 
												value="${dto.userName}" ${mode=="update" ? "readonly='readonly' style='border:none;' ":""}>
							</td>
						</tr>
						<tr height="1"><td colspan="2" bgcolor="#cccccc"></td></tr>
					
						<tr height="40">
							<td width="100" align="left" bgcolor="#E6E6E6" style="padding-left: 10px;" >
							생년월일
							</td>
							<td align="left" style="padding-left: 5px;">
								<input type="text" name="birth" size="25" maxlength="20"  class="boxTF" 
												value="${dto.birth}">
							</td>
						</tr>
						<tr height="1"><td colspan="2" bgcolor="#cccccc"></td></tr>
					
						<tr height="40">
							<td width="100" align="left" bgcolor="#E6E6E6" style="padding-left: 10px;" >
							이 메 일
							</td>
							<td align="left" style="padding-left: 5px;">
									  <select name="selectEmail" onchange="changeEmail();" class="selectField">
											<option value="">선 택</option>
											<option value="naver.com" ${dto.email2=="naver.com" ? "selected='selected'" : ""}>네이버 메일</option>
											<option value="hanmail.net" ${dto.email2=="hanmail.net" ? "selected='selected'" : ""}>한 메일</option>
											<option value="hotmail.com" ${dto.email2=="hotmail.com" ? "selected='selected'" : ""}>핫 메일</option>
											<option value="gmail.com" ${dto.email2=="gmail.com" ? "selected='selected'" : ""}>지 메일</option>
											<option value="direct">직접입력</option>
									  </select>
									  <input type="text" name="email1" size="13" maxlength="30"  class="boxTF" 
												value="${dto.email1}"> @ 
									  <input type="text" name="email2" size="13" maxlength="30"  class="boxTF" 
												value="${dto.email2}" readonly="readonly">
							</td>
						</tr>
						<tr height="1"><td colspan="2" bgcolor="#cccccc"></td></tr>
						
						<tr height="40">
							<td width="100" align="left" bgcolor="#E6E6E6" style="padding-left: 10px;" >
							전화번호
							</td>
							<td align="left" style="padding-left: 5px;">
									  <select name="tel1" class="selectField">
											<option value="">선 택</option>
											<option value="010" ${dto.tel1=="010" ? "selected='selected'" : ""}>010</option>
											<option value="011" ${dto.tel1=="011" ? "selected='selected'" : ""}>011</option>
											<option value="016" ${dto.tel1=="016" ? "selected='selected'" : ""}>016</option>
											<option value="017" ${dto.tel1=="017" ? "selected='selected'" : ""}>017</option>
											<option value="018" ${dto.tel1=="018" ? "selected='selected'" : ""}>018</option>
											<option value="019" ${dto.tel1=="019" ? "selected='selected'" : ""}>019</option>
									  </select>
									  <input type="text" name="tel2" size="5" maxlength="4"  class="boxTF" 
												value="${dto.tel2}"> -
									  <input type="text" name="tel3" size="5" maxlength="4"  class="boxTF" 
												value="${dto.tel3}">
							</td>
						</tr>
						<tr height="1"><td colspan="2" bgcolor="#cccccc"></td></tr>
					
						<tr height="40">
							<td width="100" align="left" bgcolor="#E6E6E6" style="padding-left: 10px;" >
							우편번호
							</td>
							<td align="left" style="padding-left: 5px;">
								<input type="text" name="zip" size="25" maxlength="7"  class="boxTF" 
												value="${dto.zip}" readonly="readonly">
								<button type="button" class="btn">우편번호검색</button>
							</td>
						</tr>
						<tr height="1"><td colspan="2" bgcolor="#cccccc"></td></tr>
						
						<tr height="70">
							<td width="100" align="left" bgcolor="#E6E6E6" style="padding-left: 10px; padding-top: 15px;" valign="top">
							주&nbsp;&nbsp;&nbsp;&nbsp;소
							</td>
							<td align="left" style="padding-left: 5px;">
							    <span style="display: block; margin-top: 5px;">
								    <input type="text" name="addr1" size="52" maxlength="50"  class="boxTF" 
												value="${dto.addr1}" readonly="readonly">
								</span>
							    <span style="display: block; margin-top: 5px; margin-bottom: 5px;">
								    <input type="text" name="addr2" size="52" maxlength="50"  class="boxTF" 
												value="${dto.addr2}">
								</span>
							</td>
						</tr>
						<tr height="1"><td colspan="2" bgcolor="#cccccc"></td></tr>
						
						<tr height="40">
							<td width="100" align="left" bgcolor="#E6E6E6" style="padding-left: 10px;" >
							직&nbsp;&nbsp;&nbsp;&nbsp;업
							</td>
							<td align="left" style="padding-left: 5px;">
								<input type="text" name="job" size="25" maxlength="7"  class="boxTF" 
												value="${dto.job}">
							</td>
						</tr>
					
						<tr height="2"><td colspan="2" bgcolor="#507CD1"></td></tr>
					
						<tr height="50">
							<td align="center" colspan="2">
								<c:if test="${mode=='created'}">
									<input type="button" value=" 회원가입 " class="btn" onclick="memberOk();">
									<input type="reset" value=" 다시입력 " class="btn" onclick="document.memberForm.userId">
									<input type="button" value=" 가입취소 " class="btn" onclick="javascript:location.href='<%=cp%>/';">
								</c:if>
								<c:if test="${mode=='update'}">
								    <input type="hidden" name="enabled" value="${dto.enabled}">
									<input type="button" value=" 정보수정 " class="btn" onclick="memberOk();"/>
									<input type="reset" value=" 다시입력 " class="btn" onclick="document.memberForm.userId"/>
									<input type="button" value=" 수정취소 " class="btn" onclick="javascript:location.href='<%=cp%>/';">
								</c:if>
							</td>
						</tr>
						
						<tr height="40">
							<td align="center" colspan="2">
							    <span style="color: blue;">${message}</span>
							</td>
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