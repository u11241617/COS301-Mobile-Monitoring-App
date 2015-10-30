package the5concurrentnodes.mmaData.sms;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import the5concurrentnodes.mmaData.interfaces.ConvertToJSON;

public class Sms  implements ConvertToJSON{

    private String type;
    private String address;
    private String content;
    private String date;

    /**
     * Constructor for sms that creates an object that contains type, addressFrom, content and date.
     * @param type is the type of the sms status sent or received.
     * @param addressFrom is the number from the sender of the sms.
     * @param content is the content of the sms.
     * @param date the date of when the sms was received.
     */
    public Sms(String type, String addressFrom, String content, String date) {

        this.type = type;
        this.address = addressFrom;
        this.content = content;
        this.date = date;
    }

    /**
     * getType get the type of the sms e.g. sent or received.
     * @return returns the type.
     */
    public String getType() {

        return this.type;
    }

    /**
     * setType set the type of the sms e.g. sent received.
     * @param type is the type of the sms.
     */
    public void setType(String type) {

        this.type = type;
    }

    /**
     * getAddress gets the number of the receiver.
     * @return returns the address.
     */
    public String getAddress() {

        return this.address;
    }

    /**
     * getContent gets the content of the sms.
     * @return returns content.
     */
    public String getContent() {

        return this.content;
    }

    /**
     * getDate gets the date of the sms.
     * @return returns the date.
     */
    public String getDate() {

        return date;
    }
    /**
     * Overrides the toJSONOBJECT method so that it inserts all the variables of the sms object to
     * be sent as JSON Object
     * @return returns a sms JSON object
     */
    @Override
    public JSONObject toJSONObject() {

        JSONObject jsonObject = new JSONObject();

        try{

            jsonObject.put("type", type);
            jsonObject.put("source", address);
            jsonObject.put("destination", content);
            jsonObject.put("date", date);

        }catch(JSONException e){}

        return jsonObject;
    }
}
