<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>

<h1><spring:message code="order.list"/>:</h1>

<table border="1">

	<tr>
		<td width="300"><spring:message code="reader"/></td>
		<td width="300"><spring:message code="book"/></td>
		<td width="200"><spring:message code="give.status"/></td>
		<td width="300"><spring:message code="order.date"/></td>
		<td width="200"><spring:message code="give"/></td>
	</tr>

	<c:forEach var="order" items="${orders}">
		<tr>
			<td width="300">
				<c:out value="${order.user.secondName}"/><br>
				<c:out value="${order.user.name}"/><br>
				<c:out value="${order.user.email}"/>
			</td>
			<td width="300">
				<c:out value="${order.inventory.book.title}"/><br>
				<c:out value="${order.inventory.book.writer}"/><br>
				<c:out value="${order.inventory.book.year}"/><br>
				ISBN: <c:out value="${order.inventory.book.isbn}"/>
			</td>
			<td width="200">
				<c:out value="${order.status}"/>
			</td>
			<td width="300">
				<c:out value="${order.dateOrder}"/>
			</td>
			<td width="200">
				<form name="toGiveBook" action="/web/admin/give_take/give" method="POST">
				<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}"/>
					<input type="hidden" name="order_id" value="${order.id}">
					<spring:message code="days"/>:<input type="text" name="days" size="5" pattern="^[0-9]+$" maxlength="2"><br>
					<input type="submit" name="submit" value="<spring:message code="give"/>">
				</form>
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
		        <td>[<a href="/web/books/orders_a/inorder/${page - 1}/${reader_id}"><spring:message code="previous"/></a>]</td>
		    </c:if>
		    
		    <%--For displaying Current page --%>
		        <td>
		            <c:forEach begin="1" end="${totPages}" var="i">
		                <c:choose>
		                    <c:when test="${page eq i}">
		                        <td>${i}</td>
		                    </c:when>
		                    <c:otherwise>
		                        <td><a href="/web/books/orders_a/inorder/${i}/${reader_id}">${i}</a></td>
		                    </c:otherwise>
		                </c:choose>
		            </c:forEach>
		        </td>
	            
	        <%--For displaying Next link --%>
    		<c:if test="${totPages > 1  and page < totPages}">
		        <td>[<a href="/web/books/orders_a/inorder/${page + 1}/${reader_id}"><spring:message code="next"/></a>]</td>
		    </c:if>  
        </tr>
    </table>