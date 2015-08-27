package the5concurrentnodes.mmaData.Browser;


import org.json.JSONException;
import org.json.JSONObject;

import the5concurrentnodes.mmaData.interfaces.ConvertToJSON;

public class Browser implements ConvertToJSON {

    private String type;
    private String url;
    private String title;
    private String visits;
    private String date;
    private String created;

    public Browser( String type, String url , String title,String visits, String date, String created)
    {
        this.type = type;
        this.visits=visits;
        this.date=date;
        this.url=url;
        this.created= created;
        this.title=title;
    }

    public void setType(String type)
    {
        this.type = type;
    }
    public String getTitle(){
        return title;
    }
    public String getCreated()
    {
        return created;
    }
    public String getType()
    {
        return type;
    }
    public String getUrl()
    {
        return url;
    }
    public String getVisits(){
        return visits;
    }
    public String getDate(){
        return date;
    }

    @Override
    public JSONObject toJSONObject() {

        JSONObject jsonObject = new JSONObject();

        try{

            jsonObject.put("browser", type);
            jsonObject.put("url", url);
            jsonObject.put("title", title);
            jsonObject.put("visits", visits);
            jsonObject.put("created", created);
            jsonObject.put("date", date);

        }catch(JSONException e){}

        return jsonObject;
    }


}
