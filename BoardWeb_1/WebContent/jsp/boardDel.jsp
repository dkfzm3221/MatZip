<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "com.koreait.web.BoardVO" %>
<%@ page import = "java.sql.*" %>
<%!
	Connection getCon() throws Exception{
	String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	String username = "hr";
	String password = "koreait2020";
	
	Class.forName("oracle.jdbc.driver.OracleDriver");
	
	Connection con = DriverManager.getConnection(url, username, password); 
	System.out.println("접속성공!");

	return con; 
}
	%>
	
<%
	String strI_board = request.getParameter("i_board");


	int i_board = Integer.parseInt(strI_board);	
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	//BoardVO vo = new BoardVO();
	String sql = " DELETE FROM t_board WHERE i_board = ? ";
	
	int result = -1;
	try{
		con = getCon();
		ps = con.prepareStatement(sql);
		ps.setInt(1, i_board);
		 
		result = ps.executeUpdate();
	
		
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		if(rs != null) { try {rs.close();} catch(Exception e) {} };
		if(ps != null) { try {ps.close();} catch(Exception e) {} }; 
		if(con != null) { try {con.close();} catch(Exception e) {} };
	}
	switch(result){
		case -1:
			response.sendRedirect("/jsp/boardDetail.jsp?err=-1&i_board=" + i_board);
			break;
		case 0:
			response.sendRedirect("/jsp/boardDetail.jsp?err=0&i_board=" + i_board);
			break;
		case 1:
			response.sendRedirect("/jsp/boardList.jsp");
			break;
	}


%>
