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
         <h1>Contacts</h1>
         <div class="container_contacts">
            <div class="map_contacts">
               <iframe src="https://www.google.com/maps/embed/v1/place?key=AIzaSyCAGrYFUWYX_ubQQ1_p1iX0m_l9CM3PUCs&q=ZhK+Yunost,Kyivs'ka+oblast+Ukraine" width="600" height="450" style="border:0"></iframe>
            </div>
            <div class="clear"></div>
         </div>
      </div>
    
    <!-- include footer -->
	<jsp:include page="/jsp/site/components/footer.jsp"></jsp:include>
  </body>
</html>