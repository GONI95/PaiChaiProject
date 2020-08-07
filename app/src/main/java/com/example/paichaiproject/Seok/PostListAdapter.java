package com.example.paichaiproject.Seok;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.paichaiproject.R;
import com.example.paichaiproject.Seok.PostList;

import java.util.List;

public class PostListAdapter extends BaseAdapter {

    private Context context;
    private List<PostList> postList;

    public PostListAdapter(Context context, List<PostList> postList){
        this.context = context;
        this.postList = postList;
    }

    @Override
    public int getCount() {
        return postList.size();
    }

    @Override
    public Object getItem(int i) {
        return postList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.post, null);
        TextView postContent=(TextView)v.findViewById(R.id.postContent);

        postContent.setText(postList.get(i).getPostContent());

        v.setTag(postList.get(i).getPostContent());
        return v;
    }
}