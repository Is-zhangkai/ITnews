package com.example.tools.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tools.Adapter.MixAdapter;
import com.example.tools.MyData;
import com.example.tools.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyCollections extends AppCompatActivity {

    private TextView head;
    private ImageView back;
    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private static int i = 0;
    private List<Map<String, Object>> list = new ArrayList<>();
    private int flag = 0;
    private String responseData = "";
    private String tp_url;
    private int type;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_my_collection);

        head = findViewById(R.id.chat_name);
        back =findViewById(R.id.chat_back);
        recyclerView = findViewById(R.id.recyclerview);
        refreshLayout = findViewById(R.id.new_srl1);


        Intent intent=getIntent();
        Bundle bd=intent.getExtras();
        String name=bd.getString("name");
        head.setText(name);

        type=bd.getInt("type");   // 1:collection 2:history  3:fans  4:attentions;

        switch (type){
            case 1: tp_url="star-news-ids";break;
            case 2: tp_url="history-news";break;
            case 3: tp_url="fans-ids";break;
            case 4: tp_url="followee-ids";break;
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);  //避免滑动卡顿
        list.clear();
        MyData myData = new MyData(MyCollections.this);
        token = myData.load_token();
        wzy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        i = 0;
        new Thread(() -> {
            try {
                Log.d("12333","here");
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                Request request = new Request.Builder()
                        .url("http://122.9.2.27/api/self/"+tp_url)
                        .method("GET", null)
                        .addHeader("Authorization",token)
                        .build();
                Response response = client.newCall(request).execute();
                responseData = response.body().string();
                getfeedback(responseData);
            } catch (IOException e) {
                list.clear();
                responseData="";
                flag = 1;
                Map map2 = new HashMap();
                map2.put("type", 0);
                list.add(map2);
                Log.d("1233", String.valueOf(map2));
                MyCollections.this.runOnUiThread(() -> {
                    recyclerView.setLayoutManager(new LinearLayoutManager(MyCollections.this));
                    recyclerView.setAdapter(new MixAdapter(MyCollections.this, list));
                });

                e.printStackTrace();
            }
        }).start();
    }

    public void wzy() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                onResume();
                refreshlayout.finishRefresh(1000/*,false*/);

            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                getfeedback(responseData);
                refreshlayout.finishLoadMore(200/*,false*/);//传入false表示加载失败
            }
        });

    }

    public void getfeedback(String responseData) {
//        if (responseData != "") {
//            try {
//                JSONObject jsonObject = new JSONObject(responseData);
//                if (jsonObject.getInt("code") == 200) {
//                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
//                    JSONArray jsonArray = jsonObject1.getJSONArray("goods");
//                    for (int j = 0; i < jsonArray.length() && j < 8; i++, j++) {
//                        Log.d("1233i", "1:" + i);
//                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
//                        int good_id = jsonObject2.getInt("good_id");
//                        int user_id = jsonObject2.getInt("user_id");
//                        int quantity = jsonObject2.getInt("quantity");
//                        long price = jsonObject2.getLong("price");
//                        String name = jsonObject2.getString("name");
//                        String info = jsonObject2.getString("info");
//                        String img = jsonObject2.getString("img");
//
//                        Map map = new HashMap();
//
//                        map.put("good_id", good_id);
//                        map.put("user_id", user_id);
//                        map.put("quantity", quantity);
//                        map.put("price", price);
//                        map.put("info", info);
//                        map.put("name", name);
//                        map.put("img", img);
//                        map.put("type", 1);
//                        list.add(map);
//
//                    }
//                    if (i == jsonArray.length()) {
//                        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(getActivity(), "到底了~", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//
//                }
//                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//                        recyclerView.setAdapter(new BuyAdapter(getActivity(), list));
//                    }
//                });
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
