package com.example.tools.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.example.tools.R;

import java.io.File;
import java.io.IOException;

public class WriteActivity extends AppCompatActivity {
    private Button back;
    private Button post;
    private Button edit_tag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        edit_tag=findViewById(R.id.edit_tag);
        edit_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tag[]=new String[]{"        游戏","        体育","        汽车","        军事"};
                AlertDialog.Builder builder =new AlertDialog.Builder(WriteActivity.this);
                builder.setIcon(R.drawable.ic_launcher_foreground);
                builder.setTitle("选择新闻标签:");
                builder.setItems(tag, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0)
                            edit_tag.setText("游戏");
                        else if (which==1)
                            edit_tag.setText("体育");
                        else if (which==2)
                            edit_tag.setText("汽车");
                        else
                            edit_tag.setText("军事");
                    }
                });
                builder.create().show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}