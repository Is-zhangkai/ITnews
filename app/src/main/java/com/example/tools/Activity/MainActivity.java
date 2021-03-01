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
import android.widget.TextView;
import android.widget.Toast;

import com.example.tools.SQLite.MyDatabaseHelper;
import com.example.tools.R;
import com.example.tools.Utils;
import com.example.tools.tools.BottomPopupOption;

import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView=findViewById(R.id.text);
        Button button1=findViewById(R.id.button1);
        final Button button2=findViewById(R.id.button2);
        final Button button3=findViewById(R.id.button3);

////////////////////////////////////
// get接口测试
button2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Utils.get("https://news-at.zhihu.com/api/3/stories/latest", new Utils.OkhttpCallBack() {

            @Override
            public void onSuccess(Response response) {
                Log.i("asd","success!!!!!!!!!");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"成功get",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFail(String error) {
                Log.i("asd","error!!!!!!!!!!!!");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"失败get",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
});


///////////////////////////////////////
//底部弹出框测试
button3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final BottomPopupOption bottomPopupOption = new BottomPopupOption(MainActivity.this);
        bottomPopupOption.setItemText("按钮1", "按钮2","按钮3");
        bottomPopupOption.showPopupWindow();
        bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
            @Override
            public void onItemClick(int position) {

                if (position==0){
                    Toast.makeText(MainActivity.this,"我是按钮1",Toast.LENGTH_SHORT).show();
                    bottomPopupOption.dismiss();
                }else if (position==1){
                    Toast.makeText(MainActivity.this,"我是按钮2",Toast.LENGTH_SHORT).show();
                    bottomPopupOption.dismiss();
                }else {
                    Toast.makeText(MainActivity.this,"我是按钮3",Toast.LENGTH_SHORT).show();
                    bottomPopupOption.dismiss();
                }
            }
        });
    }
});





    }

}