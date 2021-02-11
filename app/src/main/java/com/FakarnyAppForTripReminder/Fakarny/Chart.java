package com.FakarnyAppForTripReminder.Fakarny;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Chart extends AppCompatActivity {

    private String[] Months;
    private BarChart chart;
    TextView monthTextVi;

    List<HistoryTripInfo> historyTripInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        chart = findViewById(R.id.chart);
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

        BarDataSet set1 = new BarDataSet(values, "");

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
        chart.setScaleEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return Months[(int) value];
            }
        });
        xAxis.setLabelCount(12);
        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        chart.animateY(1500);   // add a nice and smooth animation

        chart.getLegend().setEnabled(false);  //hide text under chart

    }
}

