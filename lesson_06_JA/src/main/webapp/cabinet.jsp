<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Особистий кабінет користувача</title>
<style>
div {
	width: 150px;
	height: 100px;
	margin: 50px auto;
}
</style>
</head>
<body>

	<h1 align="center">${userFirstName}${userLastName},Ви успішно
		${userAction} в інтернет-магазині!</h1>
	<div>
		<a href="${pageContext.request.contextPath}/index.jsp"><button>
				<h2>Продовжити</h2>
			</button></a>
	</div>
</body>
</html>