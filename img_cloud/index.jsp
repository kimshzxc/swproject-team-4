<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width">
	<link rel="stylesheet" type="text/css" href="css/style.css" />
<title>welcome to our web site</title>
</head>
<body>
	<img src="webData/logo.png"/>
	<span id="title">My Image Cloud</span>
	<%
		request.setCharacterEncoding("UTF-8");
		String checkUserCreat = request.getParameter("info");
		if(checkUserCreat==null){
			checkUserCreat="111";
		}

		if(Integer.parseInt(checkUserCreat) == 1){
			  out.println("<script>alert('회원가입에 성공하였습니다.');</script>");
		}else if(Integer.parseInt(checkUserCreat) == 0){
			out.println("<script>alert('로그인에 실패 하였습니다. 다시 시도해 주세요.');</script>");
		}
	%>

	<form id="formMain" action="checkUser.jsp" method="post">
		<table>
			<tr>
				<td><span>ID</span></td>
				<td><input class="textfeild" type="txet" name="id"></td>
				<td rowspan="2" height="100%"><button>Login</button></td>
			</tr>
			<tr>
				<td><span>PW</span></td>
				<td><input class="textfeild" type="password" name="pw"></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td><a href="signUpPage.jsp" target="_self">sign up</a></td>
			</tr>
		</table>
	</form>

</body>
</html>
