package com.nanang.sensorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

import com.nanang.sensorapp.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    ActivityMainBinding binding;
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private Sensor sensorProximity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuilder sensorTx = new StringBuilder();
        for (Sensor sensor: sensorList){
            sensorTx.append(sensor.getName()+"\n");
        }

        if (sensorAccelerometer==null){
            Toast.makeText(this, "No Accelerometer", Toast.LENGTH_SHORT).show();
        }

        if (sensorProximity==null){
            Toast.makeText(this, "No Accelerometer", Toast.LENGTH_SHORT).show();
        }

        binding.sensorList.setText(sensorTx.toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sensorAccelerometer!= null){
            sensorManager.registerListener(this, sensorAccelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorProximity!= null){
            sensorManager.registerListener(this, sensorProximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();
        float value = sensorEvent.values[0];
        if (sensorType == Sensor.TYPE_ACCELEROMETER){
            binding.sensorAccelometer.setText(String.format("Accelerometer sensor: %1$.2f", value));
        } else if (sensorType == Sensor.TYPE_PROXIMITY){
            binding.sensorProximity.setText(String.format("Proximity sensor: %1$.2f", value));
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}