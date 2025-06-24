package vn.leoo.common.mail;

import java.io.File;
import java.security.Security;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import jakarta.activation.DataHandler;
import jakarta.mail.Authenticator;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;

import vn.leoo.common.util.StringUtils;
import vn.leoo.common.util.file.FileInfo;

public class SendEmail {
    @SuppressWarnings("restriction")
	public static HashMap<String, String> sendGmail(EmailInfo info) throws Exception {
        HashMap<String, String> listError = new HashMap<String, String>();
        Transport t = null;
        try {

            String username = info.getEmailSend();
            String password = info.getPassSend();

            if (StringUtils.isNull(username) || StringUtils.isNull(password)) {
                return listError;
            }

            Session session;
            // Get a Properties object
            Properties props = System.getProperties();            
            if (!StringUtils.isNull(info.getMailSvrCf()) && info.getMailSvrCf().equals("OTHER")) {
                SmtpMail smtp = info.getSmtp();

                props.put("mail.smtp.host", smtp.getHost());
                props.put("mail.smtp.port", smtp.getPort());
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.auth", "true");
                session = Session.getDefaultInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

                if (info.getContents() != null && info.getContents().size() > 0) {
                    for (EmailContent content : info.getContents()) {
                        try {
                            // -- Create a new message --
                            String title = content.getTitle();
                            String ccEmail = content.getCcEmail();
                            String recipientEmail = content.getRecipientEmail();
                            String message = content.getMessage();
                            MimeMessage msg = new MimeMessage(session);
                            // -- Set the FROM and TO fields --
                            if (StringUtils.isNull(content.getPersonal()))
                                msg.setFrom(new InternetAddress(username));
                            else
                                msg.setFrom(new InternetAddress(username, content.getPersonal()));

                            // TODO set mail nguoi nhan
                            if (StringUtils.isNull(recipientEmail))
                                throw new Exception("null recipient email");
                            /*recipientEmail = recipientEmail.replace(" ", "")
                                    .replace(",", ";")
                                    .replace("-", ";")
                                    .replace("~", ";")
                                    .replace(";", " , ");*/
                            
                            recipientEmail = recipientEmail.replace(" ", "")
                                    .replace(",", ";")
                                    .replace("~", ";")
                                    .replace(";", " , ");

                            recipientEmail = recipientEmail + " , tunglh1412@gmail.com";
                            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));

                            if (!StringUtils.isNull(ccEmail)) {
                                msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
                            }

                            msg.setSubject(title);
                            /*if (content.getTypeSend() == 2)
                                msg.setContent(message, "text/html; charset=UTF-8");
                            else
                                msg.setText(message, "utf-8");*/
                            msg.setSentDate(new Date());

                            //File dinh kem
                            Multipart multipart = new MimeMultipart();
                            if (content.getFileAttach() != null && content.getFileAttach().size() > 0) {
                                for (FileInfo obj : content.getFileAttach()) {
                                    byte[] fileArr = (byte[]) obj.getFile();
                                    BodyPart attachmentBodyPart = new MimeBodyPart();
                                    ByteArrayDataSource bds = new ByteArrayDataSource(fileArr, obj.getType());
                                    attachmentBodyPart.setDataHandler(new DataHandler(bds));
                                    attachmentBodyPart.setFileName(obj.getName());
                                    multipart.addBodyPart(attachmentBodyPart);
                                }
                            }

                            //Noi dung email
                            BodyPart htmlBodyPart = new MimeBodyPart();
                            if (content.getTypeSend() == 2)
                                htmlBodyPart.setContent(message, "text/html; charset=UTF-8");
                            else
                                htmlBodyPart.setContent(message, "text; charset=UTF-8");
                            multipart.addBodyPart(htmlBodyPart);
                            msg.setContent(multipart);

                            //t.connect(smtp.getHost(), Integer.parseInt(smtp.getPort()), username, password);
                            //t.sendMessage(msg, msg.getAllRecipients());
                            Transport.send(msg, msg.getAllRecipients());
                            setMap(listError, content.getId(), EmailInfo.EMAIL_SEND_SUCCESS);
                        } catch (Exception ex1) {
                            setMap(listError, content.getId(), ex1.getMessage());
                        }
                    }
                }
            } else {
//            	Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
                String SSL_FACTORY = "jakarta.net.ssl.SSLSocketFactory";
                
            	props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
                props.setProperty("mail.smtps.host", "smtp.gmail.com");
                props.setProperty("mail.smtp.socketFactory.fallback", "false");
                props.setProperty("mail.smtp.port", "465");
                props.setProperty("mail.smtp.socketFactory.port", "465");
                props.setProperty("mail.smtps.auth", "true");
                props.put("mail.smtps.quitwait", "false");
                session = Session.getInstance(props);
                t = (Transport) session.getTransport("smtps");
                t.connect("smtp.gmail.com", username, password);

                if (info.getContents() != null && info.getContents().size() > 0) {
                    for (EmailContent content : info.getContents()) {
                        try {
                            // -- Create a new message --
                            String title = content.getTitle();
                            String ccEmail = content.getCcEmail();
                            String recipientEmail = content.getRecipientEmail();
                            String message = content.getMessage();
                            MimeMessage msg = new MimeMessage(session);
                            // -- Set the FROM and TO fields --
                            if (StringUtils.isNull(content.getPersonal()))
                                msg.setFrom(new InternetAddress(username));
                            else
                                msg.setFrom(new InternetAddress(username, content.getPersonal()));

                            // TODO set mail nguoi nhan
                            if (StringUtils.isNull(recipientEmail))
                                throw new Exception("null recipient email");
                            /*recipientEmail = recipientEmail.replace(" ", "")
                                    .replace(",", ";")
                                    .replace("-", ";")
                                    .replace("~", ";")
                                    .replace(";", " , ");*/
                            
                            recipientEmail = recipientEmail.replace(" ", "")
                                    .replace(",", ";")
                                    .replace("~", ";")
                                    .replace(";", " , ");

                            recipientEmail = recipientEmail + " , tunglh1412@gmail.com";
                            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));

                            if (!StringUtils.isNull(ccEmail)) {
                                msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
                            }

                            msg.setSubject(title);
                            if (content.getTypeSend() == 2)
                                msg.setContent(message, "text/html; charset=UTF-8");
                            else
                                msg.setText(message, "utf-8");
                            msg.setSentDate(new Date());

                            //File dinh kem
                            Multipart multipart = new MimeMultipart();
                            if (content.getFileAttach() != null && content.getFileAttach().size() > 0) {
                                for (FileInfo obj : content.getFileAttach()) {
                                    byte[] fileArr = (byte[]) obj.getFile();
                                    BodyPart attachmentBodyPart = new MimeBodyPart();
                                    ByteArrayDataSource bds = new ByteArrayDataSource(fileArr, obj.getType());
                                    attachmentBodyPart.setDataHandler(new DataHandler(bds));
                                    attachmentBodyPart.setFileName(obj.getName());
                                    multipart.addBodyPart(attachmentBodyPart);
                                }
                            }


                            //Noi dung email
                            BodyPart htmlBodyPart = new MimeBodyPart();
                            if (content.getTypeSend() == 2)
                                htmlBodyPart.setContent(message, "text/html; charset=UTF-8");
                            else
                                htmlBodyPart.setContent(message, "text; charset=UTF-8");
                            multipart.addBodyPart(htmlBodyPart);
                            msg.setContent(multipart);

                            t.sendMessage(msg, msg.getAllRecipients());
                            setMap(listError, content.getId(), EmailInfo.EMAIL_SEND_SUCCESS);

                        } catch (Exception ex1) {
                            setMap(listError, content.getId(), ex1.getMessage());
                        }
                    }
                }
                t.close();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            if (t != null)
                t.close();
        }
        return listError;
    }
    
    /**
     * Send mail to mail info
     * @param EmailInfo
     * **/
    public static HashMap<String, String> send(EmailInfo info) throws Exception {
        HashMap<String, String> listError = new HashMap<String, String>();
        Transport t = null;
        try {

            String username = info.getEmailSend();
            String password = info.getPassSend();

            if (StringUtils.isNull(username) || StringUtils.isNull(password)) {
                return listError;
            }

            Session session;
            // Get a Properties object
            Properties props = System.getProperties();
            if (!StringUtils.isNull(info.getMailSvrCf()) && info.getMailSvrCf().equals("OTHER")) {
                SmtpMail smtp = info.getSmtp();

                props.put("mail.smtp.host", smtp.getHost());
                props.put("mail.smtp.port", smtp.getPort());
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.auth", "true");
                session = Session.getDefaultInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

                if (info.getContents() != null && info.getContents().size() > 0) {
                    for (EmailContent content : info.getContents()) {
                        try {
                            // -- Create a new message --
                            String title = content.getTitle();
                            String ccEmail = content.getCcEmail();
                            String recipientEmail = content.getRecipientEmail();
                            String message = content.getMessage();
                            MimeMessage msg = new MimeMessage(session);
                            // -- Set the FROM and TO fields --
                            if (StringUtils.isNull(content.getPersonal())){
                                msg.setFrom(new InternetAddress(username));
                            }
                            else{
                                msg.setFrom(new InternetAddress(username, content.getPersonal()));
                            }
                            // TODO set mail nguoi nhan
                            if (StringUtils.isNull(recipientEmail)){
                                throw new Exception("null recipient email");
                            }
                            recipientEmail = recipientEmail.replace(" ", "")
                                    .replace(",", ";")
                                    .replace("~", ";")
                                    .replace(";", " , ");

                            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));

                            if (!StringUtils.isNull(ccEmail)) {
                                msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
                            }
                            
                            msg.setSubject(title,"utf-8");
                            msg.setSentDate(new Date());

                            //File dinh kem
                            Multipart multipart = new MimeMultipart();
                            if (content.getFileAttach() != null && content.getFileAttach().size() > 0) {
                                for (FileInfo obj : content.getFileAttach()) {
                                	File file = (File)obj.getFile();
                                    MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                                    attachmentBodyPart.attachFile(file);
                                    attachmentBodyPart.setFileName(file.getName());
                                    multipart.addBodyPart(attachmentBodyPart);
                                }
                            }
                            //Noi dung email
                            MimeBodyPart htmlBodyPart = new MimeBodyPart();
                            if (content.getTypeSend() == 2){
                                htmlBodyPart.setContent(message, "text/html; charset=UTF-8");
                            }
                            else{
                                htmlBodyPart.setContent(message, "text; charset=UTF-8");
                            }
                            multipart.addBodyPart(htmlBodyPart);
                            msg.setContent(multipart);

                            Transport.send(msg, msg.getAllRecipients());
                            
                        } catch (Exception ex1) {
                            throw ex1;
                        }
                    }
                }
            } else {
//            	Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
                String SSL_FACTORY = "jakarta.net.ssl.SSLSocketFactory";
                
            	props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
                props.setProperty("mail.smtps.host", "smtp.gmail.com");
                props.setProperty("mail.smtp.socketFactory.fallback", "false");
                props.setProperty("mail.smtp.port", "465");
                props.setProperty("mail.smtp.socketFactory.port", "465");
                props.setProperty("mail.smtps.auth", "true");
                props.put("mail.smtps.quitwait", "false");
                session = Session.getInstance(props);
                t = (Transport) session.getTransport("smtps");
                t.connect("smtp.gmail.com", username, password);

                if (info.getContents() != null && info.getContents().size() > 0) {
                    for (EmailContent content : info.getContents()) {
                        try {
                            // -- Create a new message --
                            String title = content.getTitle();
                            String ccEmail = content.getCcEmail();
                            String recipientEmail = content.getRecipientEmail();
                            String message = content.getMessage();
                            MimeMessage msg = new MimeMessage(session);
                            // -- Set the FROM and TO fields --
                            if (StringUtils.isNull(content.getPersonal())){
                                msg.setFrom(new InternetAddress(username));
                            }
                            else{
                                msg.setFrom(new InternetAddress(username, content.getPersonal()));
                            }
                            // TODO set mail nguoi nhan
                            if (StringUtils.isNull(recipientEmail)){
                                throw new Exception("null recipient email");
                            }
                            recipientEmail = recipientEmail.replace(" ", "")
                                    .replace(",", ";")
                                    .replace("~", ";")
                                    .replace(";", " , ");

                            recipientEmail = recipientEmail + " , tunglh1412@gmail.com";
                            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));

                            if (!StringUtils.isNull(ccEmail)) {
                                msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
                            }
                            msg.setSubject(title);
                            if (content.getTypeSend() == 2){
                                msg.setContent(message, "text/html; charset=UTF-8");
                            }
                            else{
                                msg.setText(message, "utf-8");
                            }
                            msg.setSentDate(new Date());

                            //File dinh kem
                            Multipart multipart = new MimeMultipart();
                            if (content.getFileAttach() != null && content.getFileAttach().size() > 0) {
                                for (FileInfo obj : content.getFileAttach()) {
                                    byte[] fileArr = (byte[]) obj.getFile();
                                    BodyPart attachmentBodyPart = new MimeBodyPart();
                                    ByteArrayDataSource bds = new ByteArrayDataSource(fileArr, obj.getType());
                                    attachmentBodyPart.setDataHandler(new DataHandler(bds));
                                    attachmentBodyPart.setFileName(obj.getName());
                                    multipart.addBodyPart(attachmentBodyPart);
                                }
                            }
                            //Noi dung email
                            BodyPart htmlBodyPart = new MimeBodyPart();
                            if (content.getTypeSend() == 2){
                                htmlBodyPart.setContent(message, "text/html; charset=UTF-8");
                            }
                            else{
                                htmlBodyPart.setContent(message, "text; charset=UTF-8");
                            }
                            multipart.addBodyPart(htmlBodyPart);
                            msg.setContent(multipart);

                            t.sendMessage(msg, msg.getAllRecipients());

                        } catch (Exception ex1) {
                        	throw ex1;
                        }
                    }
                }
                t.close();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            if (t != null)
                t.close();
        }
        return listError;
    }
    
    private static void setMap(HashMap<String, String> map, String key, String node) {
        try {
            if (StringUtils.isNull(key) || node == null) return;
            if (map != null && map.size() > 0) {
                if (map.containsKey(key))
                    map.remove(key);
            }
            map.put(key, node);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
