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
import java.util.Objects;

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
        smartRefreshLayout= view.findViewById(R.id.new_srl1);
        recyclerView = view.findViewById(R.id.new_recy1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//刷新加载
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                refreshLayout.finishLoadMore();
            }
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                List<Data> list=new ArrayList<>();
                GetPager(list);
                refreshLayout.finishRefresh();
            }
        });

//新闻列表
        List<Data> list=new ArrayList<>();
        GetPager(list);

    }
    //获取轮播图
    public void GetPager(final List<Data> list){

        Utils.get("http://122.9.2.27/api/get-img-lunbo", new Utils.OkhttpCallBack() {
            @Override
            public void onSuccess(Response response) {

                try {
                    String ss=Objects.requireNonNull(response.body()).string();
                    Log.i("asd",ss);
                    JSONObject jsonObject1=new JSONObject(ss);
                    JSONObject jsonObject2=jsonObject1.getJSONObject("data");
                    JSONArray jsonArray=jsonObject2.getJSONArray("pics");
                    List<String> img=new ArrayList<>();
                    for (int i=0;i<jsonArray.length();i++){
                        String s=jsonArray.getString(i);
                        img.add(s);
                        Log.i("asd",s);
                    }
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
                    Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i=0;i<10;i++){
                                Data data1=new Data();
                                data1.setTitle("标题"+i+"号");
                                list.add(data1);
                        }


                            Log.i("asd",list.size()+"");
                            adapter=new NewsAdapter(getContext(),list);
                            recyclerView.setAdapter(adapter);
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