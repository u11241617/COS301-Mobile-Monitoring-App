package the5concurrentnodes.mobilemonitoringapp;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by alu on 6/23/2015.
 */
public class DataPullService  extends Service {


    /**
     * An IntentService must always have a constructor that calls the super constructor. The
     * string supplied to the super constructor is used to give a name to the IntentService's
     * background thread.
     */
    private RetrieveCommunicationActivities retrieveCommunicationActivities;
    public DataPullService() {

        //super("DataPullService");
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "The new Service was Created DataPull", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStart(Intent intent, int startId) {
        // For time consuming an long tasks you can launch a new thread here...
        Toast.makeText(this, " Service  DataPull Started", Toast.LENGTH_LONG).show();
        retrieveCommunicationActivities= new RetrieveCommunicationActivities(this);
        retrieveCommunicationActivities.start();

    }


}
