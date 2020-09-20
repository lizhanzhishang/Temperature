package com.example.plicationjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    TemperatureView temperatureview;
    TemperatureNCView newcl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temperatureview = findViewById(R.id.temperatureview);
        newcl = findViewById(R.id.newcl);
    }

    public void onZhileng(View view) {
        temperatureview.setModelLHF(ModelHLF.ZHILENG);
    }

    public void onZhiRe(View view) {
        temperatureview.setModelLHF(ModelHLF.ZHIRE);
    }

    public void onFeng(View view) {
        temperatureview.setModelLHF(ModelHLF.SONGFENG);
    }

    public void on1(View view) {
        newcl.setModelSpeed(ModelSpeed.LOW);
        temperatureview.setModelSpeed(ModelSpeed.LOW);
    }

    public void on2(View view) {
        newcl.setModelSpeed(ModelSpeed.Mid);
        temperatureview.setModelSpeed(ModelSpeed.Mid);
    }

    public void on3(View view) {
        newcl.setModelSpeed(ModelSpeed.Quick);
        temperatureview.setModelSpeed(ModelSpeed.Quick);
    }

    public void onAdd(View view) {
        temperatureview. addAirTip();
    }

    public void onSub(View view) {
        temperatureview. subAirTip();
    }
}
