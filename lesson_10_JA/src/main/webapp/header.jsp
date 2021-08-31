<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">

    <link rel="stylesheet" href="css/header.css">

    <title>Header</title>
</head>

<body>
    <div id="wrapper" class="animate">
        <nav class="navbar header-top fixed-top navbar-expand-lg navbar-dark bg-dark">
            <span class="navbar-toggler-icon leftmenutrigger"></span>
            <a class="navbar-brand" href="#">Інтернет-магазин журналів</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText"
                aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarText">
                <ul class="navbar-nav side-nav animate">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/cabinet.jsp">Головна<span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item create-magazine">
                        <a class="nav-link" href="${pageContext.request.contextPath}/createMagazine.jsp">Додати журнал</a>
                    </li>
                    <li class="nav-item subscribes">
                        <a class="nav-link" href="${pageContext.request.contextPath}/subscribes.jsp">Підписки на журнали</a>
                    </li>
                </ul>

                <ul class="navbar-nav ml-md-auto d-md-flex">
                    <li class="nav-item user">${firstName} ${lastName}</li>
                    <li class="nav-item">
                        <a class="nav-link product-logout" onclick="logout()">Вихід</a>
                    </li>
                </ul>
            </div>
        </nav>
    </div>
</body>

</html>