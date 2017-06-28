package com.example.apostolos.newsapp;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewsAdapter.NewsAdapterOnClickHandler {
    private ProgressBar progress;
    private EditText search;
    private TextView textView;
    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private ArrayList<NewsItems> articlesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress =(ProgressBar) findViewById(R.id.progressBar);



        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_news);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mNewsAdapter = new NewsAdapter(this);
        mRecyclerView.setAdapter(mNewsAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public void onClick(String url) {

        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemNumber = item.getItemId();

        if(itemNumber==R.id.search){

            NetworkTask task = new NetworkTask();
            task.execute();
        }
        return true;
    }
    class NetworkTask extends AsyncTask<URL, Void, ArrayList<NewsItems>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);

        }

        @Override
        protected ArrayList<NewsItems> doInBackground(URL... params) {
            String jsonResults;
            ArrayList<NewsItems> results = null;
            try {
                jsonResults = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.makeURL());
                results = NetworkUtils.parseJSON(jsonResults);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return results;
        }


        @Override
        protected void onPostExecute(final ArrayList<NewsItems> data) {

            if(data != null){
                mNewsAdapter.setNewsData(data);
            }
            else{
                Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
            progress.setVisibility(View.GONE);
        }
    }

}



