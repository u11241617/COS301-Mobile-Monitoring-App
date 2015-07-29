package the5concurrentnodes.mmaData.Browser;


import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONObject;

import the5concurrentnodes.controllers.DataHandler;
import the5concurrentnodes.generic.Config;
import the5concurrentnodes.mmaData.interfaces.LogHandler;

public class BrowserHandler implements LogHandler {
    public Browser getBrowserInformation(Cursor cursor)
    {
        String type=cursor.getString(cursor.getColumnIndex(BrowserConstants.TYPE_COLUMN_NAME));
        String url=cursor.getString(cursor.getColumnIndex(BrowserConstants.URL_COLUMN_NAME));
        String title=cursor.getString(cursor.getColumnIndex(BrowserConstants.TITLE_COLUMN_NAME));
        String visits=cursor.getString(cursor.getColumnIndex(BrowserConstants.NUMBER_VISITS_COLUMN_NAME));
        String date=cursor.getString(cursor.getColumnIndex(BrowserConstants.DATE_COLUMN_NAME));
        String created=cursor.getString(cursor.getColumnIndex(BrowserConstants.CREATED_COLUMN_NAME));

        Log.d("\n \n Browser", type + " " + url+ "\n Visits: "+ visits);// + " \n Title: "+ title + "\n date: "+date + " \n Created: "+ created);
        return new Browser(type,url,title,visits,date,created);

    }
    @Override
    public void submitLog(Context context, JSONObject params) {

        String url = Config.REST_API + "/browser";
        DataHandler.submitLog(context, url, params);
    }

}
