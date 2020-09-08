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
	String strI_board = request.getParameter("i_board");//java는 패키지명은 소문자로 대문자X 
	if(strI_board == null){
%>	
	<script>
		alert('잘 못 된 접근입니다.')
		location.href='/jsp/boardList.jsp'
	</script>
}
<%
	return;
}

	String sql = " SELECT title, ctnt, i_student FROM t_board WHERE i_board = ? ";
	int i_board = Integer.parseInt(strI_board);
	
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	BoardVO vo = new BoardVO();
	
	
	try{
		con = getCon();
		ps = con.prepareStatement(sql); //?에 값을 집어넣을수있게 해줌
		ps.setInt(1, i_board); //setInt = 값이 그대로 들어감 / 첫번째 값은 물음표의 위치값
		//ps.setString(1, strI_board); ''로 자동으로 감싸줌
		
		//executeUpdate() /
		rs = ps.executeQuery(); //문장완성후 작성
		
		if(rs.next()){ //while문 사용가능
			String title = rs.getNString("title");
			String ctnt = rs.getNString("ctnt");
			int i_student = rs.getInt("i_student");
			
			vo.setTitle(title);
			vo.setCtnt(ctnt);
			vo.setI_student(i_student);
		}
		
			
		
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		if(rs != null) { try {rs.close();} catch(Exception e) {} };
		if(ps != null) { try {ps.close();} catch(Exception e) {} }; 
		if(con != null) { try {con.close();} catch(Exception e) {} };
	}
	
%>





<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상세 페이지</title>
</head>
<body>
	<div>
		<a href = "/jsp/boardList.jsp">리스트로 가기</a>
		<a href = "#" onclick="proDel(<%=i_board %>)">삭제</a>
		<a href = "/jsp/boardMod.jsp?i_board=<%=i_board%>">수정</a>
	</div>
	<div>제목 : <%= vo.getTitle() %></div>
	<div>내용 : <%= vo.getCtnt() %></div>
	<div>작성자 : <%= vo.getI_student() %></div>
	<script>
		function proDel(i_board){
			alert('i_board : ' + i_board)
			var result = confirm('삭제하시겠습니까?')
			if(result){
				location.href = '/jsp/boardDel.jsp?i_board=' + i_board
			}
		}
	</script>
</body>
</html>





