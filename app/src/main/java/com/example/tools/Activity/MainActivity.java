package com.example.tools.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.tools.Fragment.MessageFragment;
import com.example.tools.Fragment.MyPaperFragment;
import com.example.tools.Fragment.NewsFragment;
import com.example.tools.Fragment.UserFragment;
import com.example.tools.MyData;
import com.example.tools.R;
import com.example.tools.SQLite.MessageDate;
import com.example.tools.SQLite.myApplication;
import com.example.tools.SQLite.operation;
import com.example.tools.Utils;
import com.facebook.stetho.Stetho;

import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    public MessageDate messageDate = new MessageDate(this);
    private RelativeLayout news;
    private RelativeLayout paper;
    private RelativeLayout message;
    private RelativeLayout user;
    private TextView red_all;
    private String email;
    private ImageView mes;
    private NewsFragment newsFragment = new NewsFragment();
    private MessageFragment messageFragment = new MessageFragment();
    private MyPaperFragment myPaperFragment = new MyPaperFragment();
    private UserFragment userFragment = new UserFragment();
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
    public void setRedNumber(TextView textView, int num)
    {
        if(num==0)
        {
            textView.setVisibility(View.GONE);
        }
        else if(num>0&&num<=99)
        {
            textView.setVisibility(View.VISIBLE);
            textView.setText(String.valueOf(num));
        }
        else
        {
            textView.setVisibility(View.VISIBLE);
            textView.setText("99+");
        }
    }
        private void hideFragment(FragmentTransaction transaction){
            if(newsFragment != null){
                transaction.hide(newsFragment);
            }
            if(myPaperFragment != null){
                transaction.hide(myPaperFragment);
            }
            if(messageFragment != null){
                transaction.hide(messageFragment);
            }
            if(userFragment != null){
                transaction.hide(userFragment);
            }
        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyData data=new MyData(MainActivity.this);
        email=data.load_email();
        String token =data.load_token();
        Stetho.initializeWithDefaults(this);
        red_all=findViewById(R.id.red_all);
        mes = findViewById(R.id.message);
        news = findViewById(R.id.news_layout);
        paper = findViewById(R.id.paper_layout);
        message = findViewById(R.id.message_layout);
        user = findViewById(R.id.user_layout);
        news.setSelected(true);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(!newsFragment.isAdded())
        {
            transaction.add(R.id.fragment,newsFragment).show(newsFragment).commit();
        }


        try {
            Utils.get_token("http://122.9.2.27/api/self/info", token, new Utils.OkhttpCallBack() {
                @Override
                public void onSuccess(Response response) {
                    try {
                        JSONObject jsonObject=new JSONObject(response.body().string());
                        String msg=jsonObject.getString("msg");
                        JSONObject jsonObject1=jsonObject.getJSONObject("data");
                        Log.i("asd",jsonObject1.toString());
                        data.save_name(jsonObject1.getString("nickname"));
                        data.save_pic_url(jsonObject1.getString("avatar"));
                        data.save_info(jsonObject1.getString("info"));
                        data.save_id(jsonObject1.getInt("selfid"));

//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (msg.equals("一切正常")){
//
//                                }else {
//
//                                }}
//                        });



                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFail(String error) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }



        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                news.setSelected(true);
                paper.setSelected(false);
                message.setSelected(false);
                user.setSelected(false);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if(!newsFragment.isAdded())
                {
                    transaction.add(R.id.fragment,newsFragment);
                }
                hideFragment(transaction);
                transaction.show(newsFragment);
                transaction.commit();
            }
        });
        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                news.setSelected(false);
                paper.setSelected(true);
                message.setSelected(false);
                user.setSelected(false);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if(!myPaperFragment.isAdded())
                {
                    transaction.add(R.id.fragment,myPaperFragment);
                }
                hideFragment(transaction);
                transaction.show(myPaperFragment);
                transaction.commit();
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                news.setSelected(false);
                paper.setSelected(false);
                message.setSelected(true);
                user.setSelected(false);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if(!messageFragment.isAdded())
                {
                    transaction.add(R.id.fragment,messageFragment);
                }
                hideFragment(transaction);
                transaction.show(messageFragment);
                transaction.commit();
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                news.setSelected(false);
                paper.setSelected(false);
                message.setSelected(false);
                user.setSelected(true);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if(!userFragment.isAdded())
                {
                    transaction.add(R.id.fragment,userFragment);
                }
                hideFragment(transaction);
                transaction.show(userFragment);
                transaction.commit();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("test", "MainonResume");
        try {
            DbManager dbManager = x.getDb(((myApplication) getApplicationContext()).getDaoConfig());
            List<operation> operations = new ArrayList<>();
            operations = dbManager.selector(operation.class).orderBy("id", true).limit(1000).where("read", "=", 1).and("email","=",email).findAll();
            if (operations == null) {
                messageDate.setAll_msg(0);
            } else {
                messageDate.setAll_msg(operations.size());
                Log.i("test", String.valueOf(messageDate.getAll_msg()));
            }
            List<operation> operations1 = new ArrayList<>();
            operations1 = dbManager.selector(operation.class).orderBy("id", true).limit(1000).where("type", "=", 2).and("read", "=", 1).and("email","=",email).findAll();
            if (operations1 == null) {
                messageDate.setLike_msg(0);
            } else {
                messageDate.setLike_msg(operations1.size());

            }
            List<operation> operations2 = new ArrayList<>();
            operations2 = dbManager.selector(operation.class).orderBy("id", true).limit(1000).where("type", "=", 3).and("read", "=", 1).and("email","=",email).findAll();
            if (operations2 == null) {
                messageDate.setCollect_msg(0);
            } else {
                messageDate.setCollect_msg(operations2.size());

            }
            List<operation> operations3 = new ArrayList<>();
            operations3 = dbManager.selector(operation.class).orderBy("id", true).limit(1000).where("type", "=", 4).and("read", "=", 1).and("email","=",email).findAll();
            if (operations3 == null) {
                messageDate.setComment_msg(0);
            } else {
                messageDate.setComment_msg(operations3.size());
            }
            List<operation> operations4 = new ArrayList<>();
            operations4 = dbManager.selector(operation.class).orderBy("id", true).limit(1000).where("type", "=", 5).and("read", "=", 1).and("email","=",email).findAll();
            if (operations4 == null) {
                messageDate.setFocus_msg(0);
            } else {
                messageDate.setFocus_msg(operations4.size());

            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        setRedNumber(red_all,messageDate.getAll_msg());
    }
}