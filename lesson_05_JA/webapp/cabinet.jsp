<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Особистий кабінет користувача</title>
</head>
<body>

		<h1 align="center">${userFirstName} ${userLastName}, Ви успішно ${userAction} в
			інтернет-магазині!</h1>
		<a align="center" href="${pageContext.request.contextPath}/index.jsp">На головну</a>
</body>
</html>