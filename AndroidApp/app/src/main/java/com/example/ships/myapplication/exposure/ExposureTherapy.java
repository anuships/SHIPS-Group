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
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ships.myapplication.OtherInterfaces.DrawerActivity;
import com.example.ships.myapplication.R;
import com.example.ships.myapplication.homepageAndRegistration.DBManager;
import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;



import java.io.InputStream;


public class ExposureTherapy extends DrawerActivity {
    private static String firstName;
    private static String lastName;
    private static String email;
    private static String uid;
    private static String typeOfTerm;
    final Handler ha = new Handler();

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
    double baselineval;
    boolean change = true;
    boolean biofeedbackHide = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readIntent();


//        setContentView(R.layout.activity_exposure_therapy);

        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_exposure_therapy, null,false);
        frameLayout.addView(activityView);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Systematic Desensitization");

        try {
            SQLiteDatabase mySqlDB = DBManager.getInstance(this).getWritableDatabase();
            mySqlDB.execSQL("CREATE TABLE if not exists SystematicDesensitization (EMAIL STRING, LEVEL STRING)");
            Cursor resultSet = mySqlDB.rawQuery("Select * from SystematicDesensitization where email=?", new String[]{email});
            if (resultSet.getCount() > 0) {
                resultSet.moveToFirst();
                level = resultSet.getString(1);
            } else {
                level = "1";
                String insertQuery = "INSERT INTO SystematicDesensitization (EMAIL, LEVEL) VALUES(?,?)";
                mySqlDB.execSQL(insertQuery, new String[]{email, level});
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
            mp.setOnCompletionListener(onCompletion);
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
        final long ELAPSETIME = 20000;
        ha.postDelayed(new Runnable() {

            @Override
            public void run() {
                autoChange();

                ha.postDelayed(this, ELAPSETIME);
            }
        }, ELAPSETIME);

        final long ELAPSETIMECHANGE = 1000;
        Handler ha2 = new Handler();
        ha2.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (baselineval - gsrVal >= 20){
                    if (change){
                        Button stopButton = (Button) findViewById(R.id.button2);
                        stopButton.performClick();
                        change = false;
                        Context context = getApplicationContext();
                        CharSequence text = "Please practice different relaxation skills";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                } else if (gsrVal >= baselineval) {
                    if (!change){
                        Button stopButton = (Button) findViewById(R.id.button2);
                        stopButton.performClick();
                        change = true;
                        Context context = getApplicationContext();
                        CharSequence text = "You can go on now";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
                ha.postDelayed(this, ELAPSETIMECHANGE);
            }
        }, ELAPSETIMECHANGE);
    }



    public void autoChange(){
//        long endTime = SystemClock.uptimeMillis();
//        if (Math.abs(endTime - startTime) > 5000) {


            if (!level.equals("5") && change) {
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
                    mp.setOnCompletionListener(onCompletion);
                    mp = MediaPlayer.create(this, R.raw.s1);
                    mp.start();
                    mp.setLooping(true);
                }
                Context context = getApplicationContext();
                CharSequence text = "Promoting to the next level";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
           }
        }


    public void back(View v){
        if (mp.isPlaying()){
            mp.stop();
        }
        mp.setOnCompletionListener(onCompletion);
        ha.removeCallbacksAndMessages(null);
        this.finish();
        startActivity(new Intent(this, ExposureDes.class).putExtras(createBundle()));
    }

    public void stop(View v){
        if (showImage == true){
            ImageView v2 = (ImageView) findViewById(R.id.imageView1);
            Button b = (Button) findViewById(R.id.button2);
            b.setText("Restart");
            v2.setImageResource(0);
            if (mp.isPlaying()){
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
                mp.setOnCompletionListener(onCompletion);
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
            mp.setOnCompletionListener(onCompletion);
            mp = MediaPlayer.create(this, R.raw.s1);
            mp.start();
            mp.setLooping(true);
        }
        else {
            mp.setOnCompletionListener(onCompletion);
        }
    }


    private MediaPlayer.OnCompletionListener onCompletion = new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mp) {
            if(mp.isPlaying())
            {
                mp.stop();
            }

        }
    };





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
                                baselineval = Double.parseDouble(data);
                                lineGraph.addBaseLine(baselineval);
                                haveBaseGSR = true;
                            }else if (s.contains("C")){
                                data = s.substring(s.indexOf('=') + 1, s.length());
                                gsrVal  = Double.parseDouble(data);
//                                if (gsrVal - baselineval < 50){
//                                    if (change){
//                                        Button stopButton = (Button) findViewById(R.id.button2);
//                                        stopButton.performClick();
//                                        change = false;
//                                    }
//                                } else if (gsrVal >= baselineval) {
//                                    if (!change){
//                                        Button stopButton = (Button) findViewById(R.id.button2);
//                                        stopButton.performClick();
//                                        change = true;
//                                    }
//                                }
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



    Menu biofeedbackMenu;

    @Override
    //Show the menu on the screen
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        biofeedbackMenu = menu;
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.systematic_desensitization_menu, menu);
        getMenuInflater().inflate(R.menu.systematic_desensitization_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change:
                hide();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void hide() {
        View v = findViewById(R.id.mon_chart);
        MenuItem controlBiofeedback = biofeedbackMenu.findItem(R.id.change);
        if (!biofeedbackHide){
            v.setVisibility(View.GONE);
            biofeedbackHide = true;
            controlBiofeedback.setTitle("Show biofeedback");
        } else{
            v.setVisibility(View.VISIBLE);
            biofeedbackHide = false;
            controlBiofeedback.setTitle("Hide biofeedback");
        }
    }


}
