package com.example.tools.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tools.Data;
import com.example.tools.R;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0; //说明是带有Header的
    public static final int TYPE_FOOTER = 1; //说明是带有Footer的
    public static final int TYPE_NORMAL = 2; //说明是不带有header和footer的

    private Context context;
    //private List<Data> comments;
    private List<String> comments;
    private View mHeaderView;
    private View mFooterView;
    public CommentAdapter(Context context,List<String> comments /*List<Data> comments*/) {
        this.context=context;
        this.comments=comments;
    }



    public View getHeaderView() {
        return mHeaderView;
    }
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }
    public View getFooterView() {
        return mFooterView;
    }
    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount()-1);
    }



    /** 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view * */
    @Override
    public int getItemViewType(int i) {
        if (mHeaderView == null ){//&& mFooterView == null
            return TYPE_NORMAL;
        }
        if (i == 0){
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        if (i == getItemCount()-1){
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if(mHeaderView != null && i == TYPE_HEADER) {
            return new ViewHolderComments(mHeaderView);
        }
        if(mFooterView != null && i == TYPE_FOOTER){
            return new ViewHolderComments(mFooterView);
        }
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_comments, viewGroup, false);
        return new ViewHolderComments(layout);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if(getItemViewType(i) == TYPE_NORMAL){
            if(holder instanceof ViewHolderComments) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
               ((ViewHolderComments) holder).comment.setText(comments.get(i-1));
                return;
            }
            return;
        }else if(getItemViewType(i) == TYPE_HEADER){
//            ((ViewHolderComments) holder).tital.setText(comments.get(i));

            return;
        }else{
            return;
        }
    }









     class ViewHolderComments extends RecyclerView.ViewHolder {
//header
//        ImageView img;
//         TextView tital,news_writer,info;

//comments
        TextView writer,comment;
        ImageView photo;

        public ViewHolderComments(@NonNull View itemView) {
            super(itemView);
            if (itemView == mHeaderView){
//                tital=(TextView)itemView.findViewById(R.id.tital);
                return;
            }
            if (itemView == mFooterView){
                return;
            }
          comment = (TextView)itemView.findViewById(R.id.comments);
        }
    }

    @Override
    public int getItemCount() {
        if(mHeaderView == null  && mFooterView == null){
            return comments.size();
        }else if(mHeaderView == null && mFooterView != null){
            return comments.size()+1;

        }else if (mHeaderView != null && mFooterView == null){
            return comments.size()+1;
        }else {
            return comments.size()+ 2;
        }
    }

     }

