package com.strechyourbody.rammp.stretchbody.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.NotificationService;
import com.strechyourbody.rammp.stretchbody.Services.ProgramAlarmService;

public class ProgramCalendar extends AppCompatActivity {

    AlarmManager alarmManager;
    TimePicker timePicker;
    DatePicker datePicker;
    Button start;
    Button stop;
    Calendar calendar;
    TextView status;
    String idProgram;
    String nameProgram;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_calendar);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        timePicker = (TimePicker) findViewById(R.id.timePicker);

        idProgram = getIntent().getStringExtra("idProgram");
        nameProgram = getIntent().getStringExtra("nameProgram");

        start = (Button) findViewById(R.id.btn_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCalendar();
            }
        });
        setToolbar();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void saveCalendar(){

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        //calendar.add(Calendar.YEAR,2017);

        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();

        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.clear();


        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);

        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);

        Intent intent = new Intent(getApplicationContext() , ProgramAlarmService.class);
        Bundle b = new Bundle();
        b.putString("idProgram",idProgram);
        b.putString("nameProgram",nameProgram);
        intent.putExtras(b);

        PendingIntent broadcast = PendingIntent.getBroadcast(this,0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),broadcast);

        Intent program = new Intent(ProgramCalendar.this,ProgramActivity.class);
        startActivity(program);
        Toast.makeText(ProgramCalendar.this,"Se calendarizó el programa",Toast.LENGTH_SHORT).show();
    }

    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Calendarizar rutina");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}




// alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//
//         timePicker = (TimePicker) findViewById(R.id.timePicker);
//         start = (Button) findViewById(R.id.btn_start);
//         stop = (Button) findViewById(R.id.btn_end);
//         status = (TextView) findViewById(R.id.satus);
//
//         calendar = Calendar.getInstance();
//
//
//         start.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
//        calendar.set(Calendar.MINUTE,timePicker.getMinute());
//        status.setText("On");
//        }
//        });
//
//        stop.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//        status.setText("Off");
//        }
//        });