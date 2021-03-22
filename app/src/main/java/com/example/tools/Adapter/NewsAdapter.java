package com.example.tools.Adapter;

import android.content.Context;
import android.content.Intent;
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
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;


import com.example.tools.Activity.NewsDetailsActivity;
import com.example.tools.R;
import com.example.tools.tools.Data;

import java.util.ArrayList;
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
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {

        //新闻
        if (holder instanceof NewsHolder){

            ((NewsHolder)holder).title.setText(list.get(i).getTitle());


            ((NewsHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(context,NewsDetailsActivity.class);
                    intent.putExtra("id",list.get(i).getNew_Id());
                    context.startActivity(intent);
                }
            });
        }
        //轮播图
        if (holder instanceof PagerHolder){
            List<String> img=new ArrayList<String>();
            img =list.get(i).getPics();
            viewPagerAdapter = new ViewPagerAdapter(context,img );



            ((PagerHolder)holder).viewPager2.setOffscreenPageLimit(1);
            //滑动效果
            CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
            compositePageTransformer.addTransformer(new MarginPageTransformer(10));
            compositePageTransformer.addTransformer(new TransFormer());
            //一屏三页
            ((PagerHolder)holder).viewPager2.setPageTransformer(compositePageTransformer);
            View recyclerView = ((PagerHolder)holder).viewPager2.getChildAt(0);
            if(recyclerView != null && recyclerView instanceof RecyclerView){
                recyclerView.setPadding(100, 0, 100, 0);
                ((RecyclerView) recyclerView).setClipToPadding(false);
            }


            ((PagerHolder)holder).viewPager2.setAdapter(viewPagerAdapter);
            ((PagerHolder)holder).viewPager2.setCurrentItem(1);
            // 循环滑动
            final List<String> finalImg = img;
            ((PagerHolder)holder).viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                int currentPosition;
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    currentPosition = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    // ViewPager.SCROLL_STATE_IDLE 标识的状态是当前页面完全展现，并且没有动画正在进行中，如果不
                    // 是此状态下执行 setCurrentItem 方法回在首位替换的时候会出现跳动！
                    if (state != ViewPager.SCROLL_STATE_IDLE) return;

                    // 当视图在第一个时，将页面号设置为图片的最后一张。
                    if (currentPosition == 0) {
                        ((PagerHolder)holder).viewPager2.setCurrentItem(finalImg.size() - 2, false);

                    } else if (currentPosition == finalImg.size() - 1) {
                        // 当视图在最后一个是,将页面号设置为图片的第一张。
                        ((PagerHolder)holder).viewPager2.setCurrentItem(1, false);
                    }
                }
            });

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

    public static class PagerHolder extends RecyclerView.ViewHolder {

        public ViewPager2 viewPager2;
        public LinearLayout linearLayout;
        public PagerHolder(@NonNull View itemView) {
            super(itemView);
            viewPager2=itemView.findViewById(R.id.recy_pager);
            linearLayout=itemView.findViewById(R.id.viewpager_lin);


        }}


    public static class NewsHolder extends RecyclerView.ViewHolder {

        TextView title,writer,like_num;
        ImageView imageView;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.item_news_title);
            writer=itemView.findViewById(R.id.textView6);
            like_num=itemView.findViewById(R.id.textView12);
            imageView=itemView.findViewById(R.id.imageView2);
        }}


//轮播图滑动动画
    static class TransFormer implements ViewPager2.PageTransformer {

        @Override
        public void transformPage(@NonNull View page, float position) {

            if (position >= -1.0f && position <= 0.0f) {
                //控制左侧滑入或者滑出的缩放比例
                page.setScaleX(1 + position * 0.1f);
                page.setScaleY(1 + position * 0.2f);
            } else if (position > 0.0f && position < 1.0f) {
                //控制右侧滑入或者滑出的缩放比例
                page.setScaleX(1 - position * 0.1f);
                page.setScaleY(1 - position * 0.2f);
            } else {
                //控制其他View缩放比例
                page.setScaleX(0.9f);
                page.setScaleY(0.8f);
            }
        }
    }

}
