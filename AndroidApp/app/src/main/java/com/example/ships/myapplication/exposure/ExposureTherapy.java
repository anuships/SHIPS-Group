package com.example.ships.myapplication.exposure;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ships.myapplication.GSR.GSRGraphActivity;
import com.example.ships.myapplication.R;
import com.example.ships.myapplication.homepageAndRegistration.DBManager;
import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;



import java.io.InputStream;


public class ExposureTherapy extends AppCompatActivity {
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


    public void writeLevel(String level){
        SQLiteDatabase mySqlDB = DBManager.getInstance(this).getWritableDatabase();
        ContentValues updatedValues = new ContentValues();
        updatedValues.put("LEVEL", level);
        String key = "EMAIL = ?";
        mySqlDB.update("SystematicDesensitization", updatedValues, key, new String[] { email});
    }

    boolean showImage = true;
    String level = "1";
    Dialog dialog;
    MediaPlayer mp = new MediaPlayer();
    Handler h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readIntent();
        setContentView(R.layout.activity_exposure_therapy);
        try {
            SQLiteDatabase mySqlDB = DBManager.getInstance(this).getWritableDatabase();
            mySqlDB.execSQL("CREATE TABLE if not exists SystematicDesensitization (EMAIL STRING, LEVEL STRING)");
            Cursor resultSet = mySqlDB.rawQuery("Select * from SystematicDesensitization where email=?", new String[]{email});
            if (resultSet.getCount() > 0) {
                resultSet.moveToFirst();
                level = resultSet.getString(1);
            } else {
                level = "1";
                writeLevel(level);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Button b3 = (Button) findViewById(R.id.button3);
        String bText = "Level" + level;
        b3.setText(bText);
        String resName = "i" + level;
        ImageView v = (ImageView) findViewById(R.id.imageView1);
        InputStream imageIS = this.getResources().openRawResource(getResources().getIdentifier(resName, "raw", this.getPackageName()));
        Bitmap myImage = BitmapFactory.decodeStream(imageIS);
        v.setImageBitmap(myImage);
        if (level.equals("5")) {
            mp = MediaPlayer.create(this, R.raw.s1);
            mp.start();
            mp.setLooping(true);
        }






        if (usbManager == null){
            usbManager = (UsbManager) getSystemService(ExposureTherapy.USB_SERVICE);
        }
        lineGraph = new GSRLineGraphSD(this);
        lineGraph.setXViewSize(30.0);
        LinearLayout chartLyt = (LinearLayout) findViewById(R.id.mon_chart);
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







        //Testing for auto-change function
        final long ELAPSETIME = 5000;
        final Handler ha=new Handler();
        ha.postDelayed(new Runnable() {

            @Override
            public void run() {
                autoChange();

                ha.postDelayed(this, ELAPSETIME);
            }
        }, ELAPSETIME);
//        startService(this, GSRGraphActivity.class);
    }

    public void autoChange(){
//        long endTime = SystemClock.uptimeMillis();
//        if (Math.abs(endTime - startTime) > 5000) {

            if (!level.equals("5")) {
                level = String.valueOf(Integer.parseInt(level) + 1);
                String b3Text = "Level" + level;
                Button b3 = (Button) findViewById(R.id.button3);
                b3.setText(b3Text);
                ImageView v4 = (ImageView) findViewById(R.id.imageView1);
                String resName = "i" + level;
                InputStream imageIS = this.getResources().openRawResource(getResources().getIdentifier(resName, "raw", this.getPackageName()));
                Bitmap myImage = BitmapFactory.decodeStream(imageIS);
                v4.setImageBitmap(myImage);
                writeLevel(level);
                if (level.equals("5")) {
                    mp = MediaPlayer.create(this, R.raw.s1);
                    mp.start();
                    mp.setLooping(true);
                }
                Context context = getApplicationContext();
                CharSequence text = "Promoting to the next level";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
//            }
        }
    }

    public void back(View v){
        if (mp.isPlaying()) {
            mp.stop();
        }
        startActivity(new Intent(this, ExposureDes.class).putExtras(createBundle()));
    }

    public void stop(View v){
        if (showImage == true){
            ImageView v2 = (ImageView) findViewById(R.id.imageView1);
            Button b = (Button) findViewById(R.id.button2);
            b.setText("Restart");
            v2.setImageResource(0);
            if (mp.isPlaying()) {
                mp.stop();
            }
            showImage = false;
        }
        else{
            ImageView v3 = (ImageView) findViewById(R.id.imageView1);
            String resName = "i" + level;
            InputStream imageIS = this.getResources().openRawResource(getResources().getIdentifier(resName,"raw",this.getPackageName()));
            Bitmap myImage = BitmapFactory.decodeStream(imageIS);
            v3.setImageBitmap(myImage);
            showImage = true;
            Button b = (Button) findViewById(R.id.button2);
            b.setText("Stop");
            if (level.equals("5")){
                    mp = MediaPlayer.create(this, R.raw.s1);
                    mp.start();
                    mp.setLooping(true);
            }
        }
    }
    public void goToRelaxationTraning(View v){
        checkForArduinoUSB();
//        startActivity(new Intent(this, GSRGraphActivity.class).putExtras(createBundle()));
    }

    public void selectLevel(View v){
        showRadioButtonDialog();
    }

    private void showRadioButtonDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.level_dialog);
        dialog.show();

        RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.levelChosen);

        for(int i=0;i<5;i++){
            RadioButton rb=new RadioButton(this);
            rb.setText(Integer.toString(i+1));
            rg.addView(rb);
        }
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int childCount = group.getChildCount();
                for (int x = 0; x < childCount; x++) {
                    RadioButton btn = (RadioButton) group.getChildAt(x);
                    if (btn.getId() == checkedId) {
                        level = btn.getText().toString();
                    }
                }
            }
        });
    }

    public void changeLevel(View v){
        Button b3 = (Button) findViewById(R.id.button3);
        String bText = "Level" +  level;
        b3.setText(bText);
        dialog.dismiss();
        ImageView v4 = (ImageView) findViewById(R.id.imageView1);
        String resName = "i" + level;
        InputStream imageIS = this.getResources().openRawResource(getResources().getIdentifier(resName,"raw",this.getPackageName()));
        Bitmap myImage = BitmapFactory.decodeStream(imageIS);
        v4.setImageBitmap(myImage);
        writeLevel(level);
        showImage = true;
        if (level.equals("5")){
            mp = MediaPlayer.create(this, R.raw.s1);
            mp.start();
            mp.setLooping(true);
        }
        else {
            if (mp.isPlaying()) {
                mp.stop();
            }
        }
    }








    UsbDevice device = null;
    IntentFilter filter;
    UsbDeviceConnection connection;
    UsbManager usbManager;
    GSRLineGraphSD lineGraph;
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
                    ArrayList<String> dataPoints = new ArrayList<>();
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
                    usbManager = (UsbManager) getSystemService(ExposureTherapy.USB_SERVICE);
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
    public void clickBack(View view) {
        super.onBackPressed();
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



}
