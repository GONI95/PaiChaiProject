package com.example.paichaiproject.Seok;

import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PostRequest extends StringRequest {

    final static private String URL="https://sjs4209.cafe24.com/Post.php";
    private Map<String,String> parameters;

    public PostRequest(String postContent, Response.Listener<String> listener){
        //나중에 이거 가능하면 추가 String userID
        super(Method.POST,URL,listener,null);

        parameters=new HashMap<>();
        //parameters.put("userID",userID);
        parameters.put("post",postContent);
    }

    @Override
    public Map<String,String> getParams(){
        return parameters;
    }

}
