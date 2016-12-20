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
	    	<c:out value="${sessionScope.user.getFirstName()}"/>
	    	<c:out value=" "/>
	    	<c:out value="${sessionScope.user.getLastName()}"/>
	    	<c:out value=" "/>	    	
	    	(<c:out value="${sessionScope.user.getUserName()}"/>), you are currently client to:
	    </h4>

<c:out value="Time: ${calend.time}"/>
	<h1>Welcome back, Customer!</h1>
	<h4>Tours in database:</h4>
	<table >
        	<tr >
        		<th>Id</th>
        		<th>Name</th>
        		<th>DateOfBiginning</th>
        		<th>DateEnd</th>
        		<th>NumberOfNights</th>
        		<th>Cost</th>
        		<th>KindOfTour</th>        		
        	</tr>        	
        	<c:forEach var="p" items="${requestScope.toursReserved}"> 
            	<tr>
            		<td align="center"><c:out value="${p.id_tour}"/></td>           		
            		<td align="center"><c:out value="${p.name}" /></td>
					<td align="center"><c:out value="${p.dateOfBeginning}" /></td>
					<td align="center"><c:out value="${p.dateEnd}" /></td>					
					<td align="center"><c:out value="${p.numberOfNights}" /></td>
					<td align="center"><c:out value="${p.cost}" /></td>	
					<td align="center"><c:out value="${p.kindOfTour.kindOfTour}"/></td>															           		        		            		
        		<tr/>
        	</c:forEach>
        </table>
  <br>  
    <%--For displaying Previous link except for the 1st page --%>
    <c:if test="${currentPage != 1}">
        <td><a href="/webs/UserController?page=${currentPage - 1}">Previous</a></td>
    </c:if>   
    
       <%--For displaying Page numbers. 
    The when condition does not display a link for the current page--%>
    <table border="1" cellpadding="5" cellspacing="5">
        <tr>
            <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <td>${i}</td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="/webs/UserController?page=${i}">${i}</a></td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </tr>
    </table> 
     
     <%--For displaying Next link --%>
    <c:if test="${currentPage lt noOfPages}">
        <td><a href="/webs/UserController?page=${currentPage + 1}">Next</a></td>
    </c:if>   
        	<h4>Reservation tour</h4>
	
	<head>
	<style type="text/css">
  .block1 { 
    width: 110px; 
    background: #cf0; 
    padding: 5px; 
    border: solid 1px black; 
    float: left; 
    position:  relative; 
    top: -4px; 
    left: 2px; 
   }    
   
form input[type=submit] { /* оформление для кнопки submit */
	color: #ff1100;
	margin-left: 2px;
	cursor: pointer; /* меняем указатель для кнопки */
}

form .formSubmit { /* класс для ие6 */
	margin-left: 65px;
	cursor: pointer;
	width: auto	
}

</style>
	</head>

<form action="/webs/UserController" method="get">
	<strong><class="main"></strong>
			<div class="block1">
				<label for="n">Choose  Id tour:</label>
				<input name="id_tour" type="text" size="5" />
		    </div>		    			   
 	<strong></strong>			 
	<br />
	<br />
	<br />
	<input type="hidden" name="operation" value="reservationstour">
	<input type="submit"	value="Reservations">			   
			  
	</form>	
		
		<h4>Current customer chose this tour</h4>
	<table >
        	<tr >        		
        		<th>FirstName</th>
        		<th>LastName</th>
        		<th>Tours</th>        		        		
        	</tr>        	
        	<c:forEach var="l" items="${requestScope.tourForClient}"> 
            	<tr>              	
            		<td align="center"><c:out value="${l.firstName}"/></td>           		
            		<td align="center"><c:out value="${l.lastName}" /></td>   				
				    <td align="center"><a href="/webs/UserController?operation=tourcount&id_user=${l.getId_user()}"><c:out value="${l.TourCount()}" /></a></td>							           		        		            		
        		<tr/>
        	</c:forEach>
        </table>	
		<br />
		<br />
		<a href="/webs/UserController?operation=logout">Log out</a>

</body>
</html>