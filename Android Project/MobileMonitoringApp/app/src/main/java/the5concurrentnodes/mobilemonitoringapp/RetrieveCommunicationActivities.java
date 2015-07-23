package the5concurrentnodes.mobilemonitoringapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;


/**
 * Created by alu on 6/23/2015.
 */
public class RetrieveCommunicationActivities {

    private Context _context;
    private TelephonyManager telephonyManager;
    private CallStateListener callStateListener;
    private OutgoingReceiver outgoingReceiver;

    /**
     * Constructor recieves the context
     *
     */
    public RetrieveCommunicationActivities (Context context)
    {

        _context=context;
        callStateListener = new CallStateListener();
        outgoingReceiver= new OutgoingReceiver();




    }


    /**
     * Listener to detect incoming calls.
     */
    private class CallStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (state==TelephonyManager.CALL_STATE_RINGING) {

                // called when someone is ringing to this phone

                Toast.makeText(_context,
                        "Incoming: " + incomingNumber,
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Broadcast receiver to detect the outgoing calls.
     */
    public class OutgoingReceiver extends BroadcastReceiver {
        public OutgoingReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);

            Toast.makeText(_context,
                    "Outgoing Call: "+number,
                    Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Start all the services e.g calls detection.
     */
    public void start() {
        //call detection
        telephonyManager = (TelephonyManager) _context.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        _context.registerReceiver(outgoingReceiver, intentFilter);
    }





}
