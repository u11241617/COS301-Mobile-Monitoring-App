package the5concurrentnodes.mmaData.sms;


import org.json.JSONException;
import org.json.JSONObject;

import the5concurrentnodes.mmaData.interfaces.ConvertToJSON;

public class Sms  implements ConvertToJSON{

    private String type;
    private String address;
    private String content;
    private String date;

    public Sms(String type, String addressFrom, String content, String date) {

        this.type = type;
        this.address = addressFrom;
        this.content = content;
        this.date = date;
    }

    public String getType() {

        return this.type;
    }

    public void setType(String type) {

        this.type = type;
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

    @Override
    public JSONObject toJSONObject() {

        JSONObject jsonObject = new JSONObject();

        try{

            jsonObject.put("type", type);
            jsonObject.put("source", address);
            jsonObject.put("destination", "");
            jsonObject.put("date", date);

        }catch(JSONException e){}

        return jsonObject;
    }
}
