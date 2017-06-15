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
	<h2>Sign Up Our WebSite</h2>

    <%
  		request.setCharacterEncoding("UTF-8");
  		String checkUserCreat = request.getParameter("info");
  		if(checkUserCreat==null){
  			checkUserCreat="0";
  		}

  		if(Integer.parseInt(checkUserCreat) == 1){
  			  out.println("<script>alert('회원가입에 실패하였습니다. 다시 진행하여 주세요!');</script>");
  		}else if(Integer.parseInt(checkUserCreat) == 2){
          out.println("<script>alert('존재하는 아이디 입니다. 다시 진행하여 주세요!');</script>");
      }
  	%>

	<form id="formMain" action="insertUserInfo.jsp" method="post">
		<table>
			<tr>
				<td><span>ID</span></td>
				<td><input class="textfeild" type="txet" name="id"></td>
				<td rowspan="2" height="100%"><button style="height: 100%">Sign up</button></td>
			</tr>
			<tr>
				<td><span>PW</span></td>
				<td><input class="textfeild" type="password" name="pw"></td>
			</tr>
			<tr>
				<td><span>Conf</span></td>
				<td><input class="textfeild" type="password" name="pwConf"></td>
				<td></td>
			</tr>
		</table>
	</form>


</body>
</html>
