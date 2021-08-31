<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Форма регистрації</title>
</head>

<body>
    <jsp:include page="header.jsp"></jsp:include>
    <h1 align="center">Форма реєстрації</h1>
    <form align="center" action="registering" method="post">
        <input align="center" name="firstName" type="text" placeholder="І'мя"><br><br>
        <input align="center" name="lastName" type="text" placeholder="Прізвище"><br><br>
        <input align="center" name="email" type="text" placeholder="Логін"><br><br>
        <input align="center" name="password" type="password" placeholder="Пароль"><br><br>
        <input align="center" name="accessLevel" type="radio" id="user" value="user" checked>
        <label align="center" for="user">Користувач</label><br>
        <input align="center" name="accessLevel" type="radio" id="admin" value="admin">
        <label align="center" for="admin">Адміністратор</label><br><br>
        <input align="center" type="submit" value="Відправити">
    </form>
    <jsp:include page="footer.jsp"></jsp:include>
</body>

</html>