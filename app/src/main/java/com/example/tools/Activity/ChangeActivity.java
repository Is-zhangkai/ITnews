package com.example.tools.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.tools.MyData;
import com.example.tools.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangeActivity extends AppCompatActivity {
    private ImageView change_pic;
    private TextView change_name;
    private ImageView back;
    private ConstraintLayout change_password;
    private ConstraintLayout info;
    private TextView tv_sex;
    private TextView tv_info;
    private boolean sex;
    private RadioGroup rgSex;
    private boolean sex2;
    private EditText edit;
    private ConstraintLayout change_sex;
    private String my_info;
    private String my_name;
    private int my_sex;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_information);


        change_sex = findViewById(R.id.ConstraintLayout3);
        info = findViewById(R.id.ConstraintLayout4);
        change_name = findViewById(R.id.textView17);
        back = findViewById(R.id.imageView4);
        change_pic = findViewById(R.id.imageView5);
        change_password = findViewById(R.id.ConstraintLayout5);
        tv_info =findViewById(R.id.textView20);
        tv_sex =findViewById(R.id.textView18);

        MyData myData = new MyData(ChangeActivity.this);
        my_info=myData.load_info();
        my_name=myData.load_name();
        my_sex=myData.load_sex();
        token=myData.load_token();

        go_ui();
        go_pic();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        change_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(ChangeActivity.this);
                final View view = factory.inflate(R.layout.layout_sex, null);
                rgSex = (RadioGroup) view.findViewById(R.id.rgSex);

                new AlertDialog.Builder(ChangeActivity.this)
                        .setTitle("请选择新的性别吧~")//提示框标题
                        .setView(view)
                        .setPositiveButton("确定",//提示框的两个按钮
                                new android.content.DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (rgSex.getCheckedRadioButtonId() == R.id.radio0) {
                                            sex2 = true;
                                            my_sex = 0;
                                        } else if (rgSex.getCheckedRadioButtonId() == R.id.radio1) {
                                            sex2 = false;
                                            my_sex = 1;
                                        } else {
                                            return;
                                        }
                                    }
                                }).setNegativeButton("取消", null).create().show();
                change_my_info();
            }
        });
        change_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(ChangeActivity.this);
                final View view = factory.inflate(R.layout.layout_name, null);
                edit = view.findViewById(R.id.name);

                new AlertDialog.Builder(ChangeActivity.this)
                        .setTitle("请输入新的昵称")
                        .setView(view)
                        .setPositiveButton("确定",
                                new android.content.DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        //事件
                                        if (edit.getText().toString().length() == 0) {
                                            Toast.makeText(ChangeActivity.this, "昵称不能为空！", Toast.LENGTH_SHORT).show();
                                            return;
                                        } else if (edit.getText().toString().length() > 8) {
                                            Toast.makeText(ChangeActivity.this, "昵称最多输入8个字符！", Toast.LENGTH_SHORT).show();
                                            return;
                                        } else {
                                            my_name=edit.getText().toString();
                                            change_my_info();
                                        }
                                    }
                                }).setNegativeButton("取消", null).create().show();
            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChangeActivity.this,ChangePasswordActivity.class));
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(ChangeActivity.this);
                final View view = factory.inflate(R.layout.layout_info, null);
                edit = view.findViewById(R.id.name);

                new AlertDialog.Builder(ChangeActivity.this)
                        .setTitle("请输入新的签名")
                        .setView(view)
                        .setPositiveButton("确定",
                                new android.content.DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
    //事件
                                        if (edit.getText().toString().length() == 0) {
                                            Toast.makeText(ChangeActivity.this, "不能为空！", Toast.LENGTH_SHORT).show();
                                            return;
                                        } else {
                                            my_info=edit.getText().toString();
                                            change_my_info();
                                        }
                                    }
                                }).setNegativeButton("取消", null).create().show();
            }
        });
    }
    void change_my_info(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
                String requestBody =  "{\n    \"info\": \""+my_info+"\",\n    \"nickname\": \""+my_name+"\",\n    \"gender\": "+my_sex+"\n}";
                Request request = new Request.Builder()
                        .url("http://122.9.2.27/api/self/info-refresh'")
                        .post(RequestBody.create(mediaType, requestBody))
                        .addHeader("Authorization",token)
                        .build();
                OkHttpClient okHttpClient = new OkHttpClient();
                Log.d("1233c",request.toString()+"   "+requestBody);
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("1233c", "onFailure: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("1233c", response.protocol() + " " + response.code() + " " + response.message());
                        Headers headers = response.headers();
                        String responseData = response.body().string();
                        try {
                            JSONObject jsonObject1 = new JSONObject(responseData);
                            int code = jsonObject1.getInt("code");
                            final String msg = jsonObject1.getString("msg");
                            if (code != 1000) {
                                go_ui();
                            }else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < headers.size(); i++) {
                            Log.d("1233", headers.name(i) + ":" + headers.value(i));
                        }
                        Log.d("1233", "onResponse: " + response.body());
                    }
                });
            }
        }).start();
    }
    void go_ui(){
        if(my_sex==0){
            tv_sex.setText("男");
        }else {
            tv_sex.setText("女");
        }
        change_name.setText(my_name);
        tv_info.setText(my_info);

    }
    void go_pic(){

    }
    void change_my_pic(){

    }
}
