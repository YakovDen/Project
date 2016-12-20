<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>

<div align="center">
<p><em><spring:message code="book.new"/></em></p>
	<sf:form id="addBook" name="addBook" action="/web/admin/add_delete/add" modelAttribute="book" method="POST">
	<fieldset>
	<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}"/>

	<table>
	<tr>
		<td><spring:message code="add.book.title"/></td>
		<td>
			<sf:errors path="title" cssStyle="color: red" />
			<sf:input path="title" id="title" size="50" maxlength="200"/>
		</td>
	</tr>
	<tr>
		<td><spring:message code="add.book.writer"/></td>
		<td>
			<sf:errors path="writer" cssStyle="color: red" />
			<sf:input path="writer" id="writer" size="50" maxlength="200"/>
		</td>
	</tr>
	<tr>
		<td><spring:message code="add.book.year"/></td>
		<td>
			<sf:errors path="year" cssStyle="color: red" />
			<sf:input path="year" type="text" id="year" size="50" maxlength="4"/>
		</td>
	</tr>
	<tr>
		<td><spring:message code="add.book.isbn"/> </td>
		<td>
			<sf:errors path="isbn" cssStyle="color: red" />
			<sf:input path="isbn" type="text" name="isbn" size="50" maxlength="20"/>
		</td>
	</tr>
	<tr>
		<td><spring:message code="add.book.number"/></td>
		<td>
			<input type="text" name="number" size="10" pattern="^[0-9]+$" maxlength="4">
		</td>
	</tr>
	<tr><td><input type="submit" name="submit" value="Add"></td></tr>
	</table>
	</fieldset>
	</sf:form>
</div>