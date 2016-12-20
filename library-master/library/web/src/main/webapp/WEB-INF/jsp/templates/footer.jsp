<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
   
   <br>
   <br>
   <br> 
     
   <div style="text-align: left;">
 
 	<security:authorize access="hasRole('u')">
 		<div>
	 		[<a href="/web/user/index"><em><b><spring:message code="to.main.page"/></b></em></a>]
 		</div>
 	</security:authorize>
 	
 	<security:authorize access="hasRole('a')">
 		<div>
 			[<a href="/web/admin/index"><em><b><spring:message code="to.main.page"/></b></em></a>]
 		</div>
 	</security:authorize>
 	
 </div>