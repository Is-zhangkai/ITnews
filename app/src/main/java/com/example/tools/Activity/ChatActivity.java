package com.example.tools.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tools.Adapter.ChatAdapter;
import com.example.tools.R;
import com.example.tools.SQLite.myApplication;
import com.example.tools.SQLite.operation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private ImageView chat_back;
    private TextView chat_name;
    List<Map<String, Object>> list=new ArrayList<>();
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent2=getIntent();
        Bundle bd=intent2.getExtras();
        String name=bd.getString("name");
        int n=bd.getInt("num");
        chat_back=findViewById(R.id.chat_back);
        chat_name=findViewById(R.id.chat_name);
        chat_name.setText(name);
        DbManager dbManager= null;
        try {
            dbManager = x.getDb(((myApplication)getApplicationContext()).getDaoConfig());
            List<operation> operations=new ArrayList<>();
            operations=dbManager.selector(operation.class).orderBy("id",true).limit(1000).where("type","=",n).findAll();
            dbManager.update(operation.class, WhereBuilder.b("type","=",n),new KeyValue("read",0));
            for(int i=0;i<=operations.size();i++)
            {
                Map<String,Object> map=new HashMap<>();
                map.put("date",operations.get(i).getDate());
                map.put("title",operations.get(i).getTitle());
                map.put("type",n);
                list.add(map);
                chatAdapter=new ChatAdapter(ChatActivity.this,list);
                recyclerView.setAdapter(chatAdapter);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        chat_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }
}