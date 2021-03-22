package com.example.tools.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tools.R;
import com.example.tools.tools.Comments;
import com.example.tools.tools.Data;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0; //说明是带有Header的
    public static final int TYPE_NORMAL = 1;
    private boolean focus;
    private Context context;
    private GridViewAdapter gridAdpter;

    private List<Comments> list;

    private View mHeaderView;
    public CommentAdapter(Context context,List<Comments> list) {
        this.context=context;
        this.list=list;
    }



    /** 重写这个方法,通过判断item的类型，从而绑定不同的view * */
    @Override
    public int getItemViewType(int i) {
        if (i>0 ){
            return TYPE_NORMAL;
        }
        if (i == 0){
            return TYPE_HEADER;
        }
        return super.getItemViewType(i);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=null;
        RecyclerView.ViewHolder holder=null;
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
            //( (ViewHolderComments)holder).comment.setText(comments.get(i));


        }else if (holder instanceof ViewHolderNews){
            ( (ViewHolderNews)holder).title.setText(list.get(i).getTitle());

            List<String> list=new ArrayList<>();
            list.add( "https://pic3.zhimg.com/v2-a1019116672185fdfc7616fc6432f8f7.jpg?source=8673f162");
            list.add("https://pic4.zhimg.com/v2-f684b055b954c7f3e25572c3ddda65b2.jpg?source=8673f162");
            list.add( "https://pic4.zhimg.com/v2-99b0bec360093b88f30d59bde9327f94.jpg?source=8673f162");
            list.add("https://pic1.zhimg.com/v2-f028176a557874d28d5cabe118415497.jpg?source=8673f162");
            list.add( "https://pic3.zhimg.com/v2-a1019116672185fdfc7616fc6432f8f7.jpg?source=8673f162");
            list.add("https://pic4.zhimg.com/v2-f684b055b954c7f3e25572c3ddda65b2.jpg?source=8673f162");
            gridAdpter = new GridViewAdapter(context,list);
            ( (ViewHolderNews)holder).gridView.setAdapter(gridAdpter);


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
        public ViewHolderComments(@NonNull View itemView) {
            super(itemView);
            comment=itemView.findViewById(R.id.comments);
        }
    }

    public static class ViewHolderNews extends RecyclerView.ViewHolder {
        GridView gridView;
        TextView title,content;
        Button btn_focus;
        public ViewHolderNews(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.tital);
            btn_focus=itemView.findViewById(R.id.details_btn);
            gridView=itemView.findViewById(R.id.gridview);

        }
    }
     }

