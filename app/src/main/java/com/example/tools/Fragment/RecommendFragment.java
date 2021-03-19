package com.example.tools.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tools.Adapter.NewsAdapter;
import com.example.tools.R;
import com.example.tools.Utils;
import com.example.tools.tools.Data;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;


public class RecommendFragment extends Fragment {
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private SmartRefreshLayout smartRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        smartRefreshLayout= view.findViewById(R.id.new_srl);
        recyclerView = view.findViewById(R.id.new_recy);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//刷新加载
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                refreshLayout.finishLoadMore();
            }
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                refreshLayout.finishRefresh();
            }
        });

//新闻列表
        List<Data> list=new ArrayList<>();
        GetPager(list);
        adapter=new NewsAdapter(getContext(),list);
        recyclerView.setAdapter(adapter);
    }
    //获取轮播图
    public void GetPager(final List<Data> list){



        Utils.get("http://122.9.2.27/api/get-img-lunbo", new Utils.OkhttpCallBack() {
            @Override
            public void onSuccess(Response response) {

                try {
                    JSONObject jsonObject1=new JSONObject(response.body().string());
                    JSONObject jsonObject2=jsonObject1.getJSONObject("data");
                    JSONArray jsonArray=jsonObject2.getJSONArray("pics");
                    List<String> img=new ArrayList<>();
                    for (int i=0;i<jsonArray.length();i++){
                        String s=jsonArray.getString(i);
                        img.add(s);
                        Log.i("asd",s);
                    }
                    Data data=new Data();
                    data.setPics(img);
                    list.add(data);
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