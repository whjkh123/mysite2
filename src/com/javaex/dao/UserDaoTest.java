package com.javaex.dao;

import com.javaex.vo.UserVo;

public class UserDaoTest {

	public static void main(String[] args) {

		UserDao uDao = new UserDao();
		UserVo uVo = uDao.getUser("test", "1234");

		System.out.println(uVo);

	}

}
