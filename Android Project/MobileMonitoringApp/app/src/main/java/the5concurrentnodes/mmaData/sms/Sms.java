package the5concurrentnodes.mmaData.sms;


import org.json.JSONException;
import org.json.JSONObject;

import the5concurrentnodes.generic.Config;
import the5concurrentnodes.mobilemonitoringapp.MainActivity;
import the5concurrentnodes.models.DeviceInfo;

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

    public JSONObject toJSONObject() {

        JSONObject jsonObject = new JSONObject();

        try {

            DeviceInfo deviceInfo = null;

            try{

                deviceInfo = new DeviceInfo(MainActivity.class.newInstance().getApplicationContext());

            }catch(Exception e){}

            if(type.equals(Config.SMS_SENT)){

                jsonObject.put("source", deviceInfo.getLineNumber());
                jsonObject.put("destination", address);
                jsonObject.put("type", "Sent");

            }else {

                jsonObject.put("source", address);
                jsonObject.put("destination", deviceInfo.getLineNumber());
                jsonObject.put("type", "Received");
            }

            jsonObject.put("deviceIme",deviceInfo.getIMEI());

        }catch(JSONException e){}

        return jsonObject;
    }

}
