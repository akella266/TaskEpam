package by.epam.training.Model.Beans;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Message {

    private long id;
    private long senderId;
    private User sender;
    private long receiverId;
    private User receiver;
    private Date time;
    private String message;
    private String type;
    private File file;

    public Message(long id, long senderId, long receiverId, Date time, String message, String type) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.time = time;
        this.message = message;
        this.type = type;
    }

    public Message() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
