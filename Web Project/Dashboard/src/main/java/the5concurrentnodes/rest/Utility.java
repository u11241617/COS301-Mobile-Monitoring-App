package the5concurrentnodes.rest;


import org.json.JSONException;
import org.json.JSONObject;

public class Utility {

    /**
     * Create JSONObject with tag and status
     * @param tag type of request
     * @param status boolean value, true if request completed successfully else false
     * @return JSONObject instance
     */
    public static JSONObject createJSON(String tag, boolean status) {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("tag", tag);
            jsonObject.put("status", Boolean.valueOf(status));

        } catch (JSONException e) {}

        return jsonObject;
    }

}
