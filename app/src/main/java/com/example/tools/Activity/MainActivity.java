package com.example.tools.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tools.Fragment.MessageFragment;
import com.example.tools.Fragment.MyPaperFragment;
import com.example.tools.Fragment.NewsFragment;
import com.example.tools.Fragment.UserFragment;
import com.example.tools.R;
import com.example.tools.SQLite.MessageDate;

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
    protected void onStart() {
        super.onStart();
//        try {
//            DbManager dbManager= x.getDb(((myApplication)getApplicationContext()).getDaoConfig());
//            List<operation> operations=new ArrayList<>();
//            operations=dbManager.selector(operation.class).orderBy("id",true).limit(1000).where("read","=",1).findAll();
//            Log.d("12333",""+operations.size());
//            messageDate.setAll_msg(operations.size());
//
//            List<operation> operations1=new ArrayList<>();
//            operations1=dbManager.selector(operation.class).orderBy("id",true).limit(1000).where("type","=",2).where("read","=",1).findAll();
//            messageDate.setLike_msg(operations1.size());
//            List<operation> operations2=new ArrayList<>();
//            operations2=dbManager.selector(operation.class).orderBy("id",true).limit(1000).where("type","=",3).where("read","=",1).findAll();
//            messageDate.setCollect_msg(operations2.size());
//            List<operation> operations3=new ArrayList<>();
//            operations3=dbManager.selector(operation.class).orderBy("id",true).limit(1000).where("type","=",4).where("read","=",1).findAll();
//            messageDate.setComment_msg(operations3.size());
//            List<operation> operations4=new ArrayList<>();
//            operations4=dbManager.selector(operation.class).orderBy("id",true).limit(1000).where("type","=",5).where("read","=",1).findAll();
//            messageDate.setFocus_msg(operations4.size());
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
        QBadgeView qBadgeView =new QBadgeView(this);
        qBadgeView.bindTarget(message);
        qBadgeView.setBadgeNumber(messageDate.getAll_msg());
        qBadgeView.setGravityOffset(10,3,true);
    }
}