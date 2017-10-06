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
               <iframe src="https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d2349.2121905434556!2d27.598818400000003!3d53.927974000000006!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x46dbcf0e8afd5f1d%3A0x46a70858b45abfab!2z0YPQuy4g0KfQtdGA0L3Ri9GI0LXQstGB0LrQvtCz0L4gMTIsINCc0LjQvdGB0LosINCR0LXQu9Cw0YDRg9GB0Yw!5e0!3m2!1sru!2sua!4v1420492682498" width="600" height="450" style="border:0"></iframe>
            </div>
            <div class="clear"></div>
         </div>
      </div>
    
    <!-- include footer -->
	<jsp:include page="/jsp/site/components/footer.jsp"></jsp:include>
  </body>
</html>