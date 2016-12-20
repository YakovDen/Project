<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TourAgency</title>

<style type="text/css"> 
table {
	border-collapse: collapse; /* Отображать двойные линии как одинарные */
}

th {
	background: #ccc; /* Цвет фона */
	text-align: left; /* Выравнивание по левому краю */
}

td, th {
	border: 1px solid #800; /* Параметры границы */
	padding: 4px; /* Поля в ячейках */
}
</style>

</head>
<body bgcolor="aliceblue">  
	<h4>
			Tours  booked
	    	<c:out value="${sessionScope.user.getFirstName()}"/>
	    	<c:out value=" "/>
	    	<c:out value="${sessionScope.user.getLastName()}"/>
	    	<c:out value=" "/>	    	
	    </h4>
	<table >
        	<tr >
        		<th>Id</th>         		       		
        		<th>Name tour</th>
        		<th>Cost</th>
        		<th>TourPaid</th>        		     		
        	</tr> 
        	<c:set var="limit" value="${0}"/>               	
        	<c:forEach var="n" items="${requestScope.allToursForClient}">         	
            	<tr>
					<c:if test="${n.idUT gt 0}">           				 
            				<c:set var="limit" value="${limit+1}"/>
       				 </c:if>
       				 <td align="center"><c:out value="${limit}"/>
            		<%-- <td align="center"><c:out value="${n.idUT}"/>  --%>           					
					<td align="center"><c:out value="${n.tour.name}" /></td>					
					<td align="center"><c:out value="${n.tour.cost}" /></td>																
					<td align="center"><c:out value="${n.isPaid()}" /></td>	
					
					<td align="center">
					<c:if test="${n.isPaid()}">
                    <a href="/webs/UserController?operation=paytour&idUT=${n.getIdUT()}&id_user=${n.getUser().getId_user()}&isPaid=false" >UnPay</a>
                    </c:if>
                    <c:if test="${!n.isPaid()}">
					<a href="/webs/UserController?operation=paytour&idUT=${n.getIdUT()}&id_user=${n.getUser().getId_user()}&isPaid=true" >Pay</a>
                    </c:if>
					 </td>								           		        		            		
        		<tr/>
        	</c:forEach>
        </table>	
		<br />
		<br />		
		<p><a href="/webs/UserController">Go back to the main page</a></p>
</body>
</html>