<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Інтернет-магазин журналів</title>
<style>
form {
	margin: 100px 0px 320px 0px;
}
</style>
</head>

<body>
	<jsp:include page="header.jsp"></jsp:include>
	
		<h1 align="center" >Ласкаво просимо в інтернет-магазин журналів!</h1>

		<form align="center">
			<button align="center" type="button" onClick="location.href='register.jsp'">Регістрація</button>
			<button align="center" type="button" onClick="location.href='login.jsp'">Вхід</button>
			<br>
		</form>

	<jsp:include page="footer.jsp"></jsp:include>
</body>

</html>