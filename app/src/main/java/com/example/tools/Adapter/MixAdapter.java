package com.example.tools.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tools.Activity.NewsDetailsActivity;
import com.example.tools.R;

import java.util.List;
import java.util.Map;

public class MixAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<Map<String, Object>> list;
    public Context context;
    public final int VIEW_NO = 0;
    public final int VIEW_PEO = 1;
    public final int VIEW_NEWS = 2;
    public final int VIEW_NET = 3;


    public MixAdapter(Context context, List<Map<String, Object>> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("1233",Integer.valueOf(list.get(position).get("type").toString())+"拓扑");
        return Integer.valueOf(list.get(position).get("type").toString());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Log.d("12332", viewType+"");
        if (viewType == VIEW_NEWS) {
            Log.d("12332", "新闻");
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
            return new NewsViewHolder(view);
        } else if(viewType == VIEW_NO){
            Log.d("12332", "没有");
            view = LayoutInflater.from(context).inflate(R.layout.item_nofans, parent, false);
            return new NoViewHolder(view);
        }else if(viewType == VIEW_PEO){
            view = LayoutInflater.from(context).inflate(R.layout.item_people, parent, false);
            return new PeoHolder(view);
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.item_papernonet, parent, false);
            return new NetHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NoViewHolder) {
            NoViewHolder viewHolder = (NoViewHolder) holder;
            viewHolder.textView.setText(list.get(position).get("text").toString());
            Log.d("12332", "00");
        } else if(holder instanceof NewsViewHolder){
            NewsViewHolder viewHolder = (NewsViewHolder) holder;
            viewHolder.tv_title.setText((list.get(position).get("title").toString()));
            viewHolder.tv_writer.setText((list.get(position).get("nickname").toString()));
            viewHolder.news_type.setText((list.get(position).get("tag_type").toString()));
            viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, NewsDetailsActivity.class);
                    intent.putExtra("id",Integer.valueOf(list.get(position).get("news_id").toString()));
                    intent.putExtra("user_id",Integer.valueOf(list.get(position).get("author_id").toString()));
                    intent.putExtra("writer",list.get(position).get("username").toString());
//                    intent.putExtra("photo",list.get(position).get("author_id").toString());
                    context.startActivity(intent);
                }
            });
        }else if(holder instanceof  NetHolder){

        }else {

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void myNotifyDataSetChange(){
        notifyDataSetChanged();
    }


    class NewsViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_writer;
        private TextView news_type;
        private ImageView iv_pic;
        private RelativeLayout relativeLayout;
        NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.item_news_title);
            tv_writer = itemView.findViewById(R.id.textView6);
            news_type = itemView.findViewById(R.id.textView12);
            iv_pic = itemView.findViewById(R.id.imageView2);
            relativeLayout = itemView.findViewById(R.id.relativelayout);

        }
    }
    class NoViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        NoViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview567);
        }
    }
    class NetHolder extends RecyclerView.ViewHolder {

        NetHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    class PeoHolder extends RecyclerView.ViewHolder {

        PeoHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
