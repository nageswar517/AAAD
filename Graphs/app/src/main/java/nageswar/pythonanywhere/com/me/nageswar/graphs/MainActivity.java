package nageswar.pythonanywhere.com.me.nageswar.graphs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void barGraph(View view) {
        startActivity(new Intent(this,BarActivity.class));
    }

    public void pieChart(View view) {
        startActivity(new Intent(this, PieActivity.class));
    }
}