<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TourAgency</title>

  </style>
<style type="text/css">

 body {
  	font: 11pt Arial, Helvetica, sans-serif;
    margin:0px; padding:5px;
    background: #7b7; /* Цвет фона */
    color: #000; /* Цвет текста */
   }
table {
	border-collapse: collapse; /* Отображать двойные линии как одинарные */
}

caption {
  font-family: annabelle, cursive;
  font-weight: bold;
  font-size: 2em;
  padding: 10px; 
  color: #F3CD26;
  text-shadow: 1px 1px 0 rgba(0,0,0,.3);
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
<body>
		<h4>
	    	<c:out value="${sessionScope.user.getFirstName()}"/>
	    	<c:out value=" "/>
	    	<c:out value="${sessionScope.user.getLastName()}"/>
	    	<c:out value=" "/>	    	
	    	(<c:out value="${sessionScope.user.getUserName()}"/>), you are currently touragent to:
	    </h4>

<c:out value="Current date: ${calend.time}"/>
	<h1>Welcome back, God!</h1>		
	<table border="1px">
	<caption>Tours in database</caption>
		<tr bgcolor="#ccc">
			<th>Id</th>
			<th>DateOfBeginning</th>
			<th>DateEnd</th>
			<th>Name</th>
			<th>NumberOfNights</th>
			<th>Cost</th>
			<th>Discount</th>
			<!-- <th>Id_TypeOfTour</th> -->
			<th>TypeOfToure</th>
		<!-- 	<th>Id_KindOfToure</th> -->
			<th>KindOfToure</th>
		</tr>		
		<c:forEach var="p" items="${requestScope.tours}">			
			<tr>			
				<td align="center"><c:out value="${p.id_tour}" />
				<td align="center"><c:out value="${p.dateOfBeginning}" /></td>
				<td align="center"><c:out value="${p.dateEnd}" /></td>
				<td align="center"><c:out value="${p.name}" /></td>
				<td align="center"><c:out value="${p.numberOfNights}" /></td>
				<td align="center"><c:out value="${p.cost}" /></td>
				<td align="center"><c:out value="${p.discount}" /></td>		
				<td align="center"><c:out value="${p.typeOfTour.typeOfTour}"/></td> 				
				<td align="center"><c:out value="${p.kindOfTour.kindOfTour}"/></td>											
				<td align="center"> <a href="/webs/AdminController?operation=deletetour&id_tour=${p.getId_tour()}">Delete</a></td>  
			</tr>			
		</c:forEach>				
	</table>
	
			
	<h4>Add Tour?</h4>	
	<head>
	<style type="text/css">
.field {
	clear: both;
	text-align: right; <
	strong >line-height: 25px;
	</
	strong
	>
}

label {
	float: left; <
	strong >padding-right: 10px;
	</
	strong
	>
}

.main {
	float: left
}

form input[type=submit] { /* оформление для кнопки submit */
	color: #ff1100;
	margin-left: 40px;
	cursor: pointer; /* меняем указатель для кнопки */
	position:relative; left:-300px; top:180px
}

form .formSubmit { /* класс для ие6 */
	margin-left: 65px;
	cursor: pointer;
	width: auto;		
}

</style>
	</head>

<form action="/webs/AdminController" method="get">

			<strong><div class ="main"></strong>	
			<div class="field">
				<label for="n">DateOfBeginning:</label>
				<input name="dateOfBeginning" type="text" />
		    </div>
		    <div class="field">
				<label for="n">DateEnd:</label>
				<input name="dateEnd" type="text" />
		    </div>
		    <div class="field">
				<label for="n">Name:</label>
				<input name="name" type="text" />
		    </div>	
			<div class="field">
				<label for="n">NumberOfNights:</label>
				<input name="numberOfNights" type="text" />
		    </div>
			<div class="field">
				<label for="n">Cost:</label>
				<input name="cost" type="text" />
		    </div>
			<div class="field">
				<label for="n">Discount:</label>
				<input name="discount" type="text" />
		    </div>
		    		    
		    <div class="field">
				<label for="n">TypeOfTour:</label>			            
			<select  required size = "1"  name = "id_typeOfTour"  >  					
       			 <option >Выбрать тип тура</option> 	      
		        <option value = "1">Горящий</option>
		        <option value = "2">Обычный</option>		        
    		</select>						
		    </div>
		    			
		    <div class="field">
				<label for="n">KindOfTour:</label>			
				<select required size = "1"  name = "id_kindOfTour" >       			
       			 <option >Выбрать вид тура</option> 	      
		        <option value = "1">Отдых</option>
		        <option value = "2">Экскурсия</option>	
		        <option value = "3">Шоппинг</option>		        
    		</select>
		    </div>		     	
			<strong></div></strong>		
	<input type="hidden" name="operation" value="addtour">
	<input type="submit" value = "Add">	 
	</form>	
	
	<head>
	<style type="text/css">
#mainTable {
width: 50%;
height: 50%;
position: relative; left:-270px; top: 190px
}
#mainTable td {
text-align: center;
vertical-align: middle;
}

caption {
  font-family: annabelle, cursive;
  font-weight: bold;
  font-size: 2em;
  padding: 10px; 
  color: #F3CD26;
  text-shadow: 1px 1px 0 rgba(0,0,0,.3);
 }
</style>
</head>
	<body>	
	<table id="mainTable" border="1px" >
	<caption>Customers who have booked tour</caption>
		<tr bgcolor="#ccc">
			<th>Id</th>
			<th>FirstName</th>
			<th>LastName</th>
			<th align="center">Name and Cost of Tour</th>
			<th>DiscountByTour</th>
		</tr>
		
		<c:forEach var="t" items="${requestScope.clients}">	
			<tr>					
				<td align="center"><c:out value="${t.id_user}" />
				<td align="center"><c:out value="${t.firstName}" /></td>
				<td align="center"><c:out value="${t.lastName}" /></td>	
				<td align="center"><c:out value="${t.getToursNames()}" /></td>				
				<td align="center"><c:out value="${t.discountBytour}"/></td>									
			</tr>
		</c:forEach>			
	</table>
	</body>
	
	
	<head>
	<style type="text/css">
  .block1 { 
    width: 150px; 
    background: #cf0; 
    padding: 5px; 
    border: solid 1px black; 
    float: left; 
    position:  relative; 
    top:5px; 
    left: 1020px; 
   }    

.headerPosition {
position:relative !important;
margin-top: -90.652px !important; 
margin-left: 740px !important;

 color: #F3CD26;
  text-shadow: 1px 1px 0 rgba(0,0,0,.3);
}
</style>
	</head>
<body>
<form action="/webs/AdminController" method="get">	
  
        <div class="headerPosition"><h2>Discount client</h2></div>       
  
			<div class="block1">
				<label for="n">ID Client:</label>
				<input name="id_user" type="text" size="5" />
		    </div>
		    <div class="block1">
				<label for="n">Discount:</label>
				<input name="discountBytour" type="text" size="5" />
		    </div>	  			 
			<div style="position:relative; top:-135px; right:-955px;">
				<input type="hidden" name="operation" value="discounttour">
				<input type="submit" value = "Discount">	
			</div> 
	</form>
	</body>
	<body>
	<div style="position:absolute; top:890px; right:35px;">
		<a href="/webs/AdminController?operation=logout">Log out</a>
		</div>
	</body>
		 
</body>
</html>