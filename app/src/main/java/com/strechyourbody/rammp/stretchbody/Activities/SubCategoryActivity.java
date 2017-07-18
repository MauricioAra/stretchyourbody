package com.strechyourbody.rammp.stretchbody.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.strechyourbody.rammp.stretchbody.Adapters.SubCategoryAdapter;
import com.strechyourbody.rammp.stretchbody.Entities.Program;
import com.strechyourbody.rammp.stretchbody.Entities.SubCategory;
import com.strechyourbody.rammp.stretchbody.R;
import com.strechyourbody.rammp.stretchbody.Services.ProgramService;
import com.strechyourbody.rammp.stretchbody.Services.RetrofitCliente;
import com.strechyourbody.rammp.stretchbody.Services.SubCategoryService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SubCategoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private GridView gridViewSubcategory;
    private SubCategoryAdapter subCategoryAdapter;
    private String idCategory;
    private List<SubCategory> subCategoriesGlobal;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        idCategory = getIntent().getStringExtra("id");

        // Prgress
        progress = new ProgressDialog(SubCategoryActivity.this);
        progress.setTitle("Cargando");
        progress.setMessage("Obteniendo categorías...");
        progress.setCancelable(false);
        progress.setIndeterminate(true);
        progress.show();

        // Set Toolbar
        setToolbar();

        //
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = RetrofitCliente.getClient();
        Retrofit retrofit = builder.client(httpClient.build()).build();
        SubCategoryService subCategoryService =  retrofit.create(SubCategoryService.class);

        Call<List<SubCategory>> call = subCategoryService.listSubcategory(Integer.parseInt(idCategory));

        call.enqueue(new Callback<List<SubCategory>>() {
            @Override
            public void onResponse(Call<List<SubCategory>> call, Response<List<SubCategory>> response) {
                // The network call was a success and we got a response
                if(response != null){
                    subCategoriesGlobal = response.body();
                    buildList(response.body());
                    progress.dismiss();
                }
                // TODO: use the repository list and display it
            }

            @Override
            public void onFailure(Call<List<SubCategory>> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });
    }



    private void buildList(List<SubCategory> psubCategories){
        subCategoryAdapter = new SubCategoryAdapter(this, R.layout.grid_item_subcategory, psubCategories);
        gridViewSubcategory = (GridView) findViewById(R.id.grid_view_subcategory);
        gridViewSubcategory.setAdapter(subCategoryAdapter);
        gridViewSubcategory.setOnItemClickListener(this);
    }

    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Seleccione un tipo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.clickSubcategory(subCategoriesGlobal.get(position));
    }

    private void clickSubcategory(SubCategory subCategory) {
        Intent intent = new Intent(SubCategoryActivity.this,BodyPartActivity.class);
        intent.putExtra("id",subCategory.getId().toString());
        intent.putExtra("idCat",idCategory);
        startActivity(intent);
    }
}
