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
import com.example.tools.tools.Data;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;


public class FocusNewsFragment extends Fragment {
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private SmartRefreshLayout smartRefreshLayout;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_focus_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        smartRefreshLayout= view.findViewById(R.id.new_srl2);
        recyclerView = view.findViewById(R.id.new_recy2);
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
               // GetNews(list);
                refreshLayout.finishRefresh();
            }
        });
        List<Data> list=new ArrayList<>();
        for (int i=0;i<10;i++){
            Data data1=new Data();
            data1.setTitle("标题"+i+"号");
            list.add(data1);
        }


        Log.i("asd",list.size()+"");
        adapter=new NewsAdapter(getContext(),list);
        recyclerView.setAdapter(adapter);
    }
}