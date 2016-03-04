package com.example.bobbie.basicassignmentthiefalarm;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private Boolean activated;
    private Sensor mAccelerometer;
    private SensorManager mSensorManager;
    private RadioButton alarmRBtn;
    private Button alarmBtn;
    private RelativeLayout rLayout;
    private boolean alarm = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rLayout = (RelativeLayout) findViewById(R.id.rl);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        alarmBtn = (Button) findViewById(R.id.buttonActivate);
        alarmRBtn = (RadioButton) findViewById(R.id.radioButtonradioButtonActivate);
        activated = false;

        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toogleSensor();
            }
        });

    }

    private void toogleSensor(){

        if(activated){
            mSensorManager.unregisterListener(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                alarmRBtn.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#ff0400")));

            }

        }
        else{
          //  mLayout.setBackgroundColor(Color.WHITE);
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                alarmRBtn.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#00ff04")));
                if(rLayout != null) rLayout.setBackgroundColor(Color.WHITE);
            }
        }
        activated = !activated;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        TextView tvx = (TextView)findViewById(R.id.textViewX);
        TextView tvy = (TextView)findViewById(R.id.textViewY);
        TextView tvz = (TextView)findViewById(R.id.textViewZ);
        TextView tvxsum = (TextView)findViewById(R.id.textViewXSum);
        TextView tvysum = (TextView)findViewById(R.id.textViewYSum);
        TextView tvzsum = (TextView)findViewById(R.id.textViewZSum);

        tvx.setText("" + event.values[0]);
        tvy.setText("" + event.values[1]);
        tvz.setText("" + event.values[2]);

        try{
            tvxsum.setText(event.values[0] + Float.parseFloat("" + tvxsum.getText()) + "");
            tvysum.setText(event.values[1] + Float.parseFloat("" + tvysum.getText()) + "");
            tvzsum.setText(event.values[2] + Float.parseFloat("" + tvzsum.getText()) + "");
        }
        catch (Exception e){
            tvxsum.setText("0");
            tvysum.setText("0");
            tvzsum.setText("0");
        }

        if(Math.abs(event.values[0]) + Math.abs(event.values[1]) + Math.abs(event.values[2]) >= 3) {
            toogleSensor();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if(rLayout != null) rLayout.setBackgroundColor(Color.RED);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
