package com.example.tools.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tools.R;

public class MyCollections extends AppCompatActivity {

    private TextView head;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_my_collection);

        head = findViewById(R.id.chat_name);
        back =findViewById(R.id.chat_back);

        Intent intent=getIntent();
        Bundle bd=intent.getExtras();
        String name=bd.getString("name");
        head.setText(name);
        int type=bd.getInt("type");   // 1:collection 2:history  3:fans  4:attentions;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }

}
