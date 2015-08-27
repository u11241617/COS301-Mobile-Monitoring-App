package the5concurrentnodes.mmaData.Bluetooth;


import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.util.Log;


public class BluetoothObserver extends ContentObserver {
    private Context context;
    private BluetoothHandler bluetoothHandler;

    public BluetoothObserver(Context context)
    {
        super(null);
        this.context=context;
        this.bluetoothHandler= new BluetoothHandler();
       Bluetooth bluetooth= bluetoothHandler.getBluetoothInformation();
       // bluetoothHandler.submitLog(context, bluetooth.toJSONObject());
    }
    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        //Bluetooth bluetooth= bluetoothHandler.getBluetoothInformation();
       // bluetoothHandler.submitLog(context, bluetooth.toJSONObject());

    }
    @Override
    public boolean deliverSelfNotifications(){ return  false;}
}
