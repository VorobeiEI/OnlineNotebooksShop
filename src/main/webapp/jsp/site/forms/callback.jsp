<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<html>

   <head>
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
	</head>

   <body>
      <div class="container">
         <form class="form-callback" name="loginForm" method="GET" action="controller">
            <input type="hidden" name="command" value="callback" />
            <h2 class="form-loginReg-heading">Feedback Form</h2>
            Your message:
            <input type="text" name="message" class="input-block-level" placeholder="write your message">
            Please introduce yourself:
            <input type="text" name="name" class="input-block-level" placeholder="write your name">
            Your phone or e-mail for our reply:
            <input type="text" name="contacts" class="input-block-level" placeholder="write your contacts information">
            <br />
            <p class="text-error">${errorFieldNullMessage}</p>
            <button class="btn btn-primary" type="submit" name="send">Send</button>
         </form>
      </div>
   </body>

</html>