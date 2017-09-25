package com.example.ships.myapplication.GSR;

import android.app.Activity;
import android.graphics.Color;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class GSRLineGraph {
    private GraphicalView graphView;
    private XYMultipleSeriesDataset dataset;
    private XYSeries series;
    private XYMultipleSeriesRenderer mRenderer;
    private double xViewSize;
    private double xMin;
    private double xMax;

    public GSRLineGraph(Activity activity){
        series = new XYSeries("GSR Reading");
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setLineWidth(10);
        renderer.setColor(Color.BLACK);
        renderer.setDisplayBoundingPoints(true);
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setPointStrokeWidth(3);
        mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.setLegendHeight(1);
        mRenderer.setZoomButtonsVisible(false);
        mRenderer.setMargins(new int[] {0, 0, 0, 0});

        mRenderer.setGridColor(0);
       // mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
        mRenderer.addSeriesRenderer(renderer);
        mRenderer.setPanEnabled(false, false);
        mRenderer.setYAxisMax(500);
        mRenderer.setYAxisMin(0);
        mRenderer.setShowGrid(false);
        mRenderer.setShowAxes(false);
        dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series);
        graphView = ChartFactory.getLineChartView(activity, dataset, mRenderer);

    }
    public void addBaseLine(double y){
        mRenderer.setYAxisMax(y + 100);
        mRenderer.setYAxisMin(y - 100);

        //XYSeries baseLine = new XYSeries("baseLine");
        //baseLine.add(0.0, y);
        //baseLine.add(20.0, y);
        //for (int i = 0; i < 2000;i++){
          //  baseLine.add((double) i, y);
        //}
        //dataset.addSeries(baseLine);
        //XYSeriesRenderer renderer = new XYSeriesRenderer();
        //renderer.setLineWidth(2);
        //renderer.setColor(Color.BLUE);
        //renderer.setDisplayBoundingPoints(true);
       // renderer.setPointStyle(PointStyle.CIRCLE);
        //renderer.setPointStrokeWidth(3);
       // baseLine.add(200.0, y);
        //mRenderer.addSeriesRenderer(renderer);
        //this.rePaint();
    }
    void addPoint(double x, double y){
        series.add(x, y);
        if (x > xMax){
            xMin = x - xViewSize;
            xMax = x;
            mRenderer.setXAxisMin(xMin);
            mRenderer.setXAxisMax(xMax);
        }
        graphView.repaint();
    }
    public GraphicalView getGraphView(){
        return graphView;
    }
    public void setXViewSize(double x){
        this.xMin = 0;
        this.xMax = x;
        this.xViewSize = x;
        mRenderer.setXAxisMax(xMax);
        mRenderer.setXAxisMin(xMin);
        this.rePaint();
    }
    void rePaint(){
        graphView.repaint();
    }
}
