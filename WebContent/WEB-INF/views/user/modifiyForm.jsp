<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%@ page import="com.javaex.vo.UserVo"%>

<%
	// >> UserVo uVo = (UserVo) session.getAttribute("userVo");
	UserVo uVo = (UserVo) request.getAttribute("userVo");
	UserVo authUser = (UserVo) session.getAttribute("authUser");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite/assets/css/user.css" rel="stylesheet" type="text/css">

</head>

<body>
	<div id="wrap">

		<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>

		<div id="aside">
			<h2>회원</h2>
			<ul>
				<li>회원정보</li>
				<li>로그인</li>
				<li>회원가입</li>
			</ul>
		</div>
		<!-- //aside -->

		<div id="content">

			<div id="content-head">
				<h3>회원정보</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>회원</li>
						<li class="last">회원정보</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="user">
				<div id="modifyForm">
					<form action="/mysite2/user" method="post">

						<!-- 아이디 -->
						<div class="form-group">
							<label class="form-text" for="input-uid">아이디</label>
							<span class="text-large bold"><%=uVo.getId()%></span>
						</div>

						<!-- 비밀번호 -->
						<div class="form-group">
							<label class="form-text" for="input-pass">패스워드</label>
							<input type="password" id="input-pass" name="psw" value="<%=uVo.getPassword()%>" placeholder="비밀번호를 입력하세요">
						</div>

						<!-- 이메일 -->
						<div class="form-group">
							<label class="form-text" for="input-name">이름</label>
							<input type="text" id="input-name" name="name" value="<%=uVo.getName()%>" placeholder="이름을 입력하세요">
						</div>

						<!-- 성별 → 'gender'가 male 일 때 남 체크, 나머지 여 체크 -->
						<div class="form-group">
							<span class="form-text">성별</span>
							<%
								if ("male".equals(uVo.getGender())) {
							%>
							<label for="rdo-male">남</label>
							<input type="radio" id="rdo-male" name="gender" value="male" checked="checked">
							<label for="rdo-female">여</label>
							<input type="radio" id="rdo-female" name="gender" value="female">
							<%
								} else {
							%>
							<label for="rdo-male">남</label>
							<input type="radio" id="rdo-male" name="gender" value="male">
							<label for="rdo-female">여</label>
							<input type="radio" id="rdo-female" name="gender" value="female" checked="checked">
							<%
								}
							%>
						</div>

						<!-- 버튼영역 -->
						<div class="button-area">
							<button type="submit" id="btn-submit">회원정보수정</button>
						</div>

						<input type="hidden" name="no" value="<%=uVo.getNo()%>">
						<input type="hidden" name="action" value="modifiy">

					</form>

				</div>
				<!-- //modifyForm -->
			</div>
			<!-- //user -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>

		<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>

	</div>
	<!-- //wrap -->

</body>

</html>