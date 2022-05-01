<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>로그인페이지입니다.</h1>
	<p>${msg }</p>
	<form >
		<p>
			<input type="text" name="email" placeholder="이메일">
		</p>
		<p>
			<input type="password" name="pass" placeholder="비밀번호">
		</p>
		<p>
			<input type="submit" value="로그인">
		</p>
	</form>
</body>
</html>