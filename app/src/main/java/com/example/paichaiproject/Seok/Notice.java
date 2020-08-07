package com.example.paichaiproject.Seok;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.paichaiproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Notice extends AppCompatActivity {
    private ListView listView;
    private PostListAdapter adapter;
    private List<PostList> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Notice.this, Post.class);
                startActivity(intent);
                finish();
            }
        });
        Intent intent = getIntent();

        listView=(ListView)findViewById(R.id.listView);
        postList = new ArrayList<PostList>();

        adapter = new PostListAdapter(getApplicationContext(),postList);
        listView.setAdapter(adapter);


        new BackgroundTask().execute();

    }


    //데이터베이스 게시판용
    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;
        @Override
        protected void onPreExecute(){
            target="https://sjs4209.cafe24.com/List.php";
        }
        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url=new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        public void onProgressUpdate(Void... valuse){
            super.onProgressUpdate(valuse);
        }
        @Override
        public void onPostExecute(String result){
            try{
                JSONObject jsonObject=new JSONObject(result);
                JSONArray jsonArray=jsonObject.getJSONArray("response");
                int count=0;
                String postContent;
                while(count<jsonArray.length())
                {
                    JSONObject object=jsonArray.getJSONObject(count);
                    postContent=object.getString("post");
                    PostList post= new PostList(postContent);
                    postList.add(post);
                    adapter.notifyDataSetChanged();
                    count++;

                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }


}
