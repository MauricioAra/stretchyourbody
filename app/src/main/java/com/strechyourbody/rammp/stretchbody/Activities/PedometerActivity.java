package com.strechyourbody.rammp.stretchbody.Activities;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.strechyourbody.rammp.stretchbody.Entities.SimpleStepDetector;
import com.strechyourbody.rammp.stretchbody.Entities.StepListener;
import com.strechyourbody.rammp.stretchbody.R;

public class PedometerActivity extends Activity implements SensorEventListener, StepListener {
    private TextView textView;
    private SimpleStepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private int numSteps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setToolbar();
//
        setContentView(R.layout.activity_pedometer);
        textView = (TextView) findViewById(R.id.tv_steps);

        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new SimpleStepDetector();
        simpleStepDetector.registerListener(this);
    }



    @Override
    public void onResume() {
        super.onResume();

        textView.setText(numSteps + " ");
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        textView.setText(numSteps + " ");
    }

//    private void setToolbar(){
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Contador de pasos");
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//    }

}
