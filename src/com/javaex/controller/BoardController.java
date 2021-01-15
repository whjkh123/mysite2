package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("BoardCtrl");

		request.setCharacterEncoding("UTF-8");

		String act = request.getParameter("action");

		if ("list".equals(act)) {
			System.out.println(act + " 게시판메인");

			BoardDao bDao = new BoardDao();
			List<BoardVo> bList = bDao.boardList();

			request.setAttribute("BoardList", bList);

			WebUtil.forword(request, response, "/WEB-INF/views/board/list.jsp");
		} else if ("writeForm".equals(act)) {
			System.out.println(act + "게시글 작성 창");

			WebUtil.forword(request, response, "/WEB-INF/views/board/writeForm.jsp");
		} else if ("write".equals(act)) {
			System.out.println(act + "게시글 작성");

			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");

			int user_no = authUser.getNo();
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			BoardVo bVo = new BoardVo(user_no, title, content);

			BoardDao bDao = new BoardDao();
			bDao.bIsrt(bVo);

			WebUtil.redirect(request, response, "/mysite2/board?action=list");
		} else if ("read".equals(act)) {
			System.out.println(act + "게시글 보기");

			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardDao bDao = new BoardDao();
			
			

		} else if ("delete".equals(act)) {
			System.out.println(act + "게시글 삭제");

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
