package com.example.tools.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
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
    private String new_version ;
    private String downloadUrl;
    private Button upgrade;
    String title = "发现新版本（其实是旧版本）：";
    String size = "新版本大小：21MB";
    String msg = "1、优化api接口。\r\n2、添加使用demo演示。\r\n3、新增自定义更新服务API接口。\r\n4、优化更新提示界面。";
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
//        HideKeyboard();
        go_update();
    }
    public void HideKeyboard(View v)
    {
        InputMethodManager imm = ( InputMethodManager ) v.getContext( ).getSystemService( MainActivity.this.INPUT_METHOD_SERVICE );
        if ( imm.isActive( ) ) {
            imm.hideSoftInputFromWindow( v.getApplicationWindowToken( ) , 0 );

        }
    }
    private void go_update(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                Request request = new Request.Builder()
                        .url("http://122.9.2.27/api/appinfo/latest-version")
                        .method("GET", null)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("wsz",responseData);
                    JSONObject jsonObject1 = new JSONObject(responseData);
                    int code = jsonObject1.getInt("code");
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("data");
                    new_version = jsonObject2.getString("version");
                    downloadUrl = jsonObject2.getString("url");
                    Log.d("wsz",new_version);
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            go_dh();
                        }
                    });
                } catch (IOException | JSONException e) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "请检测网络连接！", Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void go_dh(){
        MyData myData = new MyData(MainActivity.this);
        String version = myData.load_v();
        if (!version.equals(new_version)) {
            title = "发现新版本：" + new_version;
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            View view = inflater.inflate(R.layout.new_update_layout, null);
            AlertDialog.Builder mDialog = new AlertDialog.Builder(MainActivity.this, R.style.Translucent_NoTitle);
            mDialog.setView(view);
            mDialog.setCancelable(true);
            Log.d("123300", "!!!!!!!!");
            upgrade = view.findViewById(R.id.button);
            TextView textView1 = view.findViewById(R.id.textView1);
            TextView textView2 = view.findViewById(R.id.textView2);
            TextView textView3 = view.findViewById(R.id.textView3);
            ImageView iv_close = view.findViewById(R.id.iv_close);
            textView1.setText(title);
            textView2.setText(size);
            textView3.setText(msg);
            upgrade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(downloadUrl);    //设置跳转的网站
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });

            final AlertDialog dialog = mDialog.show();
            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }else{
        }
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