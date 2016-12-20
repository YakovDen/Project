<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>

<div align="center">
<p><em><spring:message code="book.del"/></em></p>
	<form name="delBook" action="/web/admin/add_delete/delete" method="POST">
	<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}"/>
	<spring:message code="inventory.id"/> <input type="text" name="inventory_id" size="30" pattern="^[0-9]+$" maxlength="30"><br><br>
	<input type="submit" name="submit" value="<spring:message code="delete"/>">
	</form>
</div>

