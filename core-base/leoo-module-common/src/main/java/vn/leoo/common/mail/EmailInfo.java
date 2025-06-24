package vn.leoo.common.mail;

import java.util.ArrayList;
import java.util.List;

public class EmailInfo {
    public static String EMAIL_SEND_SUCCESS = "SUCCESS";
    public static String EMAIL_SEND_VALID_ACC = "VALID_ACC";
    public static String EMAIL_SEND_NETWORK = "ERROR_NETWORK";
    static class EmailType {
        public static int TEXT = 1;
        public static int HTML = 2;
    }
    // Email
    private String emailSend;
    private String passSend;
    private String emailSupport;
    private String telSupport;
    private String title;
    private String personal;
    private String tmpHtml;
    private SmtpMail smtp;
    private String mailSvrCf;
    private List<EmailContent> contents = new ArrayList<EmailContent>();


    public String getEmailSend() {
        return emailSend;
    }

    public void setEmailSend(String emailSend) {
        this.emailSend = emailSend;
    }

    public String getPassSend() {
        return passSend;
    }

    public void setPassSend(String passSend) {
        this.passSend = passSend;
    }

    public String getEmailSupport() {
        return emailSupport;
    }

    public void setEmailSupport(String emailSupport) {
        this.emailSupport = emailSupport;
    }

    public String getTelSupport() {
        return telSupport;
    }

    public void setTelSupport(String telSupport) {
        this.telSupport = telSupport;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String getTmpHtml() {
        return tmpHtml;
    }

    public void setTmpHtml(String tmpHtml) {
        this.tmpHtml = tmpHtml;
    }

    public List<EmailContent> getContents() {
        return contents;
    }

    public void setContents(List<EmailContent> contents) {
        this.contents = contents;
    }

    public SmtpMail getSmtp() {
        return smtp;
    }

    public void setSmtp(SmtpMail smtp) {
        this.smtp = smtp;
    }

    public String getMailSvrCf() {
        return mailSvrCf;
    }

    public void setMailSvrCf(String mailSvrCf) {
        this.mailSvrCf = mailSvrCf;
    }
}
