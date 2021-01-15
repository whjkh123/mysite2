package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	public void dbCnt() {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}

	public void close() {

		try {

			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}

	public List<BoardVo> boardList() {

		List<BoardVo> bList = new ArrayList<BoardVo>();

		dbCnt();

		try {

			String query = "";
			query += " select b.no, ";
			query += " 		  b.title, ";
			query += "        u.name, ";
			query += "        b.hit, ";
			query += "		  TO_CHAR(b.reg_date, 'yyyy-mm-dd hh:mi:ss') ";
			query += " from   board b, users u ";
			query += " where  b.user_no = u.no ";
			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				int no = rs.getInt(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				int hit = rs.getInt(4);
				String reg_date = rs.getString(5);

				BoardVo bVo = new BoardVo(no, title, hit, name, reg_date);

				bList.add(bVo);

			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return bList;

	}

	public int insert(BoardVo bVo) {

		int count = 0;

		dbCnt();

		try {

			// INSERT INTO board VALUES(seq_board_no.NEXTVAL, 'test', 'test', default,
			// sysdate, 1);
			String query = "  ";
			query += " INSERT INTO board VALUES(seq_board_no.nextval, ?, ?, default, sysdate, ?)  ";

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, bVo.getTitle());
			pstmt.setString(2, bVo.getContent());
			pstmt.setInt(3, bVo.getNo());

			count = pstmt.executeUpdate();

			System.out.println("[DAO]: " + count + "건이 저장되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return count;
	}

	public BoardVo readList(int no) {

		BoardVo bVo = new BoardVo();

		dbCnt();

		try {

			String query = "";
			query += " select b.no, ";
			query += " 		  u.name, ";
			query += " 		  b.hit, ";
			query += "        TO_CHAR(b.reg_date, 'yyyy-mm-dd hh:mi:ss'), ";
			query += "        b.title, ";
			query += "        b.content, ";
			query += "        b.user_no ";
			query += " from   users u, board b ";
			query += " where  b.user_no = u.no ";
			query += "		  and b.no = ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				int bNo = rs.getInt(1);
				String name = rs.getString(2);
				int hit = rs.getInt(3);
				String reg_date = rs.getString(4);
				String title = rs.getString(5);
				String content = rs.getString(6);
				int uNo = rs.getInt(7);

				bVo = new BoardVo(bNo, name, hit, reg_date, title, content, uNo);

			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return bVo;

	}

	public int delete(int no) {

		int count = 0;

		dbCnt();

		try {
			String query = "";
			query += " DELETE FROM board WHERE no = ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);

			count = pstmt.executeUpdate();

			System.out.println("[DAO]: " + count + "건이 삭제되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return count;
	}

}
