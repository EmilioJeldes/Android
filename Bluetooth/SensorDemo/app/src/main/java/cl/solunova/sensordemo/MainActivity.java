package cl.solunova.sensordemo;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ArrayList<BluetoothDevice> devices;
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private ScanCallback scanCallback;
    private ScanSettings scanSettings = new ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .build();
    private DeviceAdapter deviceAdapter;
    private Handler scanTimeoutHandler = new Handler();

    // VIEWS
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnOnOff = findViewById(R.id.btn_on_off);
        Button btnDiscover = findViewById(R.id.btn_start_discover);
        listView = findViewById(R.id.device_list);

        devices = new ArrayList<>();

        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();

        // Set Listeners
        btnOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableBluetooth();
            }
        });
        btnDiscover.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                startDiscovering();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void startDiscovering() {
        scanCallback = getScanCallback();
        checkBTPermissions();
        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();

        bluetoothLeScanner.startScan(null, scanSettings, scanCallback);
        scanTimeoutHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopScan();
            }
        }, 2000);
    }

    private void stopScan() {
        scanTimeoutHandler.removeCallbacksAndMessages(null);
        if (scanCallback != null) {
            Log.d(TAG, "stopScan: Stopping scanner");
            bluetoothLeScanner.stopScan(scanCallback);
            deviceAdapter = new DeviceAdapter(this, R.layout.bluetooth_sensor_found_layout, devices);
            listView.setAdapter(deviceAdapter);
            for (BluetoothDevice device : devices) {
                Log.d(TAG, "stopScan: name: " + device.getName() + "\naddress: " + device.getAddress());
            }
        }
    }

    private ScanCallback getScanCallback() {
        return new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                final BluetoothDevice device = result.getDevice();
                Log.d(TAG, "onScanResult: Device: " + device);

                if (device == null) {
                    Log.d(TAG, "onScanResult: Not devices founded");
                }
                devices.add(device);
                Log.d(TAG, "onScanResult: Device Found: " + device.getName() + ":" + device.getAddress());
            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                super.onBatchScanResults(results);
                Log.d(TAG, "onBatchScanResults: ");
            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
                Log.d(TAG, "onScanFailed: ");
            }
        };
    }

    private void enableBluetooth() {
        if (bluetoothAdapter == null) {
            Log.d(TAG, "enableBluetooth: does not have BT capabilities");
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivity(enableBluetoothIntent);
                Log.d(TAG, "enableBluetooth: Enabling Bluetooth...");
            }
            if (bluetoothAdapter.isEnabled()) {
                bluetoothAdapter.disable();
                Log.d(TAG, "enableBluetooth: Deacttivating bluetooth");
            }
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
