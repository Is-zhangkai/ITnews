package com.example.tools.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.tools.Adapter.CommentAdapter;
import com.example.tools.Data;
import com.example.tools.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class NewsDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SmartRefreshLayout smartRefreshLayout;
    private CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_news_details);
    
        recyclerView=this.findViewById(R.id.details_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(NewsDetailsActivity.this));
      //  List<Data> comments = new ArrayList<>();



        List<String> comments = new ArrayList<>();
        for (int i=0;i<14;i++){
            comments.add(i+"水电费不会吧");
        }
        commentAdapter = new CommentAdapter(NewsDetailsActivity.this, comments);
        recyclerView.setAdapter(commentAdapter);
        setHeaderView(recyclerView);
        setFooterView(recyclerView);
    }





    private void setHeaderView(RecyclerView view){
        View header = LayoutInflater.from(this).inflate(R.layout.comment_header, view, false);
        commentAdapter.setHeaderView(header);
    }

    private void setFooterView(RecyclerView view){
        View footer = LayoutInflater.from(this).inflate(R.layout.comment_footer, view, false);
        commentAdapter.setFooterView(footer);
    }
}