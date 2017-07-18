package com.strechyourbody.rammp.stretchbody.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.strechyourbody.rammp.stretchbody.Adapters.BodyPartAdapter;
import com.strechyourbody.rammp.stretchbody.Adapters.SubCategoryAdapter;
import com.strechyourbody.rammp.stretchbody.Entities.BodyPart;
import com.strechyourbody.rammp.stretchbody.Entities.SubCategory;
import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.BodyPartService;
import com.strechyourbody.rammp.stretchbody.Services.RetrofitCliente;
import com.strechyourbody.rammp.stretchbody.Services.SubCategoryService;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BodyPartActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private GridView gridViewBodyPart;
    private String idSubCategory;
    private String idCategory;
    private BodyPartAdapter bodyPartAdapter;
    private List<BodyPart> globalBodyParts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_part);
        idSubCategory = getIntent().getStringExtra("id");
        idCategory = getIntent().getStringExtra("idCat");
        //Toast.makeText(BodyPartActivity.this,idSubCategory,Toast.LENGTH_SHORT).show();
        setToolbar();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = RetrofitCliente.getClient();
        Retrofit retrofit = builder.client(httpClient.build()).build();
        BodyPartService bodyPartService =  retrofit.create(BodyPartService.class);


        Call<List<BodyPart>> call = bodyPartService.listBodyPart(Integer.parseInt(idSubCategory));


        call.enqueue(new Callback<List<BodyPart>>() {
            @Override
            public void onResponse(Call<List<BodyPart>> call, Response<List<BodyPart>> response) {
                // The network call was a success and we got a response
                if(response != null){
                    globalBodyParts = response.body();
                    buildGrid(response.body());
                    //progressDialog.dismiss();
                }
                // TODO: use the repository list and display it
            }

            @Override
            public void onFailure(Call<List<BodyPart>> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(BodyPartActivity.this,SubCategoryActivity.class);
                intent.putExtra("id",idCategory);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void buildGrid(List<BodyPart> pbodyParts){
        bodyPartAdapter = new BodyPartAdapter(this, R.layout.grid_item_bodypart, pbodyParts);
        gridViewBodyPart = (GridView) findViewById(R.id.grid_view_body);
        gridViewBodyPart.setAdapter(bodyPartAdapter);
        gridViewBodyPart.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.clickBodyPart(globalBodyParts.get(position));
    }

    private void clickBodyPart(BodyPart bodyPart) {
        Intent intent = new Intent(BodyPartActivity.this,ExerciseActivity.class);
        intent.putExtra("idBody",bodyPart.getId().toString());
        intent.putExtra("idSub",idSubCategory);
        startActivity(intent);
    }

    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Seleccione una parte");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}