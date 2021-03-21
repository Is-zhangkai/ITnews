package com.example.tools.Activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tools.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity {
    private ImageView chat_back;
    private TextView chat_name;

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
        chat_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}