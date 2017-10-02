<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>

    <head>
   		<link href="css/bootstrap.css" rel="stylesheet">
    	<link href="css/main.css" rel="stylesheet">
	</head>

   <body>
      <div class="container">
         <form class="form-forgotpassword" name="ForgotPasswordForm" method="POST" action="controller">
            <input type="hidden" name="command" value="forgotpassword" />
            <h2 class="form-forgotpassword-heading">Enter your InStore registered email and we will email you a link to create a new password:</h2>
            Your mail:
            <input type="text" name="email" class="input-block-level" placeholder="Email">
            <br />
            ${errorMailNotRegisteredMessage}
            ${newPasswordSending}
             <br />
            <button class="btn btn-primary" type="submit" name="send">Send</button>
            <button class="btn btn-default" type="submit" name="back">Back</button>
         </form>
      </div>
   </body>

</html>