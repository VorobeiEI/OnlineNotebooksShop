<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="ctg" uri="mytag" %>
<html>
    <head>
   		<link href="css/bootstrap.css" rel="stylesheet">
    	<link href="css/main.css" rel="stylesheet">
	</head>
	
   <body>
      <div class="container">
         <form class="form-loginReg" name="loginForm" method="GET" action="controller">
            <input type="hidden" name="command" value="login" />
            <h2 class="form-loginReg-heading">Please sign in</h2>
            <input type="text" name="email" class="input-block-level" placeholder="Email address">
            <input type="password" name="password" class="input-block-level" placeholder="Password">
            <br />
            <p class="text-error">${errorLoginPassMessage}</p>
            <p class="text-error">${errorMailNotRegisteredMessage}</p>
            <p class="text-error">${wrongAction}</p>
            <br />
            <p class="text-error">${nullPage}</p>
            <br />
            <button class="btn btn-small btn-primary" type="submit">Sign in</button>
            <button class="btn btn-small btn-primary" type="submit" name="forgotPassword">Forgot password</button>
            <button class="btn btn-small btn-primary" type="submit" name="registration">Registration</button>
         </form>
      </div>
   </body>
</html>