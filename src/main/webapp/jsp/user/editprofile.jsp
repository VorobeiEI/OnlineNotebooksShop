<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!--<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>-->
<%@ taglib prefix="ctg" uri="mytag" %>
<html>

<head>
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
</head>

  <body>
    <div class="container">
      <form class="form-editprofile" name="editprofile" method="POST" action="controller">
      <input type="hidden" name="command" value="editprofile" />
        <h2 class="form-loginReg-heading">EDIT PROFILE</h2>
        <p class="text-error">${errorPasswordNotCorrect}</p>
        <p class="text-error">${wrongAction}</p>
		<p class="text-error">${nullPage}</p>
		<br />
        Name <input type="text" name="name" class="input-block-level" placeholder="${userName}">
        Email <input type="text" name="email" class="input-block-level" placeholder="${user}">
        Phone <input type="text" name="phone" class="input-block-level" placeholder="${userPhone}">
        New Password <input type="password" name="newPassword" class="input-block-level" >
        Current Password <input type="password" name="currentPassword" class="input-block-level" >
        You must supply your current password if you make changes above
        <br />
        <button class="btn btn-small btn-primary" type="submit" name="save">Save</button>
      </form>
    </div> 
  </body>
</html>