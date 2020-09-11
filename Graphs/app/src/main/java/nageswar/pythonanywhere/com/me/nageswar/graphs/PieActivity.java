package nageswar.pythonanywhere.com.me.nageswar.graphs;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PieActivity extends AppCompatActivity {

    PieChart pieChart;

    ArrayList pieEntries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie);
        pieChart = findViewById(R.id.piechart);
        pieEntries = new ArrayList();

        pieEntries.add(new PieEntry(100,"2016"));
        pieEntries.add(new PieEntry(150,"2017"));
        pieEntries.add(new PieEntry(200,"2018"));
        pieEntries.add(new PieEntry(300,"2019"));
        pieEntries.add(new PieEntry(450,"2020"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries,"Faculty");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextSize(16f);
        pieDataSet.setValueTextColor(Color.CYAN);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.animate();
    }
}