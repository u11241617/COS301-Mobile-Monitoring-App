package the5concurrentnodes.mmaData.sms;


import org.json.JSONException;
import org.json.JSONObject;

<<<<<<< HEAD
import the5concurrentnodes.generic.Config;
import the5concurrentnodes.mobilemonitoringapp.MainActivity;
import the5concurrentnodes.models.DeviceInfo;

public class Sms {

    private String _id;
=======
import the5concurrentnodes.mmaData.interfaces.ConvertToJSON;

public class Sms  implements ConvertToJSON{

>>>>>>> af3afddafefa3572aee816e4377b5215ee18dd15
    private String type;
    private String address;
    private String content;
    private String date;

<<<<<<< HEAD
    public Sms(String id, String type, String addressFrom, String content, String date) {

        this._id = id;
=======
    public Sms(String type, String addressFrom, String content, String date) {

>>>>>>> af3afddafefa3572aee816e4377b5215ee18dd15
        this.type = type;
        this.address = addressFrom;
        this.content = content;
        this.date = date;
    }

<<<<<<< HEAD
    public String get_id() {

        return  this._id;
    }

    public String getType() {

        return this.type;
=======
    public String getType() {

        return this.type;
    }

    public void setType(String type) {

        this.type = type;
>>>>>>> af3afddafefa3572aee816e4377b5215ee18dd15
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

<<<<<<< HEAD
=======
    @Override
>>>>>>> af3afddafefa3572aee816e4377b5215ee18dd15
    public JSONObject toJSONObject() {

        JSONObject jsonObject = new JSONObject();

<<<<<<< HEAD
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
=======
        try{

            jsonObject.put("type", type);
            jsonObject.put("source", address);
            jsonObject.put("destination", "");
            jsonObject.put("date", date);
>>>>>>> af3afddafefa3572aee816e4377b5215ee18dd15

        }catch(JSONException e){}

        return jsonObject;
    }
<<<<<<< HEAD

=======
>>>>>>> af3afddafefa3572aee816e4377b5215ee18dd15
}
