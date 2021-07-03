package com.example.temppressuremotionsimulator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class propertiesActivity extends AppCompatActivity {
    //<<<VARIABLES DECLARATION AS NAME SUGGESTS
float TEMPERATURE_MAX;
float PRESSURE_MAX;
float ACCELERATIONX_MAX;
float ACCELERATIONY_MAX;
float ACCELERATIONZ_MAX;
float GYROSCOPEX_MAX;
float GYROSCOPEY_MAX;
float GYROSCOPEZ_MAX;
float TEMPERATURE_MIN;
float PRESSURE_MIN;
float ACCELERATIONX_MIN;
float ACCELERATIONY_MIN;
float ACCELERATIONZ_MIN;
float GYROSCOPEX_MIN;
float GYROSCOPEY_MIN;
float GYROSCOPEZ_MIN;
long INTERVAL;
long DURATION;
//VARIABLE DECLARATION ENDS HERE>>>
    //<<<EDIT TEXTS DECLARATION BEGINS HERE
     EditText tempMax;
     EditText pressureMax;
     EditText accxMax;
     EditText accyMax;
     EditText acczMax;
     EditText gyroxMax;
     EditText gyroyMax;
     EditText gyrozMax;
     EditText tempMin;
     EditText pressureMin;
     EditText accxMin;
     EditText accyMin;
     EditText acczMin;
     EditText gyroxMin;
     EditText gyroyMin;
     EditText gyrozMin;
     EditText interval;
     EditText duration;
     //EDIT TEXT DECLARATION ENDS HERE>>>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties);
         tempMax=findViewById(R.id.tempMaxInput);
         pressureMax=findViewById(R.id.pressureMaxInput);
         accxMax=findViewById(R.id.accxMaxInput);
         accyMax=findViewById(R.id.accyMaxInput);
         acczMax=findViewById(R.id.acczMaxInput);
         gyroxMax=findViewById(R.id.gyroxMaxInput);
         gyroyMax=findViewById(R.id.gyroyMaxInput);
         gyrozMax=findViewById(R.id.gyrozMaxInput);
         tempMin=findViewById(R.id.tempMinInput);
         pressureMin=findViewById(R.id.pressureMinInput);
         accxMin=findViewById(R.id.accxMinInput);
         accyMin=findViewById(R.id.accyMinInput);
         acczMin=findViewById(R.id.acczMinInput);
         gyroxMin=findViewById(R.id.gyroxMinInput);
         gyroyMin=findViewById(R.id.gyroyMinInput);
         gyrozMin=findViewById(R.id.gyrozMinInput);
         interval=findViewById(R.id.intervalInput);
         duration=findViewById(R.id.durationInput);
        //to get data from Main activity Intent->
        cameFromIntent();
        //to set data from previous MainActivity->
        setupEditTextViews();

     }

    private void setupEditTextViews() {
        tempMax.setText(String.valueOf(TEMPERATURE_MAX));
        pressureMax.setText(String.valueOf(PRESSURE_MAX));
        accxMax.setText(String.valueOf(ACCELERATIONX_MAX));
        accyMax.setText(String.valueOf(ACCELERATIONY_MAX));
        acczMax.setText(String.valueOf(ACCELERATIONZ_MAX));
        gyroxMax.setText(String.valueOf(GYROSCOPEX_MAX));
        gyroyMax.setText(String.valueOf(GYROSCOPEY_MAX));
        gyrozMax.setText(String.valueOf(GYROSCOPEZ_MAX));
        tempMin.setText(String.valueOf(TEMPERATURE_MIN));
        pressureMin.setText(String.valueOf(PRESSURE_MIN));
        accxMin.setText(String.valueOf(ACCELERATIONX_MIN));
        accyMin.setText(String.valueOf(ACCELERATIONY_MIN));
        acczMin.setText(String.valueOf(ACCELERATIONZ_MIN));
        gyroxMin.setText(String.valueOf(GYROSCOPEX_MIN));
        gyroyMin.setText(String.valueOf(GYROSCOPEY_MIN));
        gyrozMin.setText(String.valueOf(GYROSCOPEZ_MIN));
        interval.setText(String.valueOf(INTERVAL));
        duration.setText(String.valueOf(DURATION));
    }

    private void cameFromIntent() {
        Intent intent=getIntent();
        float MAX_VALUES[]=intent.getFloatArrayExtra("MAX_VALUES");
        float MIN_VALUES[]=intent.getFloatArrayExtra("MIN_VALUES");
         TEMPERATURE_MAX=MAX_VALUES[0];
         PRESSURE_MAX=MAX_VALUES[1];
         ACCELERATIONX_MAX=MAX_VALUES[2];
         ACCELERATIONY_MAX=MAX_VALUES[3];
         ACCELERATIONZ_MAX=MAX_VALUES[4];
         GYROSCOPEX_MAX=MAX_VALUES[5];
         GYROSCOPEY_MAX=MAX_VALUES[6];
         GYROSCOPEZ_MAX=MAX_VALUES[7];
         TEMPERATURE_MIN=MIN_VALUES[0];
         PRESSURE_MIN=MIN_VALUES[1];
         ACCELERATIONX_MIN=MIN_VALUES[2];
         ACCELERATIONY_MIN=MIN_VALUES[3];
         ACCELERATIONZ_MIN=MIN_VALUES[4];
         GYROSCOPEX_MIN=MIN_VALUES[5];
         GYROSCOPEY_MIN=MIN_VALUES[6];
         GYROSCOPEZ_MIN=MIN_VALUES[7];
         INTERVAL = intent.getLongExtra("INTERVAL",500);
         DURATION=intent.getLongExtra("DURATION",1);
    }

    public void SaveClicked(View view) {
        MainActivity.sharedPreferences.edit().putFloat("TEMP_MAX",Float.parseFloat(tempMax.getText().toString())).apply();
        MainActivity.sharedPreferences.edit().putFloat("PRESSURE_MAX",Float.parseFloat(pressureMax.getText().toString())).apply();
        MainActivity.sharedPreferences.edit().putFloat("ACCELERATION_X_MAX",Float.parseFloat(accxMax.getText().toString())).apply();
        MainActivity.sharedPreferences.edit().putFloat("ACCELERATION_Y_MAX",Float.parseFloat(accyMax.getText().toString())).apply();
        MainActivity.sharedPreferences.edit().putFloat("ACCELERATION_Z_MAX",Float.parseFloat(acczMax.getText().toString())).apply();
        MainActivity.sharedPreferences.edit().putFloat("GYROSCOPE_X_MAX",Float.parseFloat(gyroxMax.getText().toString())).apply();
        MainActivity.sharedPreferences.edit().putFloat("GYROSCOPE_Y_MAX",Float.parseFloat(gyroyMax.getText().toString())).apply();
        MainActivity.sharedPreferences.edit().putFloat("GYROSCOPE_Z_MAX",Float.parseFloat(gyrozMax.getText().toString())).apply();
        MainActivity.sharedPreferences.edit().putFloat("TEMP_MIN",Float.parseFloat(tempMin.getText().toString())).apply();
        MainActivity.sharedPreferences.edit().putFloat("PRESSURE_MIN",Float.parseFloat(pressureMin.getText().toString())).apply();
        MainActivity.sharedPreferences.edit().putFloat("ACCELERATION_X_MIN",Float.parseFloat(accxMin.getText().toString())).apply();
        MainActivity.sharedPreferences.edit().putFloat("ACCELERATION_Y_MIN",Float.parseFloat(accyMin.getText().toString())).apply();
        MainActivity.sharedPreferences.edit().putFloat("ACCELERATION_Z_MIN",Float.parseFloat(acczMin.getText().toString())).apply();
        MainActivity.sharedPreferences.edit().putFloat("GYROSCOPE_X_MIN",Float.parseFloat(gyroxMin.getText().toString())).apply();
        MainActivity.sharedPreferences.edit().putFloat("GYROSCOPE_Y_MIN",Float.parseFloat(gyroyMin.getText().toString())).apply();
        MainActivity.sharedPreferences.edit().putFloat("GYROSCOPE_Z_MIN",Float.parseFloat(gyrozMin.getText().toString())).apply();
        MainActivity.sharedPreferences.edit().putLong("INTERVAL",Long.parseLong(interval.getText().toString())).apply();
        MainActivity.sharedPreferences.edit().putLong("DURATION",Long.parseLong(duration.getText().toString())).apply();
        Toast.makeText(this, "Data saved SUCCESSFULLY!", Toast.LENGTH_SHORT).show();
        propertiesActivity.this.finish();
    }
}
    