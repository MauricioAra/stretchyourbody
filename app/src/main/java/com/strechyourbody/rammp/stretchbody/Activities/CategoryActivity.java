package com.strechyourbody.rammp.stretchbody.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.strechyourbody.rammp.stretchbody.R;

public class CategoryActivity extends AppCompatActivity {
    private RelativeLayout office;
    private RelativeLayout air;
    private Button btn_office;
    private  Button btn_air;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        setToolbar();

        office = (RelativeLayout) findViewById(R.id.office);
        air = (RelativeLayout) findViewById(R.id.air);
        btn_office = (Button) findViewById(R.id.btn_office);
        btn_air = (Button) findViewById(R.id.btn_air);


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


    }

    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Seleccione un área");

        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}