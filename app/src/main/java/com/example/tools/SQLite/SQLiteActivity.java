package com.example.tools.SQLite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tools.R;
import com.facebook.stetho.Stetho;

public class SQLiteActivity extends AppCompatActivity {
    //数据库文件名称
    private static final String DATABASE_NAME = "BookStore.db";
    private static final int DATABASE_VERSION = 3;
    private MyDatabaseHelper mMyDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sq);
        Stetho.initializeWithDefaults(this);

        final EditText et1=findViewById(R.id.et1);
        final EditText et21=findViewById(R.id.et21);
        final EditText et22=findViewById(R.id.et22);
        final EditText et3=findViewById(R.id.et3);



        //创建数据库
        mMyDatabaseHelper = new MyDatabaseHelper(this, DATABASE_NAME, null, DATABASE_VERSION);
        mMyDatabaseHelper.getReadableDatabase();



        //向数据库插入数据
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=et1.getText().toString();
                if (name.equals("")) {
                    Toast.makeText(SQLiteActivity.this, "插入内容为空", Toast.LENGTH_SHORT).show();
                }else {
                final SQLiteDatabase db = mMyDatabaseHelper.getReadableDatabase();
                final ContentValues values = new ContentValues();
                values.clear();
                values.put("name", name);
                values.put("author", "Dan Brown");
                values.put("pages", 454);
                values.put("qwq", 16.96);
                db.insert("Book", null, values);
                et1.setText(null);}
            }
        });

//更新数据库的数据


     findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String name1=et21.getText().toString();
             String name2=et22.getText().toString();
             if (name1.equals("")||name2.equals("")) {
                 Toast.makeText(SQLiteActivity.this, "替换内容为空", Toast.LENGTH_SHORT).show();
             }else {
             final SQLiteDatabase db = mMyDatabaseHelper.getReadableDatabase();
             final ContentValues values = new ContentValues();

             values.clear();

             values.put("name", name2);
             db.update("Book",values, "name= ?", new String[]{name1});
             et21.setText(null);et22.setText(null);
             }
         }
     });


        //删除数据库的数据

        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=et3.getText().toString();
                if (name.equals("")) {
                    Toast.makeText(SQLiteActivity.this, "删除内容为空", Toast.LENGTH_SHORT).show();
                }else {
                final SQLiteDatabase db = mMyDatabaseHelper.getReadableDatabase();

                db.delete("Book", "name =?", new String[]{name});
                et3.setText(null);
               }
            }
        });





//        //对数据库表进行查询，会返回游标
//        Cursor cursor = db.query("Book", null, null, null, null, null, null);
//        while (cursor.moveToNext()){
//            String name = cursor.getString(cursor.getColumnIndex("name"));
//            String author = cursor.getString(cursor.getColumnIndex("author"));
//            int pages = cursor.getInt(cursor.getColumnIndex("pages"));
//            double price = cursor.getDouble(cursor.getColumnIndex("price"));
//            Log.d("Query BookStore.db", name);
//            Log.d("Query BookStore.db", author);
//            Log.d("Query BookStore.db", pages+"");
//            Log.d("Query BookStore.db", price+"");
//        }
//        cursor.close();

    }
}