<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Форма авторизації</title>
</head>

<body>
    <jsp:include page="header.jsp"></jsp:include>
    <h1 align="center">Форма авторизації</h1>
    <form align="center" action="logging" method="post">
        <input align="center" name="login" type="text" placeholder="Логін"><br><br>
        <input align="center" name="password" type="password" placeholder="Пароль"><br><br>
        <input align="center" type="submit" value="Вхід">
    </form>
    <jsp:include page="footer.jsp"></jsp:include>
</body>

</html>