<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %> 
<%@ page import = "java.sql.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.koreait.web.BoardVO" %>
<%!
//메소드 안에 메소드 x 전역변수로 처리불가능/ !:붙이면 가능  private가능
	
	Connection getCon() throws Exception{
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String username = "hr";
		String password = "koreait2020";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		Connection con = DriverManager.getConnection(url, username, password); //DB에 연결해주는 통신병
		System.out.println("접속성공!");
	
		return con;
	
}


%>
<%

	List<BoardVO> boardList = new ArrayList();// 레코드(튜플) 한줄이 아닌 여러 줄을 사용할시 List사용해야함
	Connection con = null; // DB연결담당
	PreparedStatement ps = null; //커리문 완성 + 커리문 실행 + 문장완성 printf 기능과 비슷
	ResultSet rs = null; // select문의 결과를 담는 담당
	
	String sql = " SELECT i_board, title FROM t_board ORDER BY i_board DESC "; // 양쪽에 빈칸을 주어야함 그래야 문법오류가 안남
												       	// 가독성 신경쓰기
	//변수를 try안에 넣으면 중괄호 밖에서는 사용할수 없기때문에 밖에서 선언함.
	try{
		con = getCon(); //연결되어 있는 상태
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery(); //select문은 무조건 executeQuery를 사용해야함 while문과 함께 사용

		while(rs.next()){ //true false 
			int i_board = rs.getInt("i_board");//컬럼명 getString 형변환 해주면 가능
			String title = rs.getNString("title"); //getString 과 getNString 둘다 가능하나
												   // getNString 추천
												   
			BoardVO vo = new BoardVO(); //while문 밖에서 사용시 마지막값으로 똑같이 나옴
			
			vo.setI_board(i_board);
			vo.setTitle(title);
			
			boardList.add(vo);
		
		}
		
	}catch(Exception e){
		e.printStackTrace();
		
	}finally {
		if(rs != null) { try {rs.close();} catch(Exception e) {} }; //열면 닫아야함!  
		if(ps != null) { try {ps.close();} catch(Exception e) {} };// 닫는순서는 열린순서 반대로 
		if(con != null) { try {con.close();} catch(Exception e) {} };
		//따로 감싸준이유 : 만약 rs에서 오류가 발생하더라도 ps와  con이라도 닫아주기 위해서			
		// 안닫아주면 서버죽음

	}
%>    
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
</head>
<body>
	<div>게시판 리스트
		<a href="/jsp/boardWrite.jsp"><button>글쓰기</button></a>	
	</div>
	
	<table>
		<tr>
			<th>No</th>
			<th>제목</th>
		</tr>
		<% for(BoardVO vo : boardList){%> 
		<tr>
			<td><%= vo.getI_board() %></td>
			<td><a href = "/jsp/boardDetail.jsp?i_board=<%=vo.getI_board() %>">
			 <%= vo.getTitle() %>
			</a>
			</td>
		</tr> 
		<% }%>
	</table>

</body>
</html>

<%//물음표 뒤는 쿼리스티링값? %>
