package the5concurrentnodes.mmaData.DataUsage;

import org.json.JSONException;
import org.json.JSONObject;

import the5concurrentnodes.mmaData.interfaces.ConvertToJSON;

public class DataUsage implements ConvertToJSON {

    private long total;
    private  long received;
    private long sent;

    public DataUsage(long total, long received, long sent) {
        this.total = total;
        this.received = received;
        this.sent = sent;
    }

    /*public  void setTotal(String total) {this.total = total;}
    public  void setRecieved (String received) {this.received =  received;}
    public  void setSent (String sent) { this.sent = sent;}*/

    public long getTotal(){ return this.total;}
    public long getReceived() { return this.received;}
    public long getSent () { return  this.sent;}

    @Override
    public JSONObject toJSONObject() {

        JSONObject jsonObject = new JSONObject();

        try{

            jsonObject.put("total", total);
            jsonObject.put("sent", sent);
            jsonObject.put("received", received);

        }catch(JSONException e){}

        return jsonObject;
    }
}
