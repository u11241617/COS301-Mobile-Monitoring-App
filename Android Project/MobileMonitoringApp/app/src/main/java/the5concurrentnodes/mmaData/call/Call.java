package the5concurrentnodes.mmaData.call;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import the5concurrentnodes.mmaData.interfaces.ConvertToJSON;

public class Call implements ConvertToJSON
{
    private String type;
    private String number;
    private String duration;
    private String date;

    /**
     * Constructor for Call class that creates a call object with type,number,duration and date.
     * @param type is dialed, received and missed status.
     * @param number is the number from the missed, received or dialed call.
     * @param duration is the duration of the call.
     * @param date is the date the event occured.
     */
    public Call (String type,String number,String duration,String date)
    {
       this.type = type;
        this.number = number;
        this.duration = duration;
        this.date = date;
    }

    /**
     * setType sets the type of the call.
     * @param type is the type status e.g. missed, dialed or received.
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * getType gets the type of the call status.
     * @return returns the type of the call.
     */
    public String getType()
    {
        return type;
    }

    /**
     * getNumber gets the number that was dialed, missed or received.
     * @return return the number.
     */
    public String getNumber(){
        return number;
    }

    /**
     * getDuration get the duration of the call.
     * @return returns the duration.
     */
    public String getDuration(){
        return duration;
    }
    public String getDate(){
        return date;
    }

    /**
     * Overrides the toJSONOBJECT method so that it inserts all the variables of the call object to
     * be sent as JSON Object
     * @return return a call JSON object
     */
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
