<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>

<div align="center">
	<p><spring:message code="search"/><br></p>
	<form name="search" action="/web/book/search" method="POST">
	<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}"/>
	<spring:message code="search.request"/> <input type="text" name="searching" size="50" maxlength="50"><br><br>
	<input type="submit" name="submit" value="<spring:message code="show"/>">
	</form>	
</div>

<p><spring:message code="reading.list"/> [<a href="/web/books/orders_u/inreading/1"><spring:message code="show"/></a>]</p><br>
<p><spring:message code="order.list"/> [<a href="/web/books/orders_u/inorder/1"><spring:message code="show"/></a>]</p><br>