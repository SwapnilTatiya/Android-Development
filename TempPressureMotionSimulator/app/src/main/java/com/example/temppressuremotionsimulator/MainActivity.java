package com.example.temppressuremotionsimulator;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private float TEMP_MAX ;
    //MAXIMUM TEMPERATURE READ FROM THE DATA
    private float TEMP_MIN ;
    //MINIMUM TEMPERATURE READ FROM THE DATA
    private float PRESSURE_MAX;
    //MAXIMUM PRESSURE READ FROM THE DATA
    private float PRESSURE_MIN;
    //MINIMUM PRESSURE READ FROM THE DATA
    private float ACCELERATE_X_MAX;
    //MAXIMUM ACCELERATION IN X DIRECTION READ FROM THE DATA
    private float ACCELERATE_X_MIN;
    //MINIMUM ACCELERATION IN X DIRECTION READ FROM THE DATA
    private float ACCELERATE_Y_MAX;
    //MAXIMUM ACCELERATION IN Y DIRECTION READ FROM THE DATA
    private float ACCELERATE_Y_MIN;
    //MINIMUM ACCELERATION IN Y DIRECTION READ FROM THE DATA
    private float ACCELERATE_Z_MAX;
    //MAXIMUM ACCELERATION IN Z DIRECTION READ FROM THE DATA
    private float ACCELERATE_Z_MIN;
    //MINIMUM ACCELERATION IN Z DIRECTION READ FROM THE DATA
    private float GYROSCOPE_X_MAX;
    //MAXIMUM GYROSCOPIC MEASUREMENT IN X DIRECTION READ FROM THE DATA
    private float GYROSCOPE_X_MIN;
    //MINIMUM GYROSCOPIC MEASUREMENT IN X DIRECTION READ FROM THE DATA
    private float GYROSCOPE_Y_MAX;
    //MAXIMUM GYROSCOPIC MEASUREMENT IN Y DIRECTION READ FROM THE DATA
    private float GYROSCOPE_Y_MIN;
    //MINIMUM GYROSCOPIC MEASUREMENT IN Y DIRECTION READ FROM THE DATA
    private float GYROSCOPE_Z_MAX;
    //MAXIMUM GYROSCOPIC MEASUREMENT IN Z DIRECTION READ FROM THE DATA
    private float GYROSCOPE_Z_MIN;
    //MINIMUM GYROSCOPIC MEASUREMENT IN Z DIRECTION READ FROM THE DATA
    private long DURATION;
    //The time duration Value is in minutes
    private long INTERVAL;
    //the time interval is in milliseconds

    //<<<TEXTVIEWS
    private TextView tempValue;
    private TextView pressureValue ;
    private TextView accxValue;
    private TextView accyValue;
    private TextView acczValue;
    private TextView gyroxValue;
    private TextView gyroyValue;
    private TextView gyrozValue;
    //TEXTVIEWS>>>

    //<<<BUTTONS
    private Button GenerateButton;
    //BUTTONS>>>

    float value;
    //variable which stores value of simulated data so that it can be first shown on the screen and then returned
    String floatValue;
    //variable which stores value of value in string for output

    //<<<FILES STORAGE RELATED
    File file;
    FileOutputStream fos;
    //FILES STORAGE RELATED>>>

    //SHARED PREFERENCES
    static SharedPreferences sharedPreferences;



    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tempValue = findViewById(R.id.TemperatureValue);
        pressureValue = findViewById(R.id.pressureValue);
        accxValue = findViewById(R.id.accx);
        accyValue = findViewById(R.id.accy);
        acczValue = findViewById(R.id.accz);
        gyroxValue = findViewById(R.id.gyrox);
        gyroyValue = findViewById(R.id.gyroy);
        gyrozValue = findViewById(R.id.gyroz);
        GenerateButton=findViewById(R.id.Generate);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Exit Setup");
                builder.setMessage("Are you sure you want to exit?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                });
                builder.setNegativeButton("NO", null);
                builder.show();
            }
        };
        MainActivity.this.getOnBackPressedDispatcher().addCallback(this, callback);

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            file = new File(getExternalFilesDir("XFinitoBioDesigns"), "testData.txt");
            try {
                fos=new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }


        //SHARED PREFERENCES:
       sharedPreferences=this.getSharedPreferences("com.example.temppressuremotionsimulator",MODE_PRIVATE);


    }

    private void retrieveData() {
        TEMP_MAX=sharedPreferences.getFloat("TEMP_MAX",0);
        PRESSURE_MAX=sharedPreferences.getFloat("PRESSURE_MAX",0);
        ACCELERATE_X_MAX=sharedPreferences.getFloat("ACCELERATION_X_MAX",0);
        ACCELERATE_Y_MAX=sharedPreferences.getFloat("ACCELERATION_Y_MAX",0);
        ACCELERATE_Z_MAX=sharedPreferences.getFloat("ACCELERATION_Z_MAX",0);
        GYROSCOPE_X_MAX=sharedPreferences.getFloat("GYROSCOPE_X_MAX",0);
        GYROSCOPE_Y_MAX=sharedPreferences.getFloat("GYROSCOPE_Y_MAX",0);
        GYROSCOPE_Z_MAX=sharedPreferences.getFloat("GYROSCOPE_Z_MAX",0);
        TEMP_MIN=sharedPreferences.getFloat("TEMP_MIN",0);
        PRESSURE_MIN=sharedPreferences.getFloat("PRESSURE_MIN",0);
        ACCELERATE_X_MIN=sharedPreferences.getFloat("ACCELERATION_X_MIN",0);
        ACCELERATE_Y_MIN=sharedPreferences.getFloat("ACCELERATION_Y_MIN",0);
        ACCELERATE_Z_MIN=sharedPreferences.getFloat("ACCELERATION_Z_MIN",0);
        GYROSCOPE_X_MIN=sharedPreferences.getFloat("GYROSCOPE_X_MIN",0);
        GYROSCOPE_Y_MIN=sharedPreferences.getFloat("GYROSCOPE_Y_MIN",0);
        GYROSCOPE_Z_MIN=sharedPreferences.getFloat("GYROSCOPE_Z_MIN",0);
        INTERVAL=sharedPreferences.getLong("INTERVAL",500);
        DURATION=sharedPreferences.getLong("DURATION",1);
    }
    public void call()
    {    String dataValues=tempSimulate()+","+
            pressureSimulate()+","+
            accelerate_xSimulate() +","+
            accelerate_ySimulate() +","+
            accelerate_zSimulate() +","+
            gyroscope_xSimulate() +","+
            gyroscope_ySimulate() +","+
            gyroscope_zSimulate();
        try {
            fos.write(dataValues.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
//<<<DATA SIMULATION HAPPENS HERE
    public float Simulator ( float MAX, float MIN)
    {
        float simulatedValue = (float) ((MAX - MIN) * Math.random() + MIN);
        return simulatedValue;
    }
    public float tempSimulate ()
    {
        value = Simulator(TEMP_MAX, TEMP_MIN);
        floatValue=String.valueOf(value);
        tempValue.setText("Temperature: "+floatValue);
        return value;
    }
    public float pressureSimulate ()
    {
        value = Simulator(PRESSURE_MAX, PRESSURE_MIN);
        floatValue=String.valueOf(value);
        pressureValue.setText("Pressure: "+floatValue);
        return value;
    }
    public float accelerate_xSimulate ()
    {
        value = Simulator(ACCELERATE_X_MAX, ACCELERATE_X_MIN);
        floatValue=String.valueOf(value);
        accxValue.setText("Acceleration_X: "+floatValue);
        return value;
    }
    public float accelerate_ySimulate ()
    {
        value = Simulator(ACCELERATE_Y_MAX, ACCELERATE_Y_MIN);
        floatValue=String.valueOf(value);
        accyValue.setText("Acceleration_Y: "+floatValue);
        return value;
    }
    public float accelerate_zSimulate ()
    {
        value = Simulator(ACCELERATE_Z_MAX, ACCELERATE_Z_MIN);
        floatValue=String.valueOf(value);
        acczValue.setText("Acceleration_Z: "+floatValue);
        return value;
    }
    public float gyroscope_xSimulate ()
    {
        value = Simulator(GYROSCOPE_X_MAX, GYROSCOPE_X_MIN);
        floatValue=String.valueOf(value);
        gyroxValue.setText("GYROSCOPE_X :"+floatValue);
        return value;
    }
    public float gyroscope_ySimulate ()
    {
        value = Simulator(GYROSCOPE_Y_MAX, GYROSCOPE_Y_MIN);
        floatValue=String.valueOf(value);
        gyroyValue.setText("GYROSCOPE_Y :"+floatValue);
        return value;
    }
    public float gyroscope_zSimulate ()
    {
        value = Simulator(GYROSCOPE_Z_MAX, GYROSCOPE_Z_MIN);
        floatValue=String.valueOf(value);
        gyrozValue.setText("GYROSCOPE_Z :"+floatValue);
        return value;
    }
    //DATA SIMULATION CODE ENDS HERE>>>

    //This method is on click for properties button
    public void PropertiesDisplay(View view) {
        retrieveData();
        float MAX_VALUES[]={TEMP_MAX,PRESSURE_MAX,ACCELERATE_X_MAX,ACCELERATE_Y_MAX,ACCELERATE_Z_MAX,GYROSCOPE_X_MAX,GYROSCOPE_Y_MAX,GYROSCOPE_Z_MAX};
        float MIN_VALUES[]={TEMP_MIN,PRESSURE_MIN,ACCELERATE_X_MIN,ACCELERATE_Y_MIN,ACCELERATE_Z_MIN,GYROSCOPE_X_MIN,GYROSCOPE_Y_MIN,GYROSCOPE_Z_MIN};
        Intent intent = new Intent(getApplicationContext(),propertiesActivity.class);
        intent.putExtra("MAX_VALUES",MAX_VALUES);
        intent.putExtra("MIN_VALUES",MIN_VALUES);
        intent.putExtra("INTERVAL",INTERVAL);
        intent.putExtra("TOTAL_TIME",DURATION);
        try{startActivity(intent,null);}
        catch (Exception e)
        {e.printStackTrace();}
    }


    //this method is on click for generate button
    public void GenerateClicked(View view) {
        retrieveData();
        Toast.makeText(this,file.getAbsolutePath(),Toast.LENGTH_LONG).show();

        GenerateButton.setEnabled(false);
        try {
            new CountDownTimer(TimeUnit.MINUTES.toMillis(DURATION), INTERVAL) {
                @Override
                public void onTick(long millisUntilFinished) {
                    call();
                }

                @Override
                public void onFinish() {
                        GenerateButton.setEnabled(true);
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), "GENERATION COMPLETE", Toast.LENGTH_LONG).show();
                }
            }.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            if(DURATION==0)
            {
               Toast.makeText(this, "DURATION CANT BE ZERO", Toast.LENGTH_SHORT).show();
            }
        }
    }
}