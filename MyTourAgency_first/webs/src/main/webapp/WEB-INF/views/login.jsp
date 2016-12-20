<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%> --%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>

<!-- <title>Insert title here</title> -->


<style type="text/css">
.form-1 {
	font-family: 'Ubuntu', 'Lato', sans-serif;
	font-weight: 400;
	/* Размер и положение */
	width: 300px;
	position: relative;
	margin: 60px auto 30px;
	padding: 10px;
	overflow: hidden;
	/* Стили */
	background: #111;
	border-radius: 0.4em;
	border: 1px solid #191919;
	box-shadow: inset 0 0 2px 1px rgba(255, 255, 255, 0.08), 0 16px 10px
		-8px rgba(0, 0, 0, 0.6);
}

.form-1 label {
	/* Размер и положение */
	width: 50%;
	float: left;
	padding-top: 9px;
	/* Стили*/
	color: #ddd;
	font-size: 12px;
	text-transform: uppercase;
	letter-spacing: 1px;
	text-shadow: 0 1px 0 #000;
	text-indent: 10px;
	font-weight: 700;
	cursor: pointer;
}

.form-1 input[type=text], .form-1 input[type=password] {
	/* Размер и положение */
	width: 50%;
	float: left;
	padding: 8px 5px;
	margin-bottom: 10px;
	font-size: 12px;
	/* Стили */
	background: linear-gradient(#1f2124, #27292c);
	border: 1px solid #000;
	box-shadow: 0 1px 0 rgba(255, 255, 255, 0.1);
	border-radius: 3px;
	/* Стили шрифтов */
	font-family: 'Ubuntu', 'Lato', sans-serif;
	color: #fff;
}

.form-1 input[type=text]:hover, .form-1 input[type=password]:hover,
	.form-1 label:hover ~ input[type=text], .form-1 label:hover ~ input[type=password]
	{
	background: #27292c;
}

.form-1 input[type=text]:focus, .form-1 input[type=password]:focus {
	box-shadow: inset 0 0 2px #000;
	background: #494d54;
	border-color: #51cbee;
	outline: none; /* Убираем внешние линии в Chrome */
}

.form-1 p:nth-child(3), .form-1 p:nth-child(4) {
	float: left;
	width: 50%;
}

.form-1 label[for=remember] {
	width: auto;
	float: none;
	display: inline-block;
	text-transform: capitalize;
	font-size: 11px;
	font-weight: 400;
	letter-spacing: 0px;
	text-indent: 2px;
}

.form-1 input[type=checkbox] {
	margin-left: 10px;
	vertical-align: middle;
}

.form-1 input[type=submit] {
	/* Ширина и положение */
	width: 100%;
	padding: 8px 5px;
	/* Стили */
	border: 1px solid #0273dd; /* Откат */
	border: 1px solid rgba(0, 0, 0, 0.4);
	box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.3), inset 0 10px 10px
		rgba(255, 255, 255, 0.1);
	border-radius: 3px;
	background: #38a6f0;
	cursor: pointer;
	/* Стили шрифтов */
	font-family: 'Ubuntu', 'Lato', sans-serif;
	color: white;
	font-weight: 700;
	font-size: 15px;
	text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.8);
}

.form-1 input[type=submit]:hover {
	box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.6);
}

.form-1 input[type=submit]:active {
	background: #287db5; <
	box-shadow: inset 0 0 3px rgba(0, 0, 0, 0.6);
	border-color: #000; /* Откат */
	border-color: rgba(0, 0, 0, 0.9);
}

.no-boxshadow .form-1 input[type=submit]:hover {
	background: #2a92d8;
}

/* Линия градиента */
.form-1:after {
	/* Размер и положение */
	content: "";
	height: 1px;
	width: 33%;
	position: absolute;
	left: 20%;
	top: 0;
	/* Стили */
	background: linear-gradient(left, transparent, #444, #b6b6b8, #444, transparent);
}

/* Маленькая вспышка */
.form-1:before {
	/* Размер и положение */
	content: "";
	width: 8px;
	height: 5px;
	position: absolute;
	left: 34%;
	top: -7px;
	/* Стили*/
	border-radius: 50%;
	box-shadow: 0 0 6px 4px #fff;
}

.form-1 p:nth-child(1):before {
	/* Размер и положение */
	content: "";
	width: 250px;
	height: 100px;
	position: absolute;
	top: 0;
	left: 45px;
	/* Стили */
	transform: rotate(75deg);
	background: linear-gradient(50deg, rgba(255, 255, 255, 0.15),
		rgba(0, 0, 0, 0));
	pointer-events: none;
}

.no-pointerevents .form-1 p:nth-child(1):before {
	display: none;
}
</style>

</head>
<body bgcolor="gainsboro">
<br>
	<div style="text-align: center">
		<c:if test="${not empty error}">
	        <b><em>${error}</em></b>
	    </c:if>
	</div>
	<br>

	<div class="form-1">
	<c:url value="/in_login" var="loginUrl" />
		<!-- <form class="form-1" action="/webs/login" method="GET"> -->
		<form class="form-1" action="${loginUrl}" method="POST">
		<!-- <form class="form-1" action="/j_spring_security_check1" method="POST"> -->
			<p class="clearfix">
				<label for="login">Username</label> <input type="text" name="login"
					id="login" placeholder="Username">
			</p>

			<p class="clearfix">
				<label for="password">Password</label> <input type="password"
					name="password" id="password" placeholder="Password">
			</p>

			<p class="clearfix">
				<input type="checkbox" name="remember" id="remember"> <label
					for="remember">Remember me</label>
			</p>
			<p class="clearfix">
				<input type="submit" name="submit" value="Sign in">
			</p>

		</form>
	</div>
</body>
</html>
