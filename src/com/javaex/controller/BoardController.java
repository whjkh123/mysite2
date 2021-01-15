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

		} else if ("writeForm".equals(act)) {
			System.out.println(act + "게시글 작성 창");

		} else if ("write".equals(act)) {
			System.out.println(act + "게시글 작성");

		} else if ("read".equals(act)) {
			System.out.println(act + "게시글 보기");

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
