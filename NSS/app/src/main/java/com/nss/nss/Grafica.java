package com.nss.nss;

import android.content.Context;
import android.graphics.Color;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Grafica {


    private GraphView graphView;
    private Viewport viewport;
    private GridLabelRenderer gridLabelRenderer;
    private LineGraphSeries<DataPoint> series;
    private Context context;
    private TelephonyManager tm;
    private TelefonoMedida telefonoMedida;


    public Grafica(GraphView grafica, Context context) {
        graphView = grafica;
        viewport = graphView.getViewport();
        gridLabelRenderer = graphView.getGridLabelRenderer();
        this.context=context;
    }


    private void escuchar(){
        tm=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        telefonoMedida=new TelefonoMedida(series,context);
        tm.listen(telefonoMedida, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }

    public void inicializarGraphView() {
        graphView.setTitle("Grafica en tiempo real dBm");
        graphView.setTitleTextSize(15);
        graphView.setTitleColor(Color.RED);
        gridLabelRenderer.setPadding(10);

        gridLabelRenderer.setGridColor(Color.BLACK);

        gridLabelRenderer.setHorizontalAxisTitleTextSize(15);
        gridLabelRenderer.setHorizontalAxisTitle("valor x");
        gridLabelRenderer.setHorizontalAxisTitleColor(Color.RED);

        gridLabelRenderer.setVerticalAxisTitleTextSize(15);
        gridLabelRenderer.setVerticalAxisTitle("dBm");
        gridLabelRenderer.setVerticalAxisTitleColor(Color.RED);

        series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 1),
        });

        series.setColor(Color.GREEN);
        series.setDrawBackground(true);
        series.setBackgroundColor(Color.argb(50, 0, 255, 0));
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(6);
        series.setThickness(3);

        graphView.addSeries(series);

        viewport.setBackgroundColor(Color.BLACK);
        viewport.setMinX(0);
        viewport.setMaxX(10);
        viewport.setXAxisBoundsManual(true);
        viewport.setMinY(-120);
        viewport.setMaxY(-50);
        viewport.setYAxisBoundsManual(true);
        viewport.setScalable(true);
        escuchar();
    }


}
