<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
  
 <div style="text-align: right;"> <a href="?language=en"><b>[EN]</b></a>	|	<a href="?language=ru"><b>[RU]</b></a></div>
 
 <div style="text-align: left;"> 
 	
 		<div>
	 		<spring:message code="user"/>		
	 		[<a href="/web/UserController/logout"><spring:message code="logout"/></a>]
 		</div>
 	 	
 		<div>
 			<spring:message code="admin"/>		
 			[<a href="/webs/AdminController/logout"><spring:message code="logout"/></a>]
 		</div>
 	
 	
 </div>