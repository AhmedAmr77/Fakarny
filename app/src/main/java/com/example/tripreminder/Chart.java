package com.example.tripreminder;



import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class Chart extends AppCompatActivity {

    private static final int MAX_X_VALUE = 12;
    private static final int MAX_Y_VALUE = 1500;
    private static final int MIN_Y_VALUE = 0;
    private static final String SET_LABEL = "Distance per Year";
    private static final String[] DAYS = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
    private static final String[] Months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    ArrayList<String> xEntrys = new ArrayList<>();
    private BarChart chart;
    TextView xAxisName, yAxisName, monthTextVi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        chart = (BarChart) findViewById(R.id.chart);
        monthTextVi = findViewById(R.id.monthTextVi);

        BarData data = createChartData();
        configureChartAppearance();
        prepareChartData(data);
    }


    private void prepareChartData(BarData data) {
        data.setValueTextSize(12f);
        chart.setData(data);
        chart.invalidate();
    }

    private BarData createChartData() {
        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = 0; i < MAX_X_VALUE; i++) {
            float x = i;
            float y = i+1 * 2f;
            values.add(new BarEntry(x, y));
        }

        monthTextVi.setText("Jan" + "  " + "2020");

        BarDataSet set1 = new BarDataSet(values, SET_LABEL);

        set1.setColor(Color.CYAN);
        //set1.setFormSize(25f);
        //set1.setValueTextSize(25f);


        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);

        return data;
    }

    private void configureChartAppearance() {
        chart.getDescription().setEnabled(false);
        chart.setDrawValueAboveBar(false);
        //chart.setPinchZoom(false);
        //chart.setDoubleTapToZoomEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return Months[(int) value];
            }
        });
        //xAxis.setGranularity(6f);
        //chart.getAxisLeft().setDrawGridLines(false);
        //chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisRight().setEnabled(false);
        //chart.getAxisLeft().setEnabled(true);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);


        chart.animateY(1500);   // add a nice and smooth animation

        chart.getLegend().setEnabled(false);  //hide text under chart


        //chart.setTouchEnabled(false);
        //chart.setDoubleTapToZoomEnabled(true);
        //chart.getXAxis().setEnabled(true);
        //chart.invalidate();  //to call from non ui thread

        //chart.getAxisLeft().setDrawLabels(false);
/*
        YAxis axisLeft = chart.getAxisLeft();
        //axisLeft.setGranularity(10f);
        //axisLeft.setAxisMinimum(0);

        YAxis axisRight = chart.getAxisRight();
        axisRight.setGranularity(10f);
        axisRight.setAxisMinimum(0);
 */
    }

}