package ksa.so.mail;

import java.util.Map;

public class Mail {

    private String from;
    private String to;
    private String[] toArray;
    private String cc;
    private String[] ccArray;
    private String subject;
    private String content;
    private Map model;

    public Map getModel() {
		return model;
	}
    public void setModel(Map model) {
    	this.model = model;
	}
	public Mail() {
    }

    public Mail(String from, String to, String subject, String content) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.content = content;
    }
    
    public Mail(String from, String[] toArray, String[] ccArray, String subject, String content) {
        this.from = from;
        this.toArray = toArray;
        this.ccArray = ccArray;
        this.subject = subject;
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
    
    public String[] getToArray() {
        return toArray;
    }

    public void setToArray(String[] toArray) {
        this.toArray = toArray;
    }


    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }
    
    public String[] getCcArray() {
        return ccArray;
    }

    public void setCcArray(String[] ccArray) {
        this.ccArray = ccArray;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

	
}