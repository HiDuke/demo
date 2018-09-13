package com.sun.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LineChartTime extends AppCompatActivity {
    private LineChart mChart;
    private Date theDate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linechart_time);

//        tvX = findViewById(R.id.tvXMax);
//        mSeekBarX = findViewById(R.id.seekBar1);
//        mSeekBarX.setProgress(100);
//        tvX.setText("100");
//
//        mSeekBarX.setOnSeekBarChangeListener(this);

        mChart = findViewById(R.id.chart1);

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        mChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);

        mChart.setPinchZoom(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setViewPortOffsets(0f, 0f, 0f, 0f);
        Bundle args = getIntent().getExtras();
        List<DataEveryDay> list = (List<DataEveryDay>) args.get("datas2");

        // add data

        setData(list);
        mChart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();
        l.setEnabled(false);

        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view,theDate);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
//        xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(true);
        xAxis.setTextColor(Color.rgb(255, 192, 56));
        xAxis.setCenterAxisLabels(true);
//        xAxis.setAxisMaximum(24*60*60);
        xAxis.setGranularity(3600f); // one hour
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private SimpleDateFormat mFormat = new SimpleDateFormat("HH");

            @Override
            public String getFormattedValue(float value, AxisBase axis) {

//                long millis = TimeUnit.SECONDS.toMillis((long) value);
                return mFormat.format(new Date((long)value*1000+theDate.getTime()));
            }
        });

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
//        leftAxis.setTypeface(mTfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
//        leftAxis.setAxisMinimum(0f);
//        leftAxis.setAxisMaximum(100);
//        leftAxis.setYOffset(-9f);
        leftAxis.setTextColor(Color.rgb(255, 192, 56));

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    private void setData(List<DataEveryDay> list) {
        theDate = new Date(list.get(0).getmTime().getYear(),list.get(0).getmTime().getMonth(),list.get(0).getmTime().getDate());

//        int count = list.size();
        // now in hours
//        long now = TimeUnit.MILLISECONDS.toSeconds(list.get(0).getmTime().getTime());

//        for (DataEveryDay d:list) {
//            Log.e("cheng",d.getmTemperature()+"||||||||||"+d.getmTime());
//        }

        ArrayList<Entry> values = new ArrayList<Entry>();

//        float from = now;

//         count = hours
//        float to = TimeUnit.MILLISECONDS.toSeconds(list.get(list.size()-1).getmTime().getTime());

        // increment by 1 hour
//        for (long x = now; x < to; x+= 1) {
//            values.add(new Entry(x, list.get(x/86400000))); // add one entry per hour
//        }

        for (DataEveryDay data : list) {
            Log.e("cheng2",data.getmTime().getTime()+"|"+(float)(data.getmTime().getTime())+"|"+(long)(float)(data.getmTime().getTime())+"|"+new Date(data.getmTime().getYear(),data.getmTime().getMonth(),data.getmTime().getDate()).toString()+"|"+new Date(data.getmTime().getYear(),data.getmTime().getMonth(),data.getmTime().getDate()).getTime()+"|"+data.getmTemperature());
            values.add(new Entry((float) ((data.getmTime().getTime()-theDate.getTime())/1000), data.getmTemperature()));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(values, "DataSet 1");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setValueTextColor(ColorTemplate.getHoloBlue());
        set1.setLineWidth(1.5f);
        set1.setDrawCircles(true);
        set1.setDrawValues(false);
        set1.setDrawFilled(true);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);

        // create a data object with the datasets
        LineData data = new LineData(set1);
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(9f);

        // set data
        mChart.setData(data);
    }
}
