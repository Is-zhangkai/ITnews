package com.example.tools.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import com.example.tools.R;
import com.example.tools.tools.Data;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_PAGER = 0; //说明是带有pager的
    public static final int TYPE_NEWS = 1;
    private Context context;
    private List<Data> list;
    private  ViewHolderPager pagerHolder;
    private ViewPagerAdapter viewPagerAdapter;


    public NewsAdapter(Context context, List<Data> list) {
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=null;
        RecyclerView.ViewHolder holder=null;
        if (i==TYPE_NEWS){
            view= LayoutInflater.from(context).inflate(R.layout.item_news,viewGroup,false);
            holder= new NewsAdapter.ViewHolderPager(view);}
        else if (i==TYPE_PAGER){
            view= LayoutInflater.from(context).inflate(R.layout.item_viewpager2,viewGroup,false);
            holder= new NewsAdapter.ViewHolderNews(view);}
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        if (holder instanceof ViewHolderNews){

        }
        if (holder instanceof ViewHolderPager){
            List<String> data_pager=list.get(i).getPics();
            pagerHolder=(ViewHolderPager) holder;


            viewPagerAdapter = new ViewPagerAdapter(context, data_pager);

            pagerHolder.viewPager2.setAdapter(viewPagerAdapter);
            viewPagerAdapter.notifyDataSetChanged();
        }



    }



    /** 重写这个方法,通过判断item的类型，从而绑定不同的view * */
    @Override
    public int getItemViewType(int i) {
        if (i>0 ){
            return TYPE_NEWS;
        }
        if (i == 0){
            return TYPE_PAGER;
        }
        return super.getItemViewType(i);
    }




    @Override
    public int getItemCount() {
        return list.size();
    }



    public static class ViewHolderNews extends RecyclerView.ViewHolder {

        TextView writer;

        public ViewHolderNews(@NonNull View itemView) {
            super(itemView);

        }
    }


    public static class ViewHolderPager extends RecyclerView.ViewHolder {

        ViewPager2 viewPager2;

        public ViewHolderPager(@NonNull View itemView) {
            super(itemView);

            viewPager2=itemView.findViewById(R.id.recy_pager);
        }
    }
}
