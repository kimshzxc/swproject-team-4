<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>welcome to our web site</title>
</head>
<body>

	<%@ page import="java.sql.*"%>

	<%
		request.setCharacterEncoding("UTF-8");

		String id = request.getParameter("id");
		String pw = request.getParameter("pw");

		Connection con = null;
	  Statement stmt = null;
		// driver load
		String driver = "com.mysql.jdbc.Driver";
		String dbURL = "jdbc:mysql://right.chonbuk.ac.kr/A201414204";

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(dbURL, "A201414204", "rlatmdxor1");
	        stmt = con.createStatement();

	        String sql = "select * from soft_userInfo where id=\""+ id + "\";" ;

	        ResultSet result = stmt.executeQuery(sql);

	        String tmp1="";
	        String tmp2="";
	        String tmp3="";

	        while (result.next()) {
	        	tmp1 = result.getString(2);
	        	tmp2 = result.getString(3);
	        }

          out.print(tmp1+" "+tmp2);
	        if(tmp1.equals("")){
	           response.sendRedirect("index.jsp?info=0");
	        }else{
	        	if(tmp2.equals(pw)){
	        		out.print("Login succese");

	        		request.setAttribute("id", tmp1);
	        		request.setAttribute("pw", tmp2);

	        		RequestDispatcher res=request.getRequestDispatcher("img_cloud.jsp");
	        		res.forward(request, response);
	        	}else{
	        		response.sendRedirect("index.jsp?info=0");
	        	}
	        }


		}catch (Exception e){
			out.println("DB Connection Error");
		}

	%>

</body>
</html>
