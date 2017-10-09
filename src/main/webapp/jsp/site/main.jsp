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
	<form name="mainListForm" method="GET" action="controller">
		<input type="hidden" name="command" value="main" />
	</form>

	<!-- include header -->
	<jsp:include page="/jsp/site/components/header.jsp"></jsp:include>

	<!-- include footer -->
	<jsp:include page="/jsp/site/components/footer.jsp"></jsp:include>
</body>

</html>
