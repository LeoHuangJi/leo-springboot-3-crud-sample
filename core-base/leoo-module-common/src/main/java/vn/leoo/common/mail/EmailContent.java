package vn.leoo.common.mail;

import java.util.ArrayList;
import java.util.List;

import vn.leoo.common.util.file.FileInfo;

public class EmailContent {
    private String id;
    private String title;
    private String ccEmail;
    private String recipientEmail;
    private String message;
    private String personal;
    private int typeSend = 1;
    private List<FileInfo> fileAttach = new ArrayList<FileInfo>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCcEmail() {
        return ccEmail;
    }

    public void setCcEmail(String ccEmail) {
        this.ccEmail = ccEmail;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public int getTypeSend() {
        return typeSend;
    }

    public void setTypeSend(int typeSend) {
        this.typeSend = typeSend;
    }

    public List<FileInfo> getFileAttach() {
        return fileAttach;
    }

    public void setFileAttach(List<FileInfo> fileAttach) {
        this.fileAttach = fileAttach;
    }

    public void addFileAttach(FileInfo fileAttach) {
        if (this.fileAttach == null) {
            this.fileAttach = new ArrayList<FileInfo>();
        }
        this.fileAttach.add(fileAttach);
    }
}
