package the5concurrentnodes.mmaData.DataUsage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import the5concurrentnodes.mmaData.interfaces.ConvertToJSON;

public class DataUsage implements ConvertToJSON {
    List name;
    List total;
    List overallTotal;

    public DataUsage( List name, List total, List overallTotal) {
        this.total = total;
        this.overallTotal = overallTotal;
        this.name = name;
    }

    /*public  void setTotal(String total) {this.total = total;}
    public  void setRecieved (String received) {this.received =  received;}
    public  void setSent (String sent) { this.sent = sent;}

    public long getTotal(){ return this.total;}
    public long getReceived() { return this.received;}
    public long getSent () { return  this.sent;}
    public String getName() { return this.name;}
*/
    @Override
    public JSONObject toJSONObject() {

        JSONObject jsonObject = new JSONObject();

        try{
            jsonObject.put("name", name);
            jsonObject.put("total", total);
            jsonObject.put("overallTotal", overallTotal);
        }catch(JSONException e){}

        return jsonObject;
    }
}
