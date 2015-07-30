package the5concurrentnodes.mmaData.call;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;

public class CallObserver extends ContentObserver {
    private Context context;
    private CallHandler callHandler;

    public CallObserver( Context context)
    {
        super(null);
        this.context = context;
        this.callHandler = new CallHandler();

    }

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
