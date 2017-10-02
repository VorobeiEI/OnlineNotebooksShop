<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<html>
    <head>
   		<link href="css/bootstrap.css" rel="stylesheet">
    	<link href="css/main.css" rel="stylesheet">
	</head>
	
   <body>
      <div class="container">
         <form class="form-callback" name="loginForm" method="POST" action="controller">
            <input type="hidden" name="command" value="mail" />
            <h2 class="form-loginReg-heading">Feedback Form</h2>
            Subject:
            <input type="text" name="subject" class="input-block-level" placeholder="write your message">
            Your message:
            <input type="text" name="message" class="input-block-level" placeholder="write your name">
            <br />
            <p class="text-error">${errorFieldNullMessage}</p>
            <button class="btn btn-primary" type="submit" name="send">Send</button>
         </form>
      </div>
   </body>
</html>