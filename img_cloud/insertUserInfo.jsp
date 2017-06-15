<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>welcome to our web site</title>
        </head>
        <body>
            <%@ page import="java.sql.*" %>
            <%@ page import="java.io.*" %>

        <%
		request.setCharacterEncoding("UTF-8");

		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String pwConf = request.getParameter("pwConf");

		if(!pw.equals(pwConf)||pw==null||pwConf==null||id==null||pw==""||pwConf==""||id==""){
			response.sendRedirect("signUpPage.jsp?info=1");
		}else{
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
        String check="";
        while(result.next()){
          check=result.getString(1);
        }

        if(!check.equals("")){
        response.sendRedirect("signUpPage.jsp?info=2");
        }else{
				      sql = "insert into soft_userInfo(id,pw) values ('" + id +"','" + pw +"');";

				          stmt.executeUpdate(sql);
                  String dirPath = "\\img\\"+id+"\\";

                  File makeDir = new File(request.getSession().getServletContext().getRealPath("")+dirPath);
                  if(!makeDir.exists()){
                    makeDir.mkdirs();
                  }


	      // if succese to sign up then go to main
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) { }

          response.sendRedirect("index.jsp?info=1");
          }

			}catch (Exception e){
				out.println("DB Connection Error");
			}
		}
	%>
        </body>
    </html>
