package com.strechyourbody.rammp.stretchbody.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.SessionManager;

public class CategoryActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RelativeLayout office;
    private RelativeLayout air;
    private Button btn_office;
    private  Button btn_air;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        setToolbar();
        session = new SessionManager(CategoryActivity.this);

        office = (RelativeLayout) findViewById(R.id.office);
        air = (RelativeLayout) findViewById(R.id.air);
        btn_office = (Button) findViewById(R.id.btn_office);
        btn_air = (Button) findViewById(R.id.btn_air);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navview);


        office.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this,SubCategoryActivity.class);
                intent.putExtra("id","1");
                startActivity(intent);
            }
        });

        btn_office.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this,SubCategoryActivity.class);
                intent.putExtra("id","1");
                startActivity(intent);
            }
        });

        air.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this,SubCategoryActivity.class);
                intent.putExtra("id","2");
                startActivity(intent);
            }
        });

        btn_air.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this,SubCategoryActivity.class);
                intent.putExtra("id","2");
                startActivity(intent);
            }
        });



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                boolean fragmentTransaction = false;
                Fragment fragment = null;

                switch (item.getItemId()){

                    case R.id.menu_my_program:
                        Intent intent = new Intent(CategoryActivity.this, ProgramActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menu_dash_board:
                        Intent intentMain = new Intent(CategoryActivity.this, MainActivity.class);
                        startActivity(intentMain);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menu_my_profile:
                        Intent profile = new Intent(CategoryActivity.this,ProfileUserActivity.class);
                        startActivity(profile);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menu_start_exercise:
                        Intent category = new Intent(CategoryActivity.this,CategoryActivity.class);
                        startActivity(category);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menu_start_food:
                        Intent food = new Intent(CategoryActivity.this,FoodActivity.class);
                        startActivity(food);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.menu_favorite:
                        Intent favorite = new Intent(CategoryActivity.this, FavoriteExcercisesActivity.class);
                        startActivity(favorite);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.log_out:
                        session.logOut();
                        break;
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
        getSupportActionBar().setTitle("Seleccione un área");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_dark_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
}