<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>

<h1><spring:message code="reading.list"/>:</h1>

<table border="1">

	<tr>
		<td width="300"><spring:message code="reader"/></td>
		<td width="300"><spring:message code="book"/></td>
		<td width="200"><spring:message code="give.status"/></td>
		<td width="200"><spring:message code="give.date"/></td>
		<td width="200"><spring:message code="take.date"/></td>
		<td width="200"><spring:message code="take"/></td>
	</tr>

	<c:forEach var="reading" items="${orders}">
		<tr>
			<td width="300">
				<c:out value="${reading.user.secondName}"/><br>
				<c:out value="${reading.user.name}"/><br>
				<c:out value="${reading.user.email}"/>
			</td>
			
			<td width="300">
				<c:out value="${reading.inventory.book.title}"/><br>
				<c:out value="${reading.inventory.book.writer}"/><br>
				<c:out value="${reading.inventory.book.year}"/><br>
				ISBN: <c:out value="${reading.inventory.book.isbn}"/>
			</td>
			<td width="200">
				<c:out value="${reading.status}"/>
			</td>
			<td width="200">
				<c:out value="${reading.dateOn}"/>
			</td>
			<td width="200">
				<c:out value="${reading.dateOff}"/>
			</td>
			<td width="200">
				[<a href="/web/admin/give_take/take/${reading.id}"><spring:message code="take"/></a>]
			</td>
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
		        <td>[<a href="/web/books/orders_a/inreading/${page - 1}/${reader_id}"><spring:message code="previous"/></a>]</td>
		    </c:if>
		    
		    <%--For displaying Current page --%>
		        <td>
		            <c:forEach begin="1" end="${totPages}" var="i">
		                <c:choose>
		                    <c:when test="${page eq i}">
		                        <td>${i}</td>
		                    </c:when>
		                    <c:otherwise>
		                        <td><a href="/web/books/orders_a/inreading/${i}/${reader_id}">${i}</a></td>
		                    </c:otherwise>
		                </c:choose>
		            </c:forEach>
		        </td>
	            
	        <%--For displaying Next link --%>
    		<c:if test="${totPages > 1  and page < totPages}">
		        <td>[<a href="/web/books/orders_a/inreading/${page + 1}/${reader_id}"><spring:message code="next"/></a>]</td>
		    </c:if>  
        </tr>
    </table>
    