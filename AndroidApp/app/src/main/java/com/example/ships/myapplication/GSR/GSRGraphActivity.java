package com.example.ships.myapplication.GSR;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

import com.example.ships.myapplication.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.shokai.firmata.ArduinoFirmata;
public class GSRGraphActivity extends AppCompatActivity {
    private static final Random RANDOM = new Random();
    private int lastX = 0;
    private GraphicalView graphView;
    private XYMultipleSeriesDataset dataset;
    private XYSeries series;
    boolean appOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gsrgraph);
        series = new XYSeries("London Temperature hourly");
        int hour = 0;
        for (int i = 0; i < 10; i++) {
            series.add(hour++, i*6);
        }

        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setLineWidth(2);
        renderer.setColor(Color.RED);
        renderer.setDisplayBoundingPoints(true);
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setPointStrokeWidth(3);
        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
        mRenderer.addSeriesRenderer(renderer);
        mRenderer.setPanEnabled(false, false);
        mRenderer.setYAxisMax(35);
        mRenderer.setYAxisMin(0);
        mRenderer.setShowGrid(true); // we show the grid
        dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series);
        graphView = ChartFactory.getLineChartView(this, dataset, mRenderer);

        LinearLayout chartLyt = (LinearLayout) findViewById(R.id.chart);
        chartLyt.addView(graphView,0);
        ArduinoFirmata arduino = new ArduinoFirmata(this);
        Toast.makeText(this,"HI", Toast.LENGTH_LONG).show();

        try{
            arduino.connect();
            System.out.println("WORKED!");
            Toast.makeText(this,"CONNECTED TO ARDUINO", Toast.LENGTH_LONG).show();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;

                while (appOn){
                    i++;
                    final int x = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            series.add(x, x);
                            graphView.repaint();
                        }
                    });
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        // manage error ...
                    }
                }
            }
        }).start();
    }
    @Override
    protected void onResume() {
        super.onResume();
        appOn = false;
    }
}