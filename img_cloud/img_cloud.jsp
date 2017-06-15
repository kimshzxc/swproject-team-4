<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
	.thum {
		background-color: gray;
		margin: 3px;
		width: 310px;
		height: 310px;
		display: inline-block;
		background-size: cover;
		background-repeat: no-repeat;
		background-position: center center;
	}
	.date {
		font-family: sans-serif;
		font-weight: 900;
		font-size: 20px;
		padding-left: 5px;
		width: 100%;
	}
	a{
		font-weight: 600;
		text-decoration: none;
		color: #1AAB8A;
	}
	a:hover{
		color: black;
	}

</style>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<title>welcome to our web site</title>

		</head>

		<body>
			<a href="index.jsp" target="_self"> Log Out</a>
			<%@ page import="java.sql.*"%>

<%
		request.setCharacterEncoding("UTF-8");

		Connection con = null;
		Statement stmt = null;
		String id = (String)request.getAttribute("id");
		String pw = (String)request.getAttribute("pw");
		String date = "0000-00-00";
		// driver load
		String driver = "com.mysql.jdbc.Driver";
		String dbURL = "jdbc:mysql://right.chonbuk.ac.kr:3306/A201414204";

		int getRow=0;
		int count=0;

		try {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			con = DriverManager.getConnection(dbURL, "A201414204", "rlatmdxor1");
			stmt = con.createStatement();

			String sql = "select count(*) from soft_db where id='"+id+"';";
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				getRow = Integer.parseInt(result.getString(1));
			}
			sql = "select * from soft_db where id='"+id+"' order by insertdate desc;";
			result = stmt.executeQuery(sql);

			while (result.next()) {
				String tmp = result.getString(4);
				if(date.equals(tmp.substring(0, 10))){

				}else{
	%>
			<br>
				<div class="date">
					<%= tmp.substring(0, 10) %>
				</div>
	<%
				}
				date = tmp.substring(0, 10);
	%>
			<a href="img/<%= result.getString(2) + "/" + result.getString(3) %>">
			<div class="thum" style="background-image: url('img/<%= result.getString(2) + "/" + result.getString(3) %>')"></div>
			</a>
	<%
			}
			result.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			out.println(e.toString());
		}

	%>
	</body>
</html>
