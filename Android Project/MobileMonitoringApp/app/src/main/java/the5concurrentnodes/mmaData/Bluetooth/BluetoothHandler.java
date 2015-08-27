package the5concurrentnodes.mmaData.Bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import the5concurrentnodes.controllers.DataHandler;
import the5concurrentnodes.generic.Config;
import the5concurrentnodes.mmaData.interfaces.LogHandler;


public class BluetoothHandler implements LogHandler {
    public Bluetooth getBluetoothInformation()
    {
        String _state;
        String _mode;
        int state = BluetoothAdapter.getDefaultAdapter().getState();
        int mode = BluetoothAdapter.getDefaultAdapter().getScanMode();
        String localName = BluetoothAdapter.getDefaultAdapter().getName();
        String address = BluetoothAdapter.getDefaultAdapter().getAddress();
        String pairedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices().toString();

        switch (state) {
            case BluetoothConstants.STATE_TURNING_ON:
                _state = "turning on";
                break;
            case BluetoothConstants.STATE_TURNING_OFF:
                _state = "turning off";
                break;
            case BluetoothConstants.STATE_ON:
                _state = "on";
                break;
            case BluetoothConstants.STATE_OFF:
                _state = "off";
                break;
            case BluetoothConstants.STATE_DISCONNECTING:
                _state = "disconnecting";
                break;
            case BluetoothConstants.STATE_DISCONNECTED:
                _state = "disconnected";
                break;
            case BluetoothConstants.STATE_CONNECTING:
                _state = "connecting";
                break;
            case BluetoothConstants.STATE_CONNECTED:
                _state = "connected";
                break;
            default:
                _state ="unknown";
                break;
        }

        switch (mode)
        {
            case BluetoothConstants.SCAN_MODE_CONNECTABLE:
                _mode = "Connectable";
                break;
            case BluetoothConstants.SCAN_MODE_NONE:
                _mode = "none";
                break;
            case BluetoothConstants.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                _mode = "connectable and discoverable";
                break;
            default:
                _mode = "unknown";
        }

        Log.d("\nBluetooth agagaa"," **State: "+ _state + " ***mode: " +_mode + " ***LocalName: " +localName+ " ***Address: " +address+ " ***PairedDevices:  " +pairedDevices );
        return new Bluetooth(_state,_mode,localName,address,pairedDevices);
    }
    @Override
    public void submitLog(Context context, JSONObject params) {
        String url = Config.REST_API + "/bluetooth";
        DataHandler.submitLog(context, url, params);
    }
}
