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
import com.example.tools.SQLite.MessageDate;
import com.example.tools.SQLite.MyDatabaseHelper;
import com.example.tools.R;
import com.example.tools.SQLite.myApplication;
import com.example.tools.SQLite.operation;
import com.example.tools.Utils;
import com.example.tools.tools.BottomPopupOption;

import org.xutils.BuildConfig;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;
import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends AppCompatActivity {
    public MessageDate messageDate=new MessageDate(this);
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

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("test","MainonResume");
        try {
            DbManager dbManager = x.getDb(((myApplication) getApplicationContext()).getDaoConfig());
            List<operation> operations = new ArrayList<>();
            operations = dbManager.selector(operation.class).orderBy("id", true).limit(1000).where("read", "=", 1).findAll();
            if (operations == null) {
                messageDate.setAll_msg(0);
            } else {
                messageDate.setAll_msg(operations.size());
                Log.i("test",String.valueOf(messageDate.getAll_msg()));
            }
            List<operation> operations1 = new ArrayList<>();
            operations1 = dbManager.selector(operation.class).orderBy("id", true).limit(1000).where("type", "=", 2).and("read", "=", 1).findAll();
            if (operations1 == null) {
                messageDate.setLike_msg(0);
            } else {
                messageDate.setLike_msg(operations1.size());

            }
            List<operation> operations2 = new ArrayList<>();
            operations2 = dbManager.selector(operation.class).orderBy("id", true).limit(1000).where("type", "=", 3).and("read", "=", 1).findAll();
            if (operations2 == null) {
                messageDate.setCollect_msg(0);
            } else {
                messageDate.setCollect_msg(operations2.size());

            }
            List<operation> operations3 = new ArrayList<>();
            operations3 = dbManager.selector(operation.class).orderBy("id", true).limit(1000).where("type", "=", 4).and("read", "=", 1).findAll();
            if (operations3 == null) {
                messageDate.setComment_msg(0);
            } else {
                messageDate.setComment_msg(operations3.size());
            }
            List<operation> operations4 = new ArrayList<>();
            operations4 = dbManager.selector(operation.class).orderBy("id", true).limit(1000).where("type", "=", 5).and("read", "=", 1).findAll();
            if (operations4 == null) {
                messageDate.setFocus_msg(0);
            } else {
                messageDate.setFocus_msg(operations4.size());
                ;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        QBadgeView qBadgeView = new QBadgeView(this);
        qBadgeView.bindTarget(message);
        Log.i("test",String.valueOf(messageDate.getAll_msg()));
        qBadgeView.setBadgeNumber(messageDate.getAll_msg());
        qBadgeView.setGravityOffset(10, 3, true);
        Log.i("test",String.valueOf(messageDate.getAll_msg()));
    }
}