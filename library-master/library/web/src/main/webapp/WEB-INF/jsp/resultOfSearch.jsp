<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>

<h1><spring:message code="search.result"/>:</h1>
<div>
<table border="1">
	<tr>
		<td width="300"><spring:message code="title"/></td>
		<td width="300"><spring:message code="writer"/></td>
		<td width="150"><spring:message code="add.book.year"/></td>
		<td width="300">ISBN</td>
		<security:authorize access="hasRole('ROLE_u')">
			<td width="400"><spring:message code="open.order"/></td>
		</security:authorize>
	</tr>
	<c:forEach var="book" items="${searchedBooks}">
		<tr>
			<td width="300"><c:out value="${book.title}"/></td>
			<td width="300"><c:out value="${book.writer}"/></td>
			<td width="50"><c:out value="${book.year}"/></td>
			<td width="300"><c:out value="${book.isbn}"/></td>
			<security:authorize access="hasRole('ROLE_u')">
					<td width="400">
						<form name="order" action="/web/order/openOrder" method="POST">
							<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}"/>
							<input type="hidden" name="readerId" value="${LoginUser.user_id}">
							<input type="hidden" name="inventory_id" value="${book.id}">
							<input type="radio" name="status" value="абонемент">  <spring:message code="subscription"/> <br>
							<input type="radio" name="status" value="читальный зал" checked>  <spring:message code="reading.room"/> <br>
							<input type="submit" name="submit" value="<spring:message code="order"/>">
						</form>
					</td>
			</security:authorize>
		</tr>
	</c:forEach>
</table>

<br><br>

<%--For displaying Page numbers. 
    The when condition does not display a link for the current page--%>
    <table>
        <tr>
	        <%--For displaying Previous link except for the 1st page --%>
		    <c:if test="${page != 1}">
		        <td>[<a href="/web/book/search/${page-1}"><spring:message code="previous"/></a>]</td>
		    </c:if>
		    
		    <%--For displaying Current page --%>
		        <td>
		            <c:forEach begin="1" end="${totPages}" var="i">
		                <c:choose>
		                    <c:when test="${page eq i}">
		                        <td>${i}</td>
		                    </c:when>
		                    <c:otherwise>
		                        <td><a href="/web/book/search/${i}">${i}</a></td>
		                    </c:otherwise>
		                </c:choose>
		            </c:forEach>
		        </td>
	            
	        <%--For displaying Next link --%>
    		<c:if test="${totPages > 1  and page < totPages}">
		        <td>[<a href="/web/book/search/${page+1}"><spring:message code="next"/></a>]</td>
		    </c:if>  
        </tr>
    </table>
    </div>