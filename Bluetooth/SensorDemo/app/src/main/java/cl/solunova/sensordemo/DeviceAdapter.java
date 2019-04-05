package cl.solunova.sensordemo;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DeviceAdapter extends ArrayAdapter<BluetoothDevice> {

    private static final String TAG = "DeviceAdapter";

    private ArrayList<BluetoothDevice> devicesList;
    private LayoutInflater layoutInflater;
    private int viewResourceId;

    public DeviceAdapter(Context context, int resource, ArrayList<BluetoothDevice> devices) {
        super(context, resource);
        this.viewResourceId = resource;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.devicesList = devices;
    }

    static class ViewHolder {
        TextView deviceName;
        TextView deviceMacAddress;
    }

    @Override
    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;

        // Get View Holder
        if (convertView == null) {
            Log.d(TAG, "getView: Converview Null");
            // initialize convert view
            convertView = layoutInflater.inflate(viewResourceId, null);

            // Initialize view holder
            viewHolder = new ViewHolder();
            viewHolder.deviceName = convertView.findViewById(R.id.device_name);
            viewHolder.deviceMacAddress = convertView.findViewById(R.id.device_mac_address);
            convertView.setTag(viewHolder);
        } else {
            Log.d(TAG, "getView: Converview Not null");
            viewHolder = (ViewHolder) convertView.getTag();
        }

        BluetoothDevice btDevice = devicesList.get(position);

        if (btDevice != null) {
            viewHolder.deviceName.setText(btDevice.getName());
            viewHolder.deviceMacAddress.setText(btDevice.getAddress());
        }

        Log.d(TAG, "getView: deviceName: " + viewHolder.deviceName);
        Log.d(TAG, "getView: deviceAddress: " + viewHolder.deviceMacAddress);

        return convertView;
    }
}
