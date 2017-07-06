package com.strechyourbody.rammp.stretchbody.Activities;

import android.content.ContentProviderOperation;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.strechyourbody.rammp.stretchbody.Fragments.ProgramListFragment;
import com.strechyourbody.rammp.stretchbody.R;




public class ProgramActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);
        setToolbar();
        setFragmentByDefault();

    }

    private void setFragmentByDefault(){
        getSupportFragmentManager().beginTransaction().replace(R.id.content_programs,new ProgramListFragment()).commit();
    }

    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Programas");
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
