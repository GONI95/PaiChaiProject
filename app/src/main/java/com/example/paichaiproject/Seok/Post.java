package com.example.paichaiproject.Seok;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.paichaiproject.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Post extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        final EditText contentText=(EditText)findViewById(R.id.content);

        Button postButton=(Button)findViewById(R.id.post);

        postButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String postContent = contentText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Post.this);
                                builder.setMessage("글이 등록되었습니다.")
                                        .setPositiveButton("확인",new DialogInterface.OnClickListener(){
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(Post.this, Notice.class);
                                                Post.this.startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .create()
                                        .show();
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(Post.this);
                                builder.setMessage("글이 등록되지 않습니다.")
                                        .setNegativeButton("다시시도", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                //PostRequest 클래스의 객체 생성 후 기능추가
                PostRequest postRequest=new PostRequest(postContent,responseListener);
                RequestQueue queue= Volley.newRequestQueue(Post.this);
                queue.add(postRequest);
            }
        });

    }
    public void cancelclick(View v){
        Intent intent = new Intent(Post.this, Notice.class);
        Post.this.startActivity(intent);
        finish();
    }

}
