package com.example.tools.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.icu.text.DateTimePatternGenerator;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.example.tools.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
        final CheckBox checkBox=findViewById(R.id.checkbox);//复选框








        //用户服务协议
        findViewById(R.id.agreement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog dialog = new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("用户服务协议:")
                        .setMessage("1.学科限定输入格式为2—9个汉字+0或1个数字1-4，请用户自觉输入。\n" +
                                "2.学分输入限定为小于等于10，允许输入两位小数。\n" +
                                "3.成绩限定输入100及以内非负整数。")
                        .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                checkBox.setChecked(true);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("不同意", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                checkBox.setChecked(false);
                                dialog.dismiss();
                            }
                        })
                        .create();
                dialog.show();

         }
        });

    }
}