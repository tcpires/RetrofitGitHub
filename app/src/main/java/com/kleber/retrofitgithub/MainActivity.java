package com.kleber.retrofitgithub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kleber.retrofitgithub.adapters.GitHubApapter;
import com.kleber.retrofitgithub.models.Repository;
import com.kleber.retrofitgithub.services.GitHubEndpointInterface;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by kleber on 06/06/17.
 */
public class MainActivity extends AppCompatActivity {

    public static final String API_BASE_URL = "https://api.github.com";

    final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();

    final OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

    final Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson));

    final Retrofit retrofit = retrofitBuilder.client(okHttpClientBuilder.build()).build();

    final GitHubEndpointInterface gitHubEndpointInterface =  retrofit.create(GitHubEndpointInterface.class);

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private List<Repository> repositories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.adapter = new GitHubApapter(this.repositories);
        this.layoutManager = new LinearLayoutManager(this);

        this.recyclerView = (RecyclerView) findViewById(R.id.github_repos_recycler_view);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(this.layoutManager);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(this.adapter);

        // Fetch a list of the Github repositories.
        Call<List<Repository>> call = this.gitHubEndpointInterface.getUserRepos("klebertiko");

        // Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, final Response<List<Repository>> response) {
                // The network call was a success and we got a response
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        repositories.addAll(response.body());
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                // the network call was a failure
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
