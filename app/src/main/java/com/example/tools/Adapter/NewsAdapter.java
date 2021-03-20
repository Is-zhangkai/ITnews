package com.example.tools.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import com.example.tools.R;
import com.example.tools.tools.Data;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Data> list;
    private static final int ITEM_PAGER =0 ;
    private static final int ITEM_NEWS =1 ;

    private ViewPagerAdapter viewPagerAdapter;
    public NewsAdapter(Context context,List<Data> list){
        this.context=context;
        this.list=list;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view_news=null;
        RecyclerView.ViewHolder holder = null;
        if (i==ITEM_NEWS){
            view_news= LayoutInflater.from(context).inflate(R.layout.item_news,viewGroup,false);
            holder= new NewsHolder(view_news);
        }
        if (i==ITEM_PAGER){
            view_news= LayoutInflater.from(context).inflate(R.layout.item_viewpager2,viewGroup,false);
            holder= new PagerHolder(view_news);
        }
        assert holder != null;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        //新闻
        if (holder instanceof NewsHolder){
            ((NewsHolder)holder).title.setText(list.get(i).getTitle());
        }
        //轮播图
        if (holder instanceof PagerHolder){
            viewPagerAdapter = new ViewPagerAdapter(context, list.get(i).getPics());
            ((PagerHolder)holder).viewPager2.setAdapter(viewPagerAdapter);
            viewPagerAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public int getItemViewType(int i) {

        if (list.get(i).getPics()!=null){
            Log.i("asd","这是轮播图");
            return ITEM_PAGER;
        } else {
            return ITEM_NEWS;

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PagerHolder extends RecyclerView.ViewHolder {

        public ViewPager2 viewPager2;
        public LinearLayout linearLayout;
        public PagerHolder(@NonNull View itemView) {
            super(itemView);
            viewPager2=itemView.findViewById(R.id.recy_pager);
            linearLayout=itemView.findViewById(R.id.viewpager_lin);


        }}


    public class NewsHolder extends RecyclerView.ViewHolder {

        TextView title,writer,like_num;
        ImageView imageView;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.item_news_title);
            writer=itemView.findViewById(R.id.textView6);
            like_num=itemView.findViewById(R.id.textView12);
            imageView=itemView.findViewById(R.id.imageView2);
        }}

}
