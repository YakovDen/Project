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

<br>

<p><em><spring:message code="book.take"/></em><br> </p>
	<form name="showReading" action="/web/books/orders_a/inreading" method="POST">
	<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}"/>
	<spring:message code="reader.id"/> <input type="text" name="reader_id" size="30" pattern="^[0-9]+$" maxlength="30"><br><br>
	<input type="submit" name="submit" value="<spring:message code="show"/>">
	</form>
	
<br><br>

<p><em><spring:message code="book.give"/></em><br></p>
	<form name="showOrder" action="/web/books/orders_a/inorder" method="POST">
	<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}"/>
	<spring:message code="reader.id"/> <input type="text" name="reader_id" size="30"  pattern="^[0-9]+$"maxlength="30"><br><br>
	<input type="submit" name="submit" value="<spring:message code="show"/>">
	</form>

<br>

<p><spring:message code="all.orders"/>[<a href="/web/books/orders/all"><spring:message code="show"/></a>]</p><br>

<p><spring:message code="book.overdue"/> [<a href="/web/books/orders/overDate"><spring:message code="show"/></a>]</p><br>

<p>[<a href="/web/admin/add_delete?new"><spring:message code="book.add"/></a>]</p><br>

<p>[<a href="/web/admin/add_delete?del"><spring:message code="book.del"/></a>]</p><br>

