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
         <div class="goods-forms text-center">
            <form name="MainForm" method="POST" action="controller">
               <input type="hidden" name="command" value="cabinet" />
               <input type="submit"  name="write" value="Write to mr. A" />
               <input type="submit" name="myOders" value="My oders" /> 
               <input type="submit" name="myInfo" value="My info" />
               <br />
            </form>
         </div>
         
         <!-- table with list of all user's orders -->
         <table class="table table-striped">
            <tbody>
               <c:forEach items="${userOderList}" var="order">
                  <tr>
                     <td align="left">OrderID: ${order.id}</td>
                     <td align="left">Amount: ${order.sum}</td>
                     <td align="left">Status: ${ctg:statusOrder(order.status)}</td>
                     <td>
                        <form name="ProductListForm" method="POST" action="controller">
                           <input type="hidden" name="command" value="cabinet" /> 
                           <input type="hidden" name="orderID" value="${order.id}" /> 
                           <input type="submit" id="" name="show" value="show details" />
                        </form>
                     </td>
                  </tr>
               </c:forEach>
            </tbody>
         </table>
         
         <!-- table with list of product from define order -->
         <table class="table table-striped">
            <tbody>
               <c:forEach items="${productInOrder}" var="product">
                  <tr>
                     <td align="left">Product Name: ${product.name}</td>
                     <td align="left">Description: ${product.description}</td>
                     <td align="left">Price: ${product.price}</td>
                  </tr>
               </c:forEach>
            </tbody>
         </table>
         
         <!-- table show current information about user -->
         <table>
            <tbody>
               <c:forEach items="${userInfo}" var="user">
                  <strong>Name: </strong>${user.name} <br />
                  <strong>Creation Date: </strong>${user.creationDate} <br />
                  <strong>Email: </strong>${user.email} <br />
                  <strong>Phone:</strong> ${user.phone} <br />
                  <form name="editform" method="POST" action="controller">
                     <input type="hidden" name="command" value="cabinet" />
                     <input type="submit" name="edit" value="edit" />
                  </form>
               </c:forEach>
            </tbody>
         </table>
      </div>
      <!-- include footer -->
      <jsp:include page="/jsp/site/components/footer.jsp"></jsp:include>
   </body>
</html>