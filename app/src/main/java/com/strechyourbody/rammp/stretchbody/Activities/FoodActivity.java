package com.strechyourbody.rammp.stretchbody.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.strechyourbody.rammp.stretchbody.Fragments.FoodListFragment;
import com.strechyourbody.rammp.stretchbody.R;



/**
 * Created by paulasegura on 2/8/17.
 */

public class FoodActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_content);
        setToolbar();
        setFragmentByDefault();

    }

    private void setFragmentByDefault(){
        getSupportFragmentManager().beginTransaction().replace(R.id.content_food,new FoodListFragment()).commit();
    }

    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Comidas");
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
