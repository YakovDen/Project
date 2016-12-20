<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><tiles:getAsString name="title" ignore="true" /></title>
	<style type="text/css">
   .form { 
    margin-left: 44%;
    background: #ccc;
    padding: 10px;
    width: 200px; 
    padding-right: 20px; 
    border: solid 1px black; 
    float: center;
    text-align: center;
   }
  </style> 
</head>
	<body>
	<div style="text-align: center">
		<c:if test="${not empty error}">
	        <b>${error}</b>
	    </c:if>
	</div>
	<br>
	<div style="text-align: center">
		<c:if test="${not empty exception}">
	        <b><em>${exception}</em></b>
	    </c:if>
	</div>
	<br>
		<div class="form">
		<c:url value="/in_login" var="loginUrl" />
			<form action="${loginUrl}" method="POST">
			<div>
				<label for="email">E-mail:</label>	
				<input type="email" id="email" name="username" placeholder="Enter e-mail" required="required">
			</div>
			<br>
			<div>
				<label for="password">Password:</label>
				<input type="password" id="password" name="password" placeholder="Enter password" required="required">
			</div>
				<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}"/>
				<br>
			<div>
				<input type="submit" name="submit" value="Log in"/>
			</div>
			</form>
		</div>
	</body>
</html>

