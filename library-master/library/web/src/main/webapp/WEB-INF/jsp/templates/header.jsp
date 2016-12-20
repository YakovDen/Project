<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
  
 <div style="text-align: right;"> <a href="?language=en"><b>[EN]</b></a>	|	<a href="?language=ru"><b>[RU]</b></a></div>
 
 <div style="text-align: left;">
 
 	<security:authorize access="hasRole('u')">
 		<div>
	 		<spring:message code="user"/>		<security:authentication property="principal.realName" />
	 		[<a href="/web/logout"><spring:message code="logout"/></a>]
 		</div>
 	</security:authorize>
 	
 	<security:authorize access="hasRole('a')">
 		<div>
 			<spring:message code="admin"/>		<security:authentication property="principal.realName" />
 			[<a href="/web/logout"><spring:message code="logout"/></a>]
 		</div>
 	</security:authorize>
 	
 </div>
 
 <br><br><br>
