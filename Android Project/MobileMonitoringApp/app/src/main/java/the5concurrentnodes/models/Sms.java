package the5concurrentnodes.models;


public class Sms {

    private String _id;
    private String type;
    private String address;
    private String content;
    private String date;

    public Sms(String id, String type, String addressFrom, String content, String date) {

        this._id = id;
        this.type = type;
        this.address = addressFrom;
        this.content = content;
        this.date = date;
    }

    public String get_id() {

        return  this._id;
    }

    public String getType() {

        return this.type;
    }
    public String getAddress() {

        return this.address;
    }

    public String getContent() {

        return this.content;
    }

    public String getDate() {

        return date;
    }

}
