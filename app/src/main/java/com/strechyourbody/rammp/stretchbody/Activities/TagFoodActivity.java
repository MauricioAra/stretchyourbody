package com.strechyourbody.rammp.stretchbody.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;

import com.strechyourbody.rammp.stretchbody.Adapters.BodyPartAdapter;
import com.strechyourbody.rammp.stretchbody.Adapters.FoodTagAdapter;
import com.strechyourbody.rammp.stretchbody.Entities.FoodTag;
import com.strechyourbody.rammp.stretchbody.R;

import java.util.ArrayList;
import java.util.List;

public class TagFoodActivity extends AppCompatActivity {
    private GridView gridViewTagFood;
    private FoodTagAdapter foodTagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_food);
        setToolbar();
        buildGrid();
    }

    private void buildGrid(){

        List<FoodTag> foodTags = new ArrayList<>();
        foodTags.add(new FoodTag(1,"Rápido"));
        foodTags.add(new FoodTag(1,"Fácil"));
        foodTags.add(new FoodTag(1,"Desayuno"));
        foodTags.add(new FoodTag(1,"Ecnómico"));
        foodTags.add(new FoodTag(1,"Energético"));
        foodTags.add(new FoodTag(1,"Gluten free"));
        foodTags.add(new FoodTag(1,"No sugar"));
        foodTags.add(new FoodTag(1,"Vegetariano"));


        foodTagAdapter = new FoodTagAdapter(this, R.layout.list_tag_food_item, foodTags);
        gridViewTagFood = (GridView) findViewById(R.id.grid_view_tag);
        gridViewTagFood.setAdapter(foodTagAdapter);
        //gridViewTagFood.setOnItemClickListener(this);
    }
    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Selecciona los tags");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
