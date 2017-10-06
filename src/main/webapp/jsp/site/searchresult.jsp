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
         <strong>Result of search: ${noResultMessage}</strong> 
         <table class="table table-striped">
            <tbody>
               <c:forEach items="${productList.getGoods()}" var="product">
                  <tr>
                     <td align="left">Product: ${product.name}</td>
                     <td align="left">price: ${product.price}</td>
                     <td align="left">quantity: ${product.quantity}</td>
                     <td align="left">description: ${product.description}</td>
                     <td>
                        <form name="ProductListForm" method="POST" action="controller">
                           <input type="hidden" name="command" value="goods" /> 
                           <input type="hidden" name="product" value="${product.id}" /> 
                           <input type="submit" id="" name="add" value="add to cart" />
                        </form>
                     </td>
                  </tr>
               </c:forEach>
            </tbody>
         </table>
      </div>
      <!-- include footer -->
      <jsp:include page="/jsp/site/components/footer.jsp"></jsp:include>
   </body>
</html>