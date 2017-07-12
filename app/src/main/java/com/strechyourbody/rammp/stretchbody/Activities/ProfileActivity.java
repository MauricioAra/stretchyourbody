package com.strechyourbody.rammp.stretchbody.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.strechyourbody.rammp.stretchbody.Fragments.ProfileFragment;
import com.strechyourbody.rammp.stretchbody.Fragments.ProgramListFragment;
import com.strechyourbody.rammp.stretchbody.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setToolbar();
        setFragmentByDefault();
    }

    private void setFragmentByDefault(){
        getSupportFragmentManager().beginTransaction().replace(R.id.content_profile,new ProfileFragment()).commit();
    }

    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mi perfil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
