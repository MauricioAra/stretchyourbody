package com.strechyourbody.rammp.stretchbody.Activities;

import android.content.ContentProviderOperation;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.strechyourbody.rammp.stretchbody.Fragments.ProgramListFragment;
import com.strechyourbody.rammp.stretchbody.R;




public class ProgramActivity extends AppCompatActivity {
    private FloatingActionButton fab_add_program;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);
        setToolbar();
        setFragmentByDefault();

        fab_add_program = (FloatingActionButton) findViewById(R.id.fab_add_program);

        fab_add_program.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProgramActivity.this,AddProgramActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setFragmentByDefault(){
        getSupportFragmentManager().beginTransaction().replace(R.id.content_programs,new ProgramListFragment()).commit();
    }

    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mis programas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
