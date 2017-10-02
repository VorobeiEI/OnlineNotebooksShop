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

	<!-- page loader -->
	<form name="mainListForm" method="POST" action="controller">
		<input type="hidden" name="command" value="main" />
	</form>

	<!-- include header -->
	<jsp:include page="/jsp/site/components/header.jsp"></jsp:include>

	<div class="container">
		<div class="row">
			<c:forEach items="${listOfLastNews}" var="news">
				<div class="col-sm-4">
					<h2>${news.title}</h2>
					<p>${news.smallBody}</p>
					<div>
						<form name="cartListForm" method="POST" action="controller">
							<input type="hidden" name="command" value="main" /> 
							<input type="hidden" name="newsId" value="${news.idNews}" /> 
							<input type="submit" name="showNewsDetails" value="View details" class="btn" />
						</form>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	
	<!-- include footer -->
	<jsp:include page="/jsp/site/components/footer.jsp"></jsp:include>
</body>

</html>
