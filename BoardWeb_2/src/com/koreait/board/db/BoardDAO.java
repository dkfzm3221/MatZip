package com.koreait.board.db;

import java.util.*;
import java.sql.*;

import com.koreait.board.BoardWriteSer;
import com.koreait.board.vo.BoardVO;

public class BoardDAO {

	public static List<BoardVO> selBoardList() {
		List<BoardVO> list = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = " SELECT i_board, title, i_student FROM t_board " + " ORDER BY i_board DESC ";

		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				int i_board = rs.getInt("i_board");
				String title = rs.getNString("title");
				int i_student = rs.getInt("i_student");

				BoardVO vo = new BoardVO();

				vo.setI_board(i_board);
				vo.setTitle(title);
				vo.setI_student(i_student);

				list.add(vo);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.close(con, ps, rs);
		}

		return list;
	}

	public static BoardVO selBoard(BoardVO param) {
		BoardVO vo = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = " SELECT i_board, title, ctnt, i_student FROM t_board " + " WHERE i_board = ? ";

		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getI_board());

			rs = ps.executeQuery();

			while (rs.next()) { // 0개 or 1개 밖에 안넘어옴. if 가능 .
				int i_board = rs.getInt("i_board");
				String title = rs.getNString("title");
				String ctnt = rs.getNString("ctnt");
				int i_student = rs.getInt("i_student");

				vo = new BoardVO();

				vo.setI_board(i_board);
				vo.setTitle(title);
				vo.setCtnt(ctnt);
				vo.setI_student(i_student);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.close(con, ps, rs);
		}
		return vo;
	}

	public static int insBoard(BoardVO param) {
		Connection con = null;
		PreparedStatement ps = null;

		// rs 필요 x select때만 필요

		String sql = " INSERT INTO t_board(i_board, title, ctnt, i_student) " + " VALUES"
				+ " (seq_board.nextval, ?, ?, ? )";
		int result = -1;
		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);

			ps.setNString(1, param.getTitle());
			ps.setNString(2, param.getCtnt());
			ps.setInt(3, param.getI_student());

			result = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.close(con, ps);
		}
		return result;

	}

	public static int delBoard(BoardVO param) {

		Connection con = null;
		PreparedStatement ps = null;

		String sql = " DELETE FROM t_board WHERE i_board = ?";

		int result = -1;

		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getI_board());

			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.close(con, ps);
		}

		return result;

	}

	public static int reBoard(BoardVO param) {
		Connection con = null;
		PreparedStatement ps = null;

		String sql = " UPDATE t_board SET title = ?, ctnt = ? WHERE i_board = ? ";

		int result = -1;

		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);

			ps.setNString(1, param.getTitle());
			ps.setNString(2, param.getCtnt());
			ps.setInt(3, param.getI_board());

			result = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.close(con, ps);
		}
		return result;

	}

}
