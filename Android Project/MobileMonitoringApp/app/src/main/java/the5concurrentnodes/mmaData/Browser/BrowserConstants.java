package the5concurrentnodes.mmaData.Browser;

public class BrowserConstants {
    public static final String ID_COLUMN_NAME = "_id";
    public static final String TYPE_COLUMN_NAME = "bookmark";
    public static final String DATE_COLUMN_NAME = "date";
    public static final String CREATED_COLUMN_NAME = "created";
    public static final String URL_COLUMN_NAME = "url";
    public static final String TITLE_COLUMN_NAME = "title";
    public static final String NUMBER_VISITS_COLUMN_NAME = "visits";
    public static final int BROWSER_URL_HISTORY = 0; // Indicates that value is a History item
    public static final int BROWSER_URL_BOOKMARK = 1;// Indicates that value is a bookmark Item

    public static final String CHROME_CONTENT_CALL_URI = "content://com.android.chrome.browser/bookmarks";
    public static final String OPERA_CONTENT_CALL_URI = "com.opera.mini.android.Browser";
    public static final String CONTENT_CALL_URI = "content://browser/bookmarks";
    public static final String SAMSUNG_CONTENT_CALL_URI = "content://com.sec.android.app.sbrowser.browser/bookmarks"; /// URI for the SUMSANG browser
}
