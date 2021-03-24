package com.example.tools.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tools.Adapter.NewsAdapter;
import com.example.tools.MyData;
import com.example.tools.R;
import com.example.tools.Utils;
import com.example.tools.tools.Data;
import com.google.gson.JsonObject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Response;


public class RecommendFragment extends Fragment {
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private SmartRefreshLayout smartRefreshLayout;
   // private MyData data=new MyData(getActivity());
    private String token;
    private int page=1,size=4,o_page;
    private Boolean refresh=true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        smartRefreshLayout= view.findViewById(R.id.new_srl1);
        recyclerView = view.findViewById(R.id.new_recy1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

      //  token=data.load_token();
token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MTY2MzQxNTEsImlhdCI6MTYxNjU0Nzc1MSwiaXNzIjoicnVhIiwiZGF0YSI6eyJ1c2VyaWQiOjR9fQ.pj755t5OURu1Q95PMUnW1QyOWRvxBcjTzMNl1oP6irM";

//刷新加载
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refresh=false;
                List<Data> list=new ArrayList<>();
                GetNews(list,refresh);
                refreshLayout.finishLoadMore();
            }
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refresh=true;
                List<Data> list=new ArrayList<>();
                GetData(list);
                refreshLayout.finishRefresh();
            }
        });

//新闻列表
        List<Data> list=new ArrayList<>();
        GetData(list);

    }


    //获取轮播图及新闻
    public void GetData(final List<Data> list){
//轮播图
        Utils.get("http://122.9.2.27/api/get-img-lunbo", new Utils.OkhttpCallBack() {
            @Override
            public void onSuccess(Response response) {

                try {
//                    JSONObject jsonObject1=new JSONObject(Objects.requireNonNull(response.body()).string());
//                    JSONObject jsonObject2=jsonObject1.getJSONObject("data");
//                    final JSONArray jsonArray=jsonObject2.getJSONArray("pics");
//                    List<String> img=new ArrayList<>();
//                    for (int i=0;i<jsonArray.length();i++){
//                        img.add(jsonArray.getString(i));
//                    }
                    final Data data=new Data();
                    //data.setPics(img);
                    List<String> list1=new ArrayList<>();
                    // 4 1 2 3 4 1
                    list1.add( "https://pic3.zhimg.com/v2-a1019116672185fdfc7616fc6432f8f7.jpg?source=8673f162");
                    list1.add("https://pic4.zhimg.com/v2-f684b055b954c7f3e25572c3ddda65b2.jpg?source=8673f162");
                    list1.add( "https://pic4.zhimg.com/v2-99b0bec360093b88f30d59bde9327f94.jpg?source=8673f162");
                    list1.add("https://pic1.zhimg.com/v2-f028176a557874d28d5cabe118415497.jpg?source=8673f162");
                    list1.add( "https://pic3.zhimg.com/v2-a1019116672185fdfc7616fc6432f8f7.jpg?source=8673f162");
                    list1.add("https://pic4.zhimg.com/v2-f684b055b954c7f3e25572c3ddda65b2.jpg?source=8673f162");
                    data.setPics(list1);
                    list.add(data);
                    Log.i("asd","轮播图加载完成");

//新闻数据
                    GetNews(list,refresh);



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String error) {

            }
        });
    }

    public void GetNews(final List<Data> list, final Boolean refresh){


        Utils.get_token("http://122.9.2.27/api/news/recommend/v4?page=1&size="+size, token, new Utils.OkhttpCallBack() {
            @Override
            public void onSuccess(Response response) {

                try {
                    JSONObject jsonObject21=new JSONObject(Objects.requireNonNull(response.body()).string());
                    JSONObject jsonObject22=jsonObject21.getJSONObject("data");
                    o_page=jsonObject22.getInt( "count");
                    JSONArray jsonArray21=jsonObject22.getJSONArray("news");
                    Log.i("asd",jsonArray21.length()+"");

                    for (int i=0;i<jsonArray21.length();i++){
                        Data data21=new Data();
                        JSONObject jsonObject23=jsonArray21.getJSONObject(i);
                        data21.setTitle(jsonObject23.getString("title"));
                        data21.setNews_Id(jsonObject23.getInt("id"));
                        JSONArray jsonArray22=jsonObject23.getJSONArray("news_pics_set");
                       // data21.setNews_pics_set(jsonArray22.getString(1));
                        data21.setLike_num(jsonObject23.getInt("like_num"));
                        JSONObject jsonObject24=jsonObject23.getJSONObject( "author");
                        data21.setWriter(jsonObject24.getString( "username"));

                        list.add(data21);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (refresh){
                            adapter=new NewsAdapter(getContext(),list);
                            recyclerView.setAdapter(adapter);}
                            else {
                               adapter.addData(list);
                            }
                        }
                    });



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFail(String error) {

            }
        });

    }

}