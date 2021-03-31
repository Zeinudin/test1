package com.rajendra.onlineLeraning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.rajendra.courseapp.R;
import com.rajendra.onlineLeraning.adapter.CategoryAdapter;
import com.rajendra.onlineLeraning.model.Category;
import com.rajendra.onlineLeraning.retrofit.ApiInterface;
import com.rajendra.onlineLeraning.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView categoryRecyclerView;
    CategoryAdapter categoryAdapter;
    Button button;
    Button buttonsave;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        button = (Button)findViewById(R.id.video_button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), Video_music.class);
//                startActivity(intent);
//            }
//        });

        buttonsave = (Button)findViewById(R.id.button2);
        buttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMore();
            }
        });


        apiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);
        categoryRecyclerView = findViewById(R.id.course_recycler);

        Call<List<Category>> call = apiInterface.getAllCategory();

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {

                List<Category> categoryList = response.body();

                getAllCategory(categoryList);

                //lets run it
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "No response from server", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void openMore() {
        Intent intent = new Intent(getApplicationContext(), More.class);
        startActivity(intent);
    }


    private void getAllCategory(List<Category> categoryList) {

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, 1);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(this, categoryList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

    }
}
