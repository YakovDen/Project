<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome page</title>
</head>
<style>
body {
    background: #c0c0c0; /* Цвет фона */
    color: #f00; /* Цвет текста */
   }
</style>
<body >  
	<h1>Success</h1>
	<c:url var="logout" value="/logout"/>
	<a href="${logout}">Logout</a>	
</body>
</html>