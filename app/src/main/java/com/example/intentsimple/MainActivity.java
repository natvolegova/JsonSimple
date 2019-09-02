package com.example.intentsimple;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView txt_result;
    private JSONPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_result = findViewById(R.id.txt_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JSONPlaceHolderApi.class);
        getPostsOfUser(new Integer[]{2, 3, 4},"id", "desc");
    }

    private void getComments(int id) {
        Call<List<Comment>> comments = jsonPlaceHolderApi.getPostComments2(id);
        comments.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    txt_result.setText("Code" + response.code());
                    return;
                }
                List<Comment> comments = response.body();
                String content = "";
                for (Comment comment : comments) {
                    content += comment.getPostId()+"\n"+comment.getName() + "\n" + comment.getBody() + "\n\n";
                }
                txt_result.setText(content);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                txt_result.setText(t.getMessage());
            }
        });
    }

    private void getPostsOfUser(Integer[] userId, String sort, String order) {
        Call<List<Post>> call = jsonPlaceHolderApi.getPostsOfUser(userId, sort, order);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    txt_result.setText("Code" + response.code());
                    return;
                }
                List<Post> posts = response.body();
                String content = "";
                for (Post post : posts) {
                    content += post.getTitle() + "\n" + post.getBody() + "\n\n";
                }
                txt_result.setText(content);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                txt_result.setText(t.getMessage());
            }
        });
    }

}
