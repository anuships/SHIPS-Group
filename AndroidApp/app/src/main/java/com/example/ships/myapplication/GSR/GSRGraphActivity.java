 package com.example.ships.myapplication.GSR;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.example.ships.myapplication.R;
import com.felhr.usbserial.UsbSerialInterface;
import com.felhr.usbserial.UsbSerialDevice;


public class GSRGraphActivity extends AppCompatActivity {
    private static String firstName;
    private static String lastName;
    private static String email;
    private static String uid;
    private static String typeOfTerm;

    private void readIntent(){
        Bundle b = getIntent().getExtras();
        firstName = b.getString("firstName");
        lastName = b.getString("lastName");
        email = b.getString("email");
        uid = b.getString("uid");
        typeOfTerm = b.getString("typeOfTerm");
    }

    private Bundle createBundle(){
        Bundle b = new Bundle();
        b.putString("firstName", firstName);
        b.putString("uid", uid);
        b.putString("lastName", lastName);
        b.putString("email", email);
        b.putString("typeOfTerm",typeOfTerm);//treatment term
        return b;
    }

    UsbDevice device = null;
    IntentFilter filter;
    UsbDeviceConnection connection;
    UsbManager usbManager;
    GSRLineGraph lineGraph;
    UsbSerialDevice serialPort;
    boolean serialUp = false;
    boolean haveBaseGSR = false;
    boolean appOn = true; 
    boolean appHasFocus = true;
    int count = 0;
    int point = 0;
    double gsrVal;
    double hVal;
    TextView heartRateDisplay;
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    UsbSerialInterface.UsbReadCallback mCallback = new UsbSerialInterface.UsbReadCallback() {
        //Defining a Callback which triggers whenever data is read.
        @Override
        public void onReceivedData(byte[] arg0) {
            String data;
            try{
                data = new String(arg0, "UTF-8");
                if (data.contains("=")){
                    ArrayList<String>  dataPoints = new ArrayList<>();
                    for (String dat : data.split("G")){
                        String[] hSplit = dat.split("H");
                        Collections.addAll(dataPoints, hSplit);
                    }
                    for (String s : dataPoints) {
                        if (s.length() > 1 && s.contains("=")) {
                            if (s.contains("B")) {
                                data = s.substring(s.indexOf('=') + 1, s.length());
                                double val = Double.parseDouble(data);
                                lineGraph.addBaseLine(val);
                                haveBaseGSR = true;
                            }else if (s.contains("C")){
                                data = s.substring(s.indexOf('=') + 1, s.length());
                                gsrVal  = Double.parseDouble(data);

                            }else if (s.contains("R")){
                                data = s.substring(s.indexOf('=') + 1, s.length());
                                double val = Double.parseDouble(data);
                                if (heartRateDisplay != null) {
                                    heartRateDisplay.setText(Double.toString(val));
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    public void checkForArduinoUSB(){
        if (!serialUp) {
            try {
                if (usbManager == null) {
                    usbManager = (UsbManager) getSystemService(GSRGraphActivity.USB_SERVICE);
                }
                HashMap usbDevices = usbManager.getDeviceList();
                if (!usbDevices.isEmpty()) {
                    for (Map.Entry<String, UsbDevice> entry : usbManager.getDeviceList().entrySet()) {
                        device = entry.getValue();
                        int deviceVID = device.getVendorId();
                        if (deviceVID == 6790) {
                            PendingIntent pi = PendingIntent.getBroadcast(this, 0,
                                    new Intent(ACTION_USB_PERMISSION), 0);
                            usbManager.requestPermission(device, pi);
                            break;
                        } else {
                            connection = null;
                            device = null;
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void clickStart(View view) {
        checkForArduinoUSB();
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent.getAction().equals(ACTION_USB_PERMISSION)) {
                    boolean granted = intent.getExtras().getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED);
                    if (granted && !serialUp) {
                        if (device != null){
                            connection = usbManager.openDevice(device);
                            serialPort = UsbSerialDevice.createUsbSerialDevice(device, connection);
                            if (serialPort != null) {
                                if (serialPort.open()) {
                                    serialPort.setBaudRate(9600);
                                    serialPort.setDataBits(UsbSerialInterface.DATA_BITS_8);
                                    serialPort.setStopBits(UsbSerialInterface.STOP_BITS_1);
                                    serialPort.setParity(UsbSerialInterface.PARITY_NONE);
                                    serialPort.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);
                                    serialPort.read(mCallback);
                                    serialUp = true;
                                } else {
                                    System.out.println("SERIAL PORT NOT OPEN");
                                }
                            }else{
                                System.out.println("SERIAL PORT NULL");
                            }
                        } else {
                            System.out.println("DEVICE NULL");
                        }
                    } else {
                        System.out.println("PERMISSION NOT GRANTED");
                    }
                } else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED)) {
                          checkForArduinoUSB();
                } else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_DETACHED)) {
                    serialPort.close();
                    serialUp = false;
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readIntent();
        if (usbManager == null){
            usbManager = (UsbManager) getSystemService(GSRGraphActivity.USB_SERVICE);
        }
        lineGraph = new GSRLineGraph(this);
        lineGraph.setXViewSize(30.0);
        setContentView(R.layout.activity_gsrgraph);
        heartRateDisplay = (TextView) findViewById(R.id.heartRateText);
        LinearLayout chartLyt = (LinearLayout) findViewById(R.id.chart);
        chartLyt.addView(lineGraph.getGraphView(),0);
        filter = new IntentFilter(ACTION_USB_PERMISSION);
        registerReceiver(broadcastReceiver, filter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (appOn) {
                    if (appHasFocus) {
                        String cur = "GC";
                        String base = "GB";
                        String heart = "HR";
                        if (serialPort != null) {
                                serialPort.write(cur.getBytes());

                                try {
                                if (!haveBaseGSR) {
                                    serialPort.write(base.getBytes());
                                }else{
                                    point++;
                                    lineGraph.addPoint(point, gsrVal);
                                }
                                serialPort.write(heart.getBytes());
                                Thread.sleep(200);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }else{
                        try{
                            Thread.sleep(1000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
        appHasFocus = true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (filter != null) {
            try {
                this.registerReceiver(broadcastReceiver, filter);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        appHasFocus = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        try{
            this.unregisterReceiver(broadcastReceiver);
        }catch (Exception e) {
            e.printStackTrace();
        }
        appHasFocus = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            this.unregisterReceiver(broadcastReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        appHasFocus = false;
    }
}