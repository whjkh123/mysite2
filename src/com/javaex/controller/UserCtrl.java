package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("UserCtrl");

		String act = request.getParameter("action");

		if ("joinForm".equals(act)) {
			System.out.println(act + " 회원가입 창");

			// joinForm forword
			WebUtil.forword(request, response, "/WEB-INF/views/user/joinForm.jsp");
		} else if ("join".equals(act)) {
			System.out.println(act + " 회원가입 완료");

			// parameter data load
			// http://localhost:8088/mysite2/user?uid=[]&psw=[]&uname=[]&gender=[]&action=join
			String id = request.getParameter("uid");
			String password = request.getParameter("psw");
			String name = request.getParameter("uname");
			String gender = request.getParameter("gender");

			// UserVo groupping
			UserVo uVo = new UserVo(id, password, name, gender);
			System.out.println(uVo.toString());

			// UserDao.insert() execute
			UserDao uDao = new UserDao();
			uDao.dbIsrt(uVo);

			// joinOk forword
			WebUtil.forword(request, response, "/WEB-INF/views/user/joinOk.jsp");
		} else if ("loginForm".equals(act)) {
			System.out.println(act + " 로그인 창");

			// loginForm forword
			WebUtil.forword(request, response, "/WEB-INF/views/user/loginForm.jsp");
		} else if ("login".equals(act)) {
			System.out.println(act + " 로그인 결과 창");

			// parameter data load
			String id = request.getParameter("id");
			String psw = request.getParameter("psw");

			// UserDao.get() execute
			UserDao uDao = new UserDao();
			UserVo uVo = uDao.getUser(id, psw);

			if (uVo == null) {
				System.out.println("로그인 실패");

				WebUtil.redirect(request, response, "/mysite2/user?action=loginForm");
			} else {
				System.out.println("로그인 성공");

				System.out.println(uVo.toString());

				// UserVo(id, psw) data attribute to jsp
				HttpSession session = request.getSession();
				session.setAttribute("authUser", uVo);

				WebUtil.redirect(request, response, "/mysite2/main");
			}

		} else if ("logout".equals(act)) {
			System.out.println(act + " 로그아웃");

			// session UserVo reset
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();

			WebUtil.redirect(request, response, "/mysite2/main");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
