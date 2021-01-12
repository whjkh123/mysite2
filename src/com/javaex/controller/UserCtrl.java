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

				// UserVo data attribute to jsp
				// id, password 비교
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
		} else if ("modifiyForm".equals(act)) {
			System.out.println(act + " 회원정보 수정 창");

			HttpSession session = request.getSession();
			UserVo authVo = (UserVo) session.getAttribute("authUser");

			UserDao uDao = new UserDao();

			UserVo uVo = uDao.getOne(authVo.getNo());

			session.setAttribute("userVo", uVo);

			// modifiyForm forword
			WebUtil.forword(request, response, "/WEB-INF/views/user/modifiyForm.jsp");
		} else if ("modifiy".equals(act)) {
			System.out.println(act + " 회원정보 수정");
			// id = test
			// password = 1234 → 1111
			// name = 홍길동 → 이효리
			// gender = male → female

			// error:java.sql.SQLException: 인덱스에서 누락된 IN 또는 OUT 매개변수:: 4 발생
			// Update users SET password = ?, name = ?, gender = ? WHERE no = ?
			// 변수(?)는 4개인데 sql 쿼리문엔 3개만 작성 → 해결
			int no = Integer.parseInt(request.getParameter("no"));
			String psw = request.getParameter("psw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");

			UserVo uVo = new UserVo(no, psw, name, gender);

			UserDao uDao = new UserDao();
			uDao.dbUpd(uVo);

			// #1 id = test, password = 1234 login
			// #2 회원정보 수정 password = 1234 → 1111, name = 홍길동 → 이효리, gender = male → female
			// #3 sql users table 상에서 데이터 변경 확인
			// #4 'modifiyForm'에서 '회원정보수정'을 클릭 후 'main'으로 redirect 됐을 시 name = 홍길동 유지
			// #5 logout 후 id = test, password = 1111 login 성공, name = 조경환 변경

			// UserVo data attribute to jsp
			// 해당 계정(id, password 비교)의 데이터 출력
			HttpSession session = request.getSession();
			session.setAttribute("authUser", uVo);
			// 수정 된 데이터를 'session'으로부터 load → 'authUser'에 save
			// 회원정보 수정 후 데이터 즉시 반영 확인
			// logout 후 다시 login 했을 시 변경 된 데이터 유지 확인

			WebUtil.redirect(request, response, "/mysite2/main");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
