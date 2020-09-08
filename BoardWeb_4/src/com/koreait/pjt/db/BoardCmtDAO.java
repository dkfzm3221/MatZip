package com.koreait.pjt.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.koreait.pjt.vo.BoardCmtVO;

public class BoardCmtDAO {
	public static int insCmt(BoardCmtVO param) {
		String sql = " INSERT INTO t_board4_cmt "
				+ " (i_cmt, i_board, i_user, cmt) "
				+ " VALUES "
				+" (seq_board4_cmt.nextval, ?, ?, ?)";
		
		return JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {

			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getI_board());
				ps.setInt(2, param.getI_user());
				ps.setNString(3, param.getCmt());
			}
			
		});
	}
	public static int updCmt(BoardCmtVO param) {
		String sql = " UPDATE t_board4_cmt "
				+ " SET m_dt = sysdate "
				+ " , cmt = ? "
				+ " WHERE i_cmt = ? " 
				+ " AND i_user = ? ";
		return JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {

			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setNString(1, param.getCmt());
				ps.setInt(2, param.getI_cmt());
				ps.setInt(3, param.getI_user());
			}
			
		});
	}
	
	public static List<BoardCmtDomain> selCmtList(final int i_board){
		List<BoardCmtDomain> list = new ArrayList();
		
		String sql = " SELECT A.i_cmt, B.i_user "
				+ ", A.cmt, A.r_dt, B.nm, B.profile_img "
				+ " FROM t_board4_CMT A "
				+ " INNER JOIN t_user B "
				+ " ON A.i_user = B.i_user "
				+ " WHERE A.i_board = ? "
				+ " ORDER BY A.i_cmt DESC ";
		
			JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {

			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setInt(1, i_board);
			}
			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				while(rs.next()) {
					int i_cmt = rs.getInt("i_cmt");
					int i_user = rs.getInt("i_user");
					String nm = rs.getNString("nm");
					String cmt = rs.getNString("cmt");
					String r_dt = rs.getNString("r_dt");
					String profile_img = rs.getNString("profile_img");
							
					BoardCmtDomain vo = new BoardCmtDomain();
					
					vo.setI_cmt(i_cmt);
					vo.setI_user(i_user);
					vo.setCmt(cmt);
					vo.setR_dt(r_dt);
					vo.setNm(nm);
					vo.setProfile_img(profile_img);
					list.add(vo);
				}
				return 1;
			}
			
		});
		return list;	
	}
	public static int delCmt(BoardCmtVO param) {
		String sql = " DELETE FROM t_board4_cmt WHERE "
				+ " i_cmt = ? AND i_user = ? ";
		
		return JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {

			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getI_cmt());
				ps.setInt(2, param.getI_user());
			}
			
		});
	}
}
