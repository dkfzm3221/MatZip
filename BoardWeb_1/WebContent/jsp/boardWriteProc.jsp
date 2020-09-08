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
	//request.setCharacterEncoding("UTF-8");
	String title = request.getParameter("title");//외부로 부터 값을 받아오기 위해서 맵 형식 레퍼런스 변수에 값이 없으면  null
	String ctnt = request.getParameter("ctnt"); //request에는 주소값 들어감
	String strI_student = request.getParameter("i_student");
	
	if("".equals(title) || "".equals(ctnt) || "".equals(strI_student)){
		response.sendRedirect("/jsp/boardWrite.jsp?err=10" );
		return;
	}
	
	int i_student = Integer.parseInt(strI_student); //형변환 문자열이 섞여있으면 에러 터짐
	
	Connection con = null;
	PreparedStatement ps = null; //rs는 필요 x select문만 사용 
	
	String sql = " insert into t_board(i_board, title, ctnt, i_student) " + 
			" select nvl(max(i_board), 0) + 1,?,?,? " //(?) : 안에 값을 삽입하기 위한 기법
			+ " from t_board ";
	

	int result = -1;
	try{
		con = getCon();
		ps = con.prepareStatement(sql);
		
		ps.setNString(1, title);
		ps.setNString(2, ctnt);
		ps.setInt(3, i_student);
		
		result = ps.executeUpdate();

		
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		if(ps != null) { try {ps.close();} catch(Exception e) {} }; 
		if(con != null) { try {con.close();} catch(Exception e) {} };
	}
	
	int err = 0;
	switch(result){
	
	case 1:
		response.sendRedirect("/jsp/boardList.jsp");
		return; //종료
	case 0:
		err = 10;
		break;
	case -1:
		err = 20;
		break;
	}
	
	response.sendRedirect("/jsp/boardWrite.jsp?err=" + err);
	//return; 자동으로 들어감

	

	
	
%>
<div>title : <%=title %></div>
<div>ctnt : <%=ctnt %></div>
<div>strI_student : <%=strI_student %></div>