package com.example.tools.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tools.Fragment.MessageFragment;
import com.example.tools.Fragment.MyPaperFragment;
import com.example.tools.Fragment.NewsFragment;
import com.example.tools.Fragment.UserFragment;
import com.example.tools.SQLite.MyDatabaseHelper;
import com.example.tools.R;
import com.example.tools.Utils;
import com.example.tools.tools.BottomPopupOption;

import okhttp3.Response;
import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout news;
    private RelativeLayout paper;
    private RelativeLayout message;
    private RelativeLayout user;
    private ImageView mes;
    private NewsFragment newsFragment = new  NewsFragment();
    private MessageFragment messageFragment= new MessageFragment();
    private MyPaperFragment myPaperFragment=new MyPaperFragment();
    private UserFragment userFragment =new UserFragment();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mes=findViewById(R.id.message);
        news=findViewById(R.id.news_layout);
        paper=findViewById(R.id.paper_layout);
        message=findViewById(R.id.message_layout);
        user=findViewById(R.id.user_layout);
        news.setSelected(true);
        QBadgeView qBadgeView =new QBadgeView(this);
        qBadgeView.bindTarget(message);
        qBadgeView.setBadgeNumber(100);
        qBadgeView.setGravityOffset(10,3,true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, newsFragment).commit();
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                news.setSelected(true);
                paper.setSelected(false);
                message.setSelected(false);
                user.setSelected(false);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, newsFragment).commit();
            }
        });
        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                news.setSelected(false);
                paper.setSelected(true);
                message.setSelected(false);
                user.setSelected(false);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, myPaperFragment).commit();
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                news.setSelected(false);
                paper.setSelected(false);
                message.setSelected(true);
                user.setSelected(false);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, messageFragment).commit();
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                news.setSelected(false);
                paper.setSelected(false);
                message.setSelected(false);
                user.setSelected(true);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, userFragment).commit();
            }
        });









    }

}