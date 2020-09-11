package nageswar.pythonanywhere.com.me.nageswar.graphs;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class BarActivity extends AppCompatActivity {

    BarChart barChart;

    ArrayList<BarEntry> barEntries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        barChart = findViewById(R.id.bargraph);
        barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(2000,45));
        barEntries.add(new BarEntry(2001,35));
        barEntries.add(new BarEntry(2002,25));
        barEntries.add(new BarEntry(2003,85));
        barEntries.add(new BarEntry(2004,95));
        barEntries.add(new BarEntry(2005,45));

        BarDataSet dataSet = new BarDataSet(barEntries,"Student");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(20f);

        BarData barData = new BarData(dataSet);

        barChart.setData(barData);
        barChart.animateY(2000);
    }
}