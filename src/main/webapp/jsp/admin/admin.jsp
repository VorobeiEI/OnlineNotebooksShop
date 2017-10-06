<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="ctg" uri="mytag"%>
<!DOCTYPE html>
<html>
   <head>
      <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
      <link rel="stylesheet" href="css/main.css" type="text/css">
   </head>
   <body>
   
      <!-- include header -->
      <jsp:include page="/jsp/site/components/header.jsp"></jsp:include>
      
      <div class="container">
         
      <form name="AdminForm" method="POST" action="controller">
         <input type="hidden" name="command" value="admin" /> 
         XML Path:<br /> 
         <input type="submit" name ="create" value="create" />
         <input type="text" name="xmlPath" value="" /> specify the absolute path of XML
         <br /> 
         ${productCreatedMessage} 
         ${errorsSearchXMLMessage}
         ${errorParseXMLMessage}
         ${errorXMLnotValid}
         ${chageStatusSuccesMessage} 
         <br /> 
         <input type="submit" name ="showProducts" value="show Products" />
         <input type="submit" name ="showCustomers" value="show Customers" />
         <input type="submit" name ="showOders" value="show Oders" />

      </form>
	  
	  <!-- table for view all product from data base -->
      <table class="table table-bordered">
         <tbody>
            <c:forEach items="${productList.getGoods()}" var="product">
               <tr>
                  <td align="left">Product: ${product.name}</td>
                  <td align="left">Product_ID: ${product.id}</td>
                  <td align="left">Price: ${product.price}</td>
                  <td align="left">Quantity: ${product.quantity}</td>
                  <td align="left">Description: ${product.description}</td>
                  <td align="left">ProducerId: ${product.producerId}</td>
               </tr>
            </c:forEach>
         </tbody>
      </table>

	  <!-- table for view all users from data base -->
      <table class="table table-bordered">
         <tbody>
            <c:forEach items="${userList}" var="user">
               <tr>
                  <td align="left">UserID: ${user.id}</td>
                  <td align="left">Name: ${user.name}</td>
                  <td align="left">CreationDate: ${user.creationDate}</td>
                  <td align="left">Email: ${user.email}</td>
                  <td align="left">Phone: ${user.phone}</td>
                  <td align="left">Role: ${user.role}</td>
                  <td align="left">Status: ${user.status}</td>
                  <td>
                     <form name="OrderListForm" method="POST" action="controller">
                        <input type="hidden" name="command" value="admin" /> 
                        <input type="hidden" name="email" value="${user.email}" />
                        <input type="submit" email="" name="changeUserStatus" value="change" />
                     </form>
                  </td>
               </tr>
            </c:forEach>
         </tbody>
      </table>
	
	  <!-- table for view all orders from data base -->
      <table class="table table-bordered">
         <tbody>
            <c:forEach items="${oderList}" var="order">
               <tr>
                  <td align="left">OrderID: ${order.id}</td>
                  <td align="left">Name: ${order.sum}</td>
                  <td align="left">User_ID: ${order.userId}</td>
                  <td align="left">Status: ${order.status}</td>
                  <td>
                     <form name="OrderListForm" method="POST" action="controller">
                        <input type="hidden" name="command" value="admin" /> 
                        <input type="hidden" name="orderID" value="${order.id}" /> 
                        <input type="submit" id="" name="complete" value="complete" />
                     </form>
                  </td>
               </tr>
            </c:forEach>
         </tbody>
      </table>

      <!-- include footer -->
      <jsp:include page="/jsp/site/components/footer.jsp"></jsp:include>
   </body>
</html>