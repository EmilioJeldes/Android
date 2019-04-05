package cl.ejeldes.bluetoothlearning;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DeviceListAdapter extends ArrayAdapter<BluetoothDevice> {

    private LayoutInflater mLayoutInflater;
    private ArrayList<BluetoothDevice> mDevices;
    private int mViewResourceId;

    public DeviceListAdapter(Context context, int resource, ArrayList<BluetoothDevice> devices) {
        super(context, resource, devices);
        this.mDevices = devices;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = resource;
    }

    static class ViewHolderItem {
        TextView deviceName;
        TextView deviceAddress;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolderItem viewHolder;

        // Caching the View to avoid re renderings
        // If the view gets loaded by the first time
        if (convertView == null) {
            // Gets initialized
            convertView = mLayoutInflater.inflate(mViewResourceId, null);

            // setup Viewholder
            viewHolder = new ViewHolderItem();
            viewHolder.deviceName = convertView.findViewById(R.id.lblDeviceName);
            viewHolder.deviceAddress = convertView.findViewById(R.id.lblDeviceAddress);
            convertView.setTag(viewHolder);

        } else {
            // If its not null, it gets from the tag inside the view
            viewHolder = ((ViewHolderItem) convertView.getTag());
        }

        // Get the bluetooth device from the adapter list
        BluetoothDevice device = mDevices.get(position);
        if (device != null) {
            // set the text to the views
            viewHolder.deviceName.setText(device.getName());
            viewHolder.deviceAddress.setText(device.getAddress());
        }

        return convertView;
    }
}
