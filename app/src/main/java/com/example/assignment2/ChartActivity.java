package com.example.assignment2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;


public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        LineChart chart =(LineChart) findViewById(R.id.chart);
        List<Entry> entries = new ArrayList<Entry>();
        //to display five values, and later formatter is used so years will not have decimal values
        float[] xAxis = {0f,1f,2f,3f,4f};
        float[] yAxis = {100, 200, 150, 320, 470};
        for (int i=0; i<xAxis.length; i++){
            entries.add(new Entry(xAxis[i], yAxis[i]));
        }
        //implementing IAxisValueFormatter interface to show year values not as float/decimal
        final String[] years = new String[] { "2015", "2016", "2017", "2018","2019" };
        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return years[(int)value];
            }
        };
        LineDataSet dataSet = new LineDataSet(entries, "This is Demo");
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        XAxis xAxisFromChart = chart.getXAxis();
        xAxisFromChart.setDrawAxisLine(true);
        xAxisFromChart.setValueFormatter(formatter);
        // minimum axis-step (interval) is 1,if no, the same value will be displayed multiple times
        xAxisFromChart.setGranularity(1f);
        xAxisFromChart.setPosition(XAxis.XAxisPosition.BOTTOM);

    }
}
