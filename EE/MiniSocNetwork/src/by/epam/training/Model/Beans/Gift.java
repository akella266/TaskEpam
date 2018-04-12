package by.epam.training.Model.Beans;

public class Gift {
    private long id;
    private long senderId;
    private long receiverId;
    private long giftId;

    public Gift() {
    }

    public Gift(long id, long senderId, long receiverId, long giftId) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.giftId = giftId;
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

    public long getGiftId() {
        return giftId;
    }

    public void setGiftId(long giftId) {
        this.giftId = giftId;
    }
}
