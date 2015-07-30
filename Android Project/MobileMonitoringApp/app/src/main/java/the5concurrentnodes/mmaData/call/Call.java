package the5concurrentnodes.mmaData.call;

import org.json.JSONException;
import org.json.JSONObject;

import the5concurrentnodes.mmaData.interfaces.ConvertToJSON;

public class Call implements ConvertToJSON
{
    private String type;
    private String number;
    private String duration;
    private String date;

    public Call (String type,String number,String duration,String date)
    {
       this.type = type;
        this.number = number;
        this.duration = duration;
        this.date = date;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public String getType()
    {
        return type;
    }
    public String getNumber(){
        return number;
    }
    public String getDuration(){
        return duration;
    }
    public String getDate(){
        return date;
    }

    @Override
    public JSONObject toJSONObject() {

        JSONObject jsonObject = new JSONObject();

        try{

            jsonObject.put("type", type);
            jsonObject.put("source", number);
            jsonObject.put("destination", number);
            jsonObject.put("duration", duration);
            jsonObject.put("date", date);

        }catch(JSONException e){}

        return jsonObject;
    }


}
