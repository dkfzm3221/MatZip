package com.koreait.pjt.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.pjt.MyUtils;
import com.koreait.pjt.ViewResolver;
import com.koreait.pjt.db.BoardCmtDAO;
import com.koreait.pjt.db.BoardDAO;
import com.koreait.pjt.db.BoardDomain;
import com.koreait.pjt.vo.BoardVO;
import com.koreait.pjt.vo.UserVO;

@WebServlet("/board/detail")
public class BoardDetailSer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserVO loginUser = MyUtils.getLoginUser(request);
		if(loginUser == null) {
			response.sendRedirect("/login");
			return;
		}
		
		String strI_board = request.getParameter("i_board");
		int i_board = MyUtils.parseStrToInt(strI_board);
		String strI_user = request.getParameter("i_user");
		int i_user = MyUtils.parseStrToInt(strI_user);
	
		
		//단독으로 조회수 올리기 방지! --- [start]
		ServletContext application = getServletContext(); //어플리케이션 내장객체 얻어오기
		Integer readI_user = (Integer)application.getAttribute("read_" + strI_board);
		if(readI_user == null || readI_user != loginUser.getI_user()) {
			BoardDAO.addHits(i_board);
			application.setAttribute("read_" + strI_board, loginUser.getI_user());
		}
		//단독으로 조회수 올리기 방지! --- [end]
		
		
		request.setAttribute("likeList", BoardDAO.selBoardLikeList(i_board));
		
		
		
		
		BoardVO param = new BoardVO();
		param.setI_user(loginUser.getI_user());
		param.setI_board(i_board);
		//param.setI_user(i_user);
		
//		BoardDomain param2= new BoardDomain();
//		param2 =  BoardDAO.selBoard(param);
//		System.out.println(loginUser.getI_user());
//		System.out.println("I_board:"+param2.getI_board());
//		System.out.println("yn_like:"+param2.getYn_like());
		request.setAttribute("data", BoardDAO.selBoard(param));
		request.setAttribute("cmtList", BoardCmtDAO.selCmtList(i_board));
		
		ViewResolver.forward("board/detail", request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}