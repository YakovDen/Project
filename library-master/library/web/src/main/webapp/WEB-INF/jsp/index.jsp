<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><tiles:getAsString name="title" ignore="true" /></title>
</head>
<body>
 <div style="text-align: right;"> <a href="?language=en"><b>[EN]</b></a>	|	<a href="?language=ru"><b>[RU]</b></a></div>
 <br><br>
<div style="text-align: center"><h1> <spring:message code="page.main.entrance"/> </h1></div><br><br>
<div style="text-align: center">

<security:authorize access="!isAuthenticated()">
<a href="/web/login"><img src="/web/pic/book.jpg" hspace="20" /></a>
</security:authorize>


<security:authorize access="isAuthenticated()">

	<security:authorize access="hasRole('ROLE_u')">
		<c:redirect url="/user/index"/>
	</security:authorize>
	
	<security:authorize access="hasRole('ROLE_a')">
		<c:redirect url="/admin/index"/>
	</security:authorize>
	
</security:authorize>


</div>
</body>
</html>