<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
    <head>
   		<link href="css/bootstrap.css" rel="stylesheet">
    	<link href="css/main.css" rel="stylesheet">
	</head>
	
  <body>
    <div class="container">
      <form class="form-loginReg" name="loginForm" method="POST" action="controller">
      <input type="hidden" name="command" value="registration" />
        <h2 class="form-loginReg-heading">ABOUT YOU</h2>
		<input type="text" name="email" class="input-block-level" placeholder="Email address">
		<input type="password" name="password" class="input-block-level" placeholder="Password">
		<input type="password" name="passwordC" class="input-block-level" placeholder="Comfirm Password">
		<input type="text" name="firstName" class="input-block-level" placeholder="First name">
		<input type="text" name="phone" class="input-block-level" placeholder="Mobile phone">
		<p class="text-error">${errorFieldNullMessage}</p>
		<p class="text-error">${errorUniqueMailMessage}</p>
		<p class="text-error">${errorNotCorrectEmailMessage}</p>
		<p class="text-error">${errorConfirmPasswordMessage} </p>
		<p class="text-error">${errorNotCorrectPhoneMessage}</p>
		<br />
        <button class="btn btn-small btn-primary" type="submit">Registration</button>
       	<button class="btn btn-small btn-primary" type="reset">Clear</button>
      </form>
    </div> 
  </body>
</html>