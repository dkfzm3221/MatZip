package com.koreait.board;

//jsp : view 담당
//servlet : 로직 담당
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// request.RequestDispatcher 사라지냐 안사라지냐 주소값 안변함 doget이면 get방식 dopost면 post방식
// response.Sendredirect 사라짐 주소값 변함 무조건 get방식

import com.koreait.board.db.BoardDAO;
import com.koreait.board.db.DbCon;
import com.koreait.board.vo.BoardVO;

@WebServlet("/boardList") // 원하는 주소값을 적으면 됨 jsp container(Tomcat)가 실행시켜줌
public class BoardListSer extends HttpServlet {
	private static final long serialVersionUID = 1L; // default를 주고 싶으면 private를 지우면 됨 //절대 지우면 안됨 연동 안됨

	public BoardListSer() {
		super(); // 부모 //this나자신
	}

	// request 요청 // response 응답 get 방식 - 주소가 보임 속도가 빠름 화면 보여줄 때 많이 사용
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException { // get방식실행

		List<BoardVO> list = BoardDAO.selBoardList();
		request.setAttribute("data", list);

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/boardList.jsp");
		rd.forward(request, response);

	}

	// post 방식 - 캡슐화.값이 안보임 속도가 느려짐 보안이 좋음
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException { // post방식 실행

		doGet(request, response);
	}

}

// request 요청 들어오고 응답시 없어짐 개인용
// page 페이지 열리면 살고 닫으면 죽음 개인용
// session 모든 브라우저 끄면 죽음 개인용
// application 서버키면 생성 서버내리면 죽음 서버용
