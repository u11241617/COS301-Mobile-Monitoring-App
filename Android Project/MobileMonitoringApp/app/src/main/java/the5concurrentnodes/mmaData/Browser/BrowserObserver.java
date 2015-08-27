package the5concurrentnodes.mmaData.Browser;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class BrowserObserver extends ContentObserver {
    private Context context;
    private BrowserHandler browserHandler;
    static final String[] projection = new String[] { android.provider.Browser.BookmarkColumns.URL, android.provider.Browser.BookmarkColumns.TITLE, android.provider.Browser.BookmarkColumns.VISITS, android.provider.Browser.BookmarkColumns.DATE };
    static final String selection = android.provider.Browser.BookmarkColumns.BOOKMARK + " = 0";
    static final String sortOrder = android.provider.Browser.BookmarkColumns.DATE;

    public BrowserObserver (Context context)
    {
        super(null);
        this.context = context;
        this.browserHandler= new BrowserHandler();
    }
    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);

       /* Cursor chromeCursor = context.getContentResolver().query(Uri.parse(BrowserConstants.CHROME_CONTENT_URI)
                , null, null, null, "_id DESC");
        Cursor samsungCursor = context.getContentResolver().query(Uri.parse(BrowserConstants.SAMSUNG_CONTENT_URI)
                , null, null, null, "_id DESC");
        Cursor Operacursor = context.getContentResolver().query(Uri.parse(BrowserConstants.OPERA_CONTENT_URI)
                , null, null, null, "_id DESC");
        Cursor cursor = context.getContentResolver().query(Uri.parse(BrowserConstants.CONTENT_DEFAULT_URI )
                , null, null, null, "_id DESC");

        handleBrowserInformation(chromeCursor, "Chrome");
         handleBrowserInformation(samsungCursor, "Samsung");
        handleBrowserInformation(Operacursor,"Opera");
        handleBrowserInformation(cursor, "Default");*/

    }
    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange);

        //Retrieve all the visited URLs:
        final Cursor cursor =context.getContentResolver().query(android.provider.Browser.BOOKMARKS_URI, projection, selection, null, sortOrder);

        cursor.moveToFirst();
        //cursor.moveToLast();
       while (!cursor.isAfterLast()) {
            final String url = cursor.getString(cursor.getColumnIndex(projection[0]));
            final String title = cursor.getString(cursor.getColumnIndex(projection[1]));
            //visits = ... (cursor.getColumnIndex(projection[2]))
            //date = ... (cursor.getColumnIndex(projection[3]))
            Log.d("TAG********", title + " : " + url + "\n");
           // Browser browser = browserHandler.getBrowserInformation(cursor);
            //browser.setType("Default");

            //browserHandler.submitLog(context, browser.toJSONObject());
           cursor.moveToNext();
        }
    }

    public void handleBrowserInformation (Cursor _cusor, String browserType) {

        if (_cusor != null) {

            _cusor.moveToFirst();
            Browser browser = browserHandler.getBrowserInformation(_cusor);
            browser.setType(browserType);

            if (Integer.parseInt(browser.getType()) == BrowserConstants.BROWSER_URL_BOOKMARK ||
                    Integer.parseInt(browser.getType()) == BrowserConstants.BROWSER_URL_HISTORY) {

                if (Integer.parseInt(browser.getType()) == BrowserConstants.BROWSER_URL_BOOKMARK) {

                   // browser.setType("Bookmark");

                } else {

                   // browser.setType("History");
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

