<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>회원가입 페이지입니다.</h1>
	<!-- // / 8080포트 다음시점의 root경로 -->
	<form action="insert" method="post">
		<p>
			<input type="text" name="email" placeholder="이메일">
		</p>
		<p>
			<input type="password" name="pass" placeholder="비밀번호">
		</p>
		<p>
			<input type="text" name="name" placeholder="이름">
		</p>
		<p>
			<button type="submit">회원가입</button>
			<button type="button">취소</button>
		</p>
		
	</form>
</body>
</html>