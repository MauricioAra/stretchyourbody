package com.strechyourbody.rammp.stretchbody.Activities;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.strechyourbody.rammp.stretchbody.Fragments.DashBoardFragment;
import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.NotificationReciever;
import com.strechyourbody.rammp.stretchbody.Services.NotificationService;
import com.strechyourbody.rammp.stretchbody.Services.SessionManager;

public class MainActivity extends AppCompatActivity implements DashBoardFragment.OnFragmentAddProgramListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private SessionManager session;
    private long notificationDelay;
    private int NOTIFICATION_ID = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notificationDelay = AlarmManager.INTERVAL_DAY * 7;
        scheduleNotification(MainActivity.this, notificationDelay, NOTIFICATION_ID);
        notificationExample();

        session = new SessionManager(MainActivity.this);
        if (!session.isLoggedIn()) {
            session.logOut();
            return;
        } else {
            setContentView(R.layout.activity_main);
            setToolbar();

            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            navigationView = (NavigationView) findViewById(R.id.navview);
            setFragmentByDefault();

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent supercat = new Intent(MainActivity.this,CategoryActivity.class);
                    startActivity(supercat);
                }
            });
        }


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                boolean fragmentTransaction = false;
                Fragment fragment = null;

                if (session.isLoggedIn()) {

                switch (item.getItemId()){

                    case R.id.menu_my_program:
                        Intent intent = new Intent(MainActivity.this, ProgramActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menu_dash_board:
                        fragment = new DashBoardFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.menu_my_profile:
                        Intent profile = new Intent(MainActivity.this,ProfileUserActivity.class);
                        startActivity(profile);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.menu_start_exercise:
                        Intent category = new Intent(MainActivity.this,CategoryActivity.class);
                        startActivity(category);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.menu_start_food:
                        Intent food = new Intent(MainActivity.this,FoodActivity.class);
                        startActivity(food);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.menu_favorite:
                        Intent favorite = new Intent(MainActivity.this,FavoriteExcercisesActivity.class);
                        startActivity(favorite);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.menu_maletares:
                        Intent malestares = new Intent(MainActivity.this,MalestarActivity.class);
                        startActivity(malestares);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.log_out:
                        session.logOut();
                        break;
                }

                } else {
                    session.logOut();
                }

                if(fragmentTransaction) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                    item.setChecked(true);
                    getSupportActionBar().setTitle(item.getTitle());
                    drawerLayout.closeDrawers();
                }

                return true;
            }
        });
    }

    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setFragmentByDefault(){
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new DashBoardFragment()).commit();
        MenuItem item = navigationView.getMenu().getItem(0);
        item.setChecked(true);
        getSupportActionBar().setTitle(item.getTitle());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                // abrir el menu lateral
                drawerLayout.openDrawer(GravityCompat.START);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnRegresar() {
        Log.d("TAG", "Holis");
    }

    //example of notification usage
    //should be erased later
    public void notificationExample() {
        Intent intent = new Intent(getApplicationContext() , NotificationService.class);
        Bundle b = new Bundle();
        b.putString("title", "Stretch your body!"); //titulo de la notificacion
        b.putString("content", "Notificacion!"); //mensaje de la notificacion
        intent.putExtras(b);
        PendingIntent pendingIntent  = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE); //Crea una nueva notificacion despues de un tiempo
        am.set(AlarmManager.RTC_WAKEUP, 1000 , pendingIntent); //tiempo
    }

    //Sends user notification if app has not been used after 1 week
    public void scheduleNotification(Context context, long delay, int notificationId) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle("Stretch your body!")
                .setContentText("Acurdate de hacer ejercicio!")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.red_heart);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(activity);

        Notification notification = builder.build();

        Intent notificationIntent = new Intent(context, NotificationReciever.class);
        notificationIntent.putExtra(NotificationReciever.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(NotificationReciever.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, delay, pendingIntent);
    }
}
