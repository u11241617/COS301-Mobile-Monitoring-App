package the5concurrentnodes.mmaData.call;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

public class CallObserver extends ContentObserver {
    private Context context;
    private CallHandler callHandler;

    /**
     * CallObserver creates a CallObserver object that creates a object with context and a callHandler
     * object.
     * @param context is androids Context instance.
     */

    public CallObserver( Context context)
    {
        super(null);
        this.context = context;
        this.callHandler = new CallHandler();

    }


    /**
     * onChange is notified as soon as Calls status changes so when a new call, dialed or received is made.
     * @param selfChange boolean variable state whether things have changed.
     */
    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);

        Cursor cursor = context.getContentResolver().query(Uri.parse(CallConstants.CONTENT_CALL_URI)
                , null, null, null, "_id DESC");

        if(cursor != null) {

            cursor.moveToFirst();
            Call call = callHandler.getCallInformation(cursor);

            if(Integer.parseInt(call.getType()) == CallConstants.CALL_DIALED ||
                    Integer.parseInt(call.getType()) == CallConstants.CALL_RECEIVED ||Integer.parseInt(call.getType()) == CallConstants.CALL_MISSED) {

                if(Integer.parseInt(call.getType()) == CallConstants.CALL_DIALED) {

                    call.setType("Dialled");

                }else if(Integer.parseInt(call.getType()) == CallConstants.CALL_RECEIVED) {

                    call.setType("Received");
                }else{
                    call.setType("Missed");
                }

                callHandler.submitLog(context, call.toJSONObject());
            }

            if(!cursor.isClosed())
                cursor.close();
        }
    }
    @Override
    public boolean deliverSelfNotifications(){ return  false;}
}
