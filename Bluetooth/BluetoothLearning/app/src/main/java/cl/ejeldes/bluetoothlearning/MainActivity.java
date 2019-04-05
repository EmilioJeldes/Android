package cl.ejeldes.bluetoothlearning;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    // Bluetooth Adapter for bluetooth control
    private BluetoothAdapter mBluetoothAdapter;

    // Bluetooth Devices arrayList
    public ArrayList<BluetoothDevice> devices = new ArrayList<>();
    public DeviceListAdapter deviceListAdapter;

    // ListView
    private ListView deviceListView;

    // ON OFF BT BROADCAST RECEIVER
    private final BroadcastReceiver mBroadCastReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // get the action from the intent
            String action = intent.getAction();

            // compare the action with the expected.
            // In this case BluetoothAdapter.ACTION_STATE_CHANGED to catch Bluetooth State
            if (action != null && action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                // Get the current state from the passed intent
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "onReceive: STATE OFF");
                        break;

                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "onReceive: STATE TURNING OFF");
                        break;

                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "onReceive: STATE ON");
                        break;

                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "onReceive: STATE TURNING ON");
                        break;
                }
            }
        }
    };

    // BroadcastReceiver for Discoverability mode on/off
    BroadcastReceiver mBroadCastReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Grab the action from the intent
            final String action = intent.getAction();

            // Compare it with ACTION_SCAN_MODE_CHANGED to catch bluetooth scan changes
            if (action != null && action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {
                // Get mode from intent
                int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);

                switch (mode) {
                    // Device is in discoverable mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Log.d(TAG, "onReceive: Discoverability Enabled");
                        break;

                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        Log.d(TAG, "onReceive: Discoverability enabled. Able to receive connections");
                        break;

                    case BluetoothAdapter.SCAN_MODE_NONE:
                        Log.d(TAG, "onReceive: Discoverability disabled. Not able to receive connections");
                        break;

                    case BluetoothAdapter.STATE_CONNECTING:
                        Log.d(TAG, "onReceive: Connecting...");
                        break;

                    case BluetoothAdapter.STATE_CONNECTED:
                        Log.d(TAG, "onReceive: Connected");
                        break;
                }
            }
        }
    };

    // Broadcast Receiver to discover devices and set it to a list
    BroadcastReceiver mBroadCastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get the action
            final String action = intent.getAction();
            Log.d(TAG, "onReceive: ACTION FOUND");

            // Check the action
            if (action != null && action.equals(BluetoothDevice.ACTION_FOUND)) {
                // Get the bluetooth from getParcelableExtra and passing the EXTRA_DEVICE
                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                // Add the device to the list
                devices.add(bluetoothDevice);
                Log.d(TAG, "onReceive: " + bluetoothDevice.getName() + ": " + bluetoothDevice.getAddress());
                deviceListAdapter = new DeviceListAdapter(context, R.layout.activity_device_list_adapter, devices);
                deviceListView.setAdapter(deviceListAdapter);
            }
        }
    };

    BroadcastReceiver mBroadCastReceiver4 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action != null && action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                // get bluetooth device from parcelable extra
                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                switch (bluetoothDevice.getBondState()) {
                    case BluetoothDevice.BOND_BONDED:
                        Log.d(TAG, "onReceive: BOND_BONDED");
                        break;

                    case BluetoothDevice.BOND_BONDING:
                        Log.d(TAG, "onReceive: BOND_BONDING");
                        break;

                    case BluetoothDevice.BOND_NONE:
                        Log.d(TAG, "onReceive: BOND_NONE");
                        break;
                }


            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize variables
        Button btnOnOff = findViewById(R.id.btn_on_off);
        Button btnDiscoverability = findViewById(R.id.btn_enable_discovery);
        deviceListView = findViewById(R.id.list_new_devices);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Broadcast Action: Indicates a change in the bond state of a remote device. For example, if a device is bonded (paired).
        IntentFilter bondStateFilter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mBroadCastReceiver4, bondStateFilter);

        // BUTTON ON/OFF BLUETOOTH LISTENER
        btnOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ensableDisableBT();
            }
        });

        // LIST VIEW ON CLICK LISTENER
        deviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mBluetoothAdapter.cancelDiscovery();

                Log.d(TAG, "onItemClick: You clicked on a device");
                String deviceName = devices.get(position).getName();
                String deviceAddress = devices.get(position).getAddress();

                Log.d(TAG, "onItemClick: deviceName: " + deviceName);
                Log.d(TAG, "onItemClick: deviceAddress: " + deviceAddress);

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    Log.d(TAG, "Trying to pair with " + deviceName);
                    devices.get(position).createBond();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        devices.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        devices.clear();
        try {
            unregisterReceiver(mBroadCastReceiver1);
            unregisterReceiver(mBroadCastReceiver2);
            unregisterReceiver(mBroadCastReceiver3);
            unregisterReceiver(mBroadCastReceiver4);
        } catch (Exception e) {
            Log.d(TAG, "onDestroy: Exception trying to unregister receivers");
        }

    }

    private void ensableDisableBT() {
        if (mBluetoothAdapter == null) {
            Log.d(TAG, "ensableDisableBT: does not have BT capabilities");
        }
        if (!mBluetoothAdapter.isEnabled()) {
            // Intent to enable Bluetooth (ACTION_REQUEST_ENABLE)
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);

            // Intent filter with ACTION_STATE_CHANGED
            IntentFilter btIntentFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadCastReceiver1, btIntentFilter);
        }
        if (mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();

            // Intent filter with ACTION_STATE_CHANGED
            IntentFilter btIntentFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadCastReceiver1, btIntentFilter);
        }
    }

    public void btnEnableDisableDiscoverable(View view) {
        Log.d(TAG, "btnEnableDisableDiscoverable: Making device discoverable for 300 seconds");

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);

        IntentFilter intentFilter = new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        registerReceiver(mBroadCastReceiver2, intentFilter);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void btnDiscover(View view) {
        Log.d(TAG, "btnDiscover: Loogking for unpaired devices.");

        // If bluetoothAdapter already discovering
        if (mBluetoothAdapter.isDiscovering()) {
            // cancel it
            mBluetoothAdapter.cancelDiscovery();
            Log.d(TAG, "btnDiscover: Canceling discovery.");

            // Check BT permissions in manifes
            checkBTPermissions();

            // Start discovery again
            mBluetoothAdapter.startDiscovery();

            // Register bc receiver with intent ACTION_FOUND (when a remote device is found)
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadCastReceiver3, discoverDevicesIntent);
        }
        // if bluetooth is not discovering
        if (!mBluetoothAdapter.startDiscovery()) {
            Log.d(TAG, "btnDiscover: Discovery wasn't on");
            // Check BT permissions in manifes
            checkBTPermissions();

            mBluetoothAdapter.startDiscovery();

            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadCastReceiver3, discoverDevicesIntent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkBTPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Log.d(TAG, "checkBTPermissions: cheking permissions");
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");

            if (permissionCheck != 0) {
                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);
                Log.d(TAG, "checkBTPermissions: permissions ok");
            }
        } else {
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }
}
