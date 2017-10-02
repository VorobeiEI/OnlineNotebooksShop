<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="ctg" uri="mytag"%>
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
		<p class="text-info">${cartIsEmptyMessage}</p>
		<p class="text-error">${ErrorAccountBlockedMessage}</p>
		<p class="text-success">${OrderInProcessed}</p>
		<p class="text-error">${ErrorNotInStockMessage}</p>
	<strong>${ctg:notnullOrderAmount(orderAmount)}</strong>
	<table class="table table-striped">
		<tbody>
			<c:forEach items="${cart}" var="product">
				<tr>
					<td align="left"><c:out value="Product: ${product.name}" /></td>
					<td align="left"><c:out value="id: ${product.id}" /></td>
					<td align="left"><c:out value="price: ${product.price}" /></td>
					<td align="left"><c:out value="description: ${product.description}" /></td>
					<td>
					<form name="cartListForm" method="POST" action="controller">
						<input type="hidden" name="command" value="cart" /> 
						<input type="hidden" name="product" value="${product.id}" /> 
						<input type="submit" id="" name="remove" value="remove" />
					</form>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<strong>${ctg:notnullOrderAmount(orderAmount)}</strong>

	<c:if test = "${cart != null}">
		<form name="PayForm" method="POST" action="controller">
			<input type="hidden" name="command" value="cart" />
			<input type="submit" name="pay" value="Pay" />
		</form>
	</c:if>
	
	</div>
	
	<!-- include footer -->
	<jsp:include page="/jsp/site/components/footer.jsp"></jsp:include>
</body>
</html>