package com.example.tools.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tools.R;
import com.example.tools.tools.Comments;
import com.example.tools.tools.Data;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0; //说明是带有Header的
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_no =3;
    private boolean focus;
    private Context context;
    private GridViewAdapter gridAdpter;
    private List<Comments> list;


    public CommentAdapter(Context context,List<Comments> list) {
        this.context=context;
        this.list=list;
    }






    public void addData(List<Comments> addList){
        if (addList!=null){
            list.addAll(addList);
            notifyItemRangeChanged(list.size()-addList.size(),addList.size());
        }
    }

    /** 重写这个方法,通过判断item的类型，从而绑定不同的view * */
    @Override
    public int getItemViewType(int i) {
        if (list.get(i).getNoComments()!=null)
        {
            return TYPE_no;
        }  else {

        if (i>0 ){
            return TYPE_NORMAL;
        }
        if (i == 0){
            return TYPE_HEADER;
        }}

        return super.getItemViewType(i);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=null;
        RecyclerView.ViewHolder holder=null;
        if (i==TYPE_no){
            view= LayoutInflater.from(context).inflate(R.layout.item_no,viewGroup,false);
            holder= new ViewHolderNo(view);
        }
        if (i==TYPE_NORMAL){
            view= LayoutInflater.from(context).inflate(R.layout.item_comments,viewGroup,false);
            holder= new ViewHolderComments(view);}
        else if (i==TYPE_HEADER){
            view= LayoutInflater.from(context).inflate(R.layout.comment_header,viewGroup,false);
            holder= new ViewHolderNews(view);}
        return holder;

    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int i) {

        if (holder instanceof ViewHolderComments){
            ( (ViewHolderComments)holder).comment.setText(list.get(i).getComment_content());
            ( (ViewHolderComments)holder).writer.setText(list.get(i).getComment_writer());
            Glide.with(context).load(list.get(i).getPhoto()).error(R.drawable.errorhead).into(( (ViewHolderComments)holder).img);


        }else if (holder instanceof ViewHolderNews){
            ( (ViewHolderNews)holder).title.setText(list.get(i).getTitle());
            ( (ViewHolderNews)holder).writer.setText(list.get(i).getWriter());
            ( (ViewHolderNews)holder).content.setText(list.get(i).getContent());
            Glide.with(context).load(list.get(i).getPhoto()).error(R.drawable.error).circleCrop().into(  ( (ViewHolderNews)holder).photo);



            if (list.get(i).getPics()!=null){
            gridAdpter = new GridViewAdapter(context,list.get(i).getPics());
            ( (ViewHolderNews)holder).gridView.setAdapter(gridAdpter);}


            //关注按钮
            ( (ViewHolderNews)holder).btn_focus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (focus){focus=false;
                    ( (ViewHolderNews)holder).btn_focus.setBackgroundResource(R.drawable.btn_focus_fill);
                    ( (ViewHolderNews)holder).btn_focus.setText("关注");
                    ( (ViewHolderNews)holder).btn_focus.setTextColor(context.getResources().getColor(R.color.white));
                    }else {focus=true;

                    ( (ViewHolderNews)holder).btn_focus.setBackgroundResource(R.drawable.button_focus);
                    ( (ViewHolderNews)holder).btn_focus.setText("已关注");
                    ( (ViewHolderNews)holder).btn_focus.setTextColor(context.getResources().getColor(R.color.gradientstart));}
                }
            });
        }


    }


    public int getItemCount() {

        return list.size();
    }





    public static class ViewHolderComments extends RecyclerView.ViewHolder {
        TextView writer,comment;
        ImageView img;
        public ViewHolderComments(@NonNull View itemView) {
            super(itemView);
            comment=itemView.findViewById(R.id.comments);
            writer=itemView.findViewById(R.id.comment_writer);
            img=itemView.findViewById(R.id.comment_head);

        }
    }


    public static class ViewHolderNo extends RecyclerView.ViewHolder {

        public ViewHolderNo(@NonNull View itemView) {
            super(itemView);

        }
    }

    public static class ViewHolderNews extends RecyclerView.ViewHolder {
        GridView gridView;
        TextView title,content,writer;
        Button btn_focus;
        ImageView photo;
        public ViewHolderNews(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.tital);
            btn_focus=itemView.findViewById(R.id.details_btn);
            gridView=itemView.findViewById(R.id.gridview);
            content=itemView.findViewById(R.id.details_news);
            photo=itemView.findViewById(R.id.details_photo);
            writer=itemView.findViewById(R.id.details_writer);

        }
    }
     }

