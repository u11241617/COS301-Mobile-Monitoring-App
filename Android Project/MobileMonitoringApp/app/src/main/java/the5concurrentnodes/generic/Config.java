package the5concurrentnodes.generic;

public class Config {

    public final static String HOST = "10.0.2.2";
    public final static String REST_API = "http://"+ HOST + "/Dashboard/resources";

    public static final String CONTENT_SMS_URI = "content://sms";
    public static final int SMS_RECEIVED = 1; //Values that represent received sms
    public static final int SMS_SENT = 2; //Values that represent a sent sms
}
