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
         <br />
         <div class="goods-forms text-center">
            <form name="GoodsForm" method="GET" action="controller">
               <input type="hidden" name="command" value="goods" />
               <input type="hidden" name="show" value="" />
               		<button type="submit" class="btn btn-default btn-xs" name="producer_id" value="1">Acer</button>
               		<button type="submit" class="btn btn-default btn-xs" name="producer_id" value="2">Asus</button>
               		<button type="submit" class="btn btn-default btn-xs" name="producer_id" value="3">HP</button>
            </form>
         </div>
         
          <br />
               <p class="text-info">${cartIsEmptyMessage}</p>
               <p class="text-error">${ErrorNotInStockMessage}</p>
               <p class="text-info">${ErrorUserNotLogin}</p>
         <c:if test = "${productCategList != null}">
            <form name="GoodsForm" method="POST" action="controller">
            	Sort by:
           		<input type="hidden" name="command" value="goods" />
                <input type="submit" id="" name="sortByName" value="name" />
                <input type="submit" id="" name="sortByPrice" value="price" />

                ${amountofproducts} products found |
                <c:choose>
				    <c:when test="${productCategList.getSize() == 20 }">
				        <a href="controller?command=goods&view=40">View 40 per page</a>
				    </c:when>
				    <c:when test="${productCategList.getSize() > 20 }">
				        <a href="controller?command=goods&view=20">View 20 per page</a>
				    </c:when>
				    <c:otherwise>
				    </c:otherwise>
				</c:choose>
            </form>
         </c:if>
          Go to:
          <input type="submit" id="" name="nextPage" value="next" />
          <input type="submit" id="" name="prevPage" value="previous" />
         <table class="table table-striped">
            <tbody>
               <c:forEach items="${productCategList.getGoods()}" var="product">
                  <tr>
                     <td align="left">Product: ${product.name}</td>
                     <td align="left">CPU: ${product.cpu}</td>
                     <td align="left">RAM: ${product.ram}</td>
                     <td align="left">Memory: ${product.memory}</td>
                     <td align="left">description: ${product.description}</td>
                      <td align="left">quantity: ${product.quantity}</td>
                      <td align="left">price: ${product.price}</td>
                     <td>
                        <form name="ProductListForm" method="POST" action="controller">
                           <input type="hidden" name="command" value="goods" /> 
                           <input type="hidden" name="product" value="${product.id}" /> 
                           <input type="submit" id=" " name="add" value="add to cart" />
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