<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
	<title>Error</title>
</head>

<style>
body {
    background: #c0c0c0; /* Цвет фона */
    color: #f00; /* Цвет текста */
   }
</style>

<body>

<p><b>Touragent:<c:out value=" "/>
<c:out value="${sessionScope.user.getLastName()}"/>
<c:out value=" "/> 
<c:out value="${sessionScope.user.getFirstName()}"/></b>[<a href="/webs/AdminController?operation=logout">Lod out</a>]</p><br><br>
<h3>Customer doesn't exist with the Id </h3>
<p><a href="/webs/AdminController">Go back to the main page</a></p>
</body>
</html>