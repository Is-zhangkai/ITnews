package com.example.tools.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tools.R;

import java.util.List;
import java.util.Map;

public class MixAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<Map<String, Object>> list;
    public Context context;
    public final int VIEW_NO = 0;
    public final int VIEW_PEO = 1;
    public final int VIEW_NEWS = 2;


    public MixAdapter(Context context, List<Map<String, Object>> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        return Integer.valueOf(list.get(position).get("type").toString());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_NEWS) {
            Log.d("1233", "2");
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
            return new NewsViewHolder(view);
        } else if(viewType == VIEW_NO){
            view = LayoutInflater.from(context).inflate(R.layout.item_nofans, parent, false);
            return new NewsViewHolder(view);
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.item_people, parent, false);
            return new NewsViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
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
}
