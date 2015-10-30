package the5concurrentnodes.mmaData.Browser;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;

public class BrowserDefaultObserver extends ContentObserver {
    private Context context;
    private BrowserHandler browserHandler;

    public BrowserDefaultObserver(Context context)
    {
        super(null);
        this.context = context;
        this.browserHandler= new BrowserHandler();
    }
    @Override
    public void onChange(boolean selfChange) {
        onChange(selfChange, null);

    }
    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange);

        Cursor cursor = context.getContentResolver().query(Uri.parse(BrowserConstants.CONTENT_DEFAULT_URI)
                , null, null, null, "_id DESC");

        if(cursor != null) {

            cursor.moveToFirst();
            Browser browser = browserHandler.getBrowserInformation(cursor);

            browser.setType("Default");

            browserHandler.submitLog(context, browser.toJSONObject());
            if(!cursor.isClosed())
                cursor.close();
        }
    }

    @Override
    public boolean deliverSelfNotifications(){ return  false;}
}

