package com.FakarnyAppForTripReminder.Fakarny;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Chart extends AppCompatActivity {

    private final String SET_LABEL = "Distance per Year";
    //private static final String[] DAYS = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
    private String[] Months;
    ArrayList<String> xEntrys = new ArrayList<>();
    private BarChart chart;
    TextView xAxisName, yAxisName, monthTextVi;

    List<HistoryTripInfo> historyTripInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        chart = (BarChart) findViewById(R.id.chart);
        monthTextVi = findViewById(R.id.monthTextVi);

        Intent i = getIntent();
        historyTripInfoList = (List<HistoryTripInfo>) i.getSerializableExtra("histInfo");
        Months = new String[]{getApplicationContext().getResources().getString(R.string.jan), getApplicationContext().getResources().getString(R.string.feb), getApplicationContext().getResources().getString(R.string.mar), getApplicationContext().getResources().getString(R.string.april), getApplicationContext().getResources().getString(R.string.may), getApplicationContext().getResources().getString(R.string.jun), getApplicationContext().getResources().getString(R.string.jul), getApplicationContext().getResources().getString(R.string.aug), getApplicationContext().getResources().getString(R.string.sep), getApplicationContext().getResources().getString(R.string.oct), getApplicationContext().getResources().getString(R.string.nov), getApplicationContext().getResources().getString(R.string.dec)};
        BarData data = createChartData();
        configureChartAppearance();
        prepareChartData(data);
    }

    private void prepareChartData(BarData data) {
        data.setValueTextSize(12f);
        chart.setData(data);
        chart.invalidate();
    }

    int convertDate(long l){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(l);
        return calendar.get(Calendar.MONTH);
    }

     float setMonthData(int m){
        float current = 0.0f;
        for(HistoryTripInfo currentTrip : historyTripInfoList){
            if (m==convertDate(currentTrip.getTripTime()))
                current += (float)(currentTrip.getTripDistance());
        }
        return (current/1000);
    }

    private BarData createChartData() {
        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = 0; i < Months.length; i++) {
            float x = i;
            float y = setMonthData(i);
            values.add(new BarEntry(x, y));
        }

        monthTextVi.setText(getApplicationContext().getResources().getString(R.string.chart_description));

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

