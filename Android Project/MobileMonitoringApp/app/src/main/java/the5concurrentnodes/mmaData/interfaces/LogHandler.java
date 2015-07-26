package the5concurrentnodes.mmaData.interfaces;

import android.content.Context;

import org.json.JSONObject;

public interface LogHandler {

    public void submitLog(Context context, JSONObject params);
}
