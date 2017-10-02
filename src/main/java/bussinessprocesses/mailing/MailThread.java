package bussinessprocesses.mailing;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.*;

/**
 * Created by jacksparrow on 02.10.17.
 */
public class MailThread extends Thread {
    private static final Logger logger = Logger.getLogger(MailThread.class);

    private MimeMessage message;
    private String sendToEmail;
    private String mailSubject;
    private String mailText;
    private BodyPart messageBodyPart;
    private Multipart multipart;

    public MailThread(String sendToEmail, String mailSubject, String mailText) {
        this.sendToEmail = sendToEmail;
        this.mailSubject = mailSubject;
        this.mailText = mailText;
    }

    private void init() {
        Session mailSession = (new SessionCreator()).createSession();
        mailSession.setDebug(true);
        message = new MimeMessage(mailSession);
        messageBodyPart = new MimeBodyPart();
        try {
            messageBodyPart.setContent(mailText, "text/html; charset=utf-8" );
            multipart = new MimeMultipart();
            // add the message body to the mime message
            multipart.addBodyPart(messageBodyPart);
            //make our message
            message.setHeader("Content-Type", "text/plain; charset=UTF-8");
            message.setSubject(mailSubject, "utf-8");
            message.setContent(multipart);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendToEmail));
        } catch (AddressException e) {
            logger.error("Not correct email: " + sendToEmail + " " + e);
        } catch (MessagingException e) {
            logger.error("Exception formating mail " + e);
        }
    }

    public void run() {
        init();
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            logger.error("Exception sending messege" + e);
        }
    }
}
