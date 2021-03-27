package com.example.tools.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tools.Fragment.MyPaperFragment;

import java.util.List;
import java.util.Map;
import com.example.tools.R;
import com.example.tools.tools.Comments;
import com.example.tools.tools.Data;
import com.example.tools.tools.MyNews;

public class PaperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private  List<MyNews> list;
    private View inflater;
    private static final int no = 0;
    private static final int yes = 1;
    private static final int net=2;
    public PaperAdapter(Context context,  List<MyNews> list) {
        this.context = context;
        this.list = list;
    }


    public void addData( List<MyNews> addList){
        if (addList!=null){
            list.addAll(addList);
            notifyItemRangeChanged(list.size()-addList.size(),addList.size());
        }
    }


    @Override
    public int getItemViewType(int i) {
       if (list.get(i).getNo()!=null){
           return  no;
       }
        if (list.get(i).getError()!=null){
            return  net;
        } else {
            return  yes;
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==yes)
        {
            Log.i("asd","asdfghjkl");
            inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mypaper, parent, false);
            RecyclerView.ViewHolder ViewHolder = new PaperAdapter.ViewHolder(inflater);
            return ViewHolder;}
        else if(viewType==net)
        {
            inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_papernonet,parent,false);
            PaperAdapter.netHolder netHolder = new PaperAdapter.netHolder(inflater);
            return  netHolder;
        }
        else
        {
            inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nomypaper,parent,false);
            PaperAdapter.noHolder noHolder = new PaperAdapter.noHolder(inflater);
            return  noHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        int viewType=getItemViewType(i);
        if(viewType==yes)
        {
            PaperAdapter.ViewHolder viewHolder= (PaperAdapter.ViewHolder)holder;
            viewHolder.paperTitle.setText(list.get(i).getTitle());
            Glide.with(context).load(list.get(i).getImg()).error(R.drawable.error).into(viewHolder.paperImage);




            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }

    @Override
    public int getItemCount() {
         return list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
            ImageView paperImage;
            TextView paperTitle;
            TextView paperTag;
            RelativeLayout myPaper;
            Button delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            paperImage=itemView.findViewById(R.id.item_paper_image);
            paperTitle=itemView.findViewById(R.id.item_news_title);
            paperTag=itemView.findViewById(R.id.item_paper_tag);
            myPaper=itemView.findViewById(R.id.mypaper_item);
            delete=itemView.findViewById(R.id.delete);
        }


    }
    class noHolder extends RecyclerView.ViewHolder{

        public noHolder (View view){
            super(view);
        }
    }
    class netHolder extends RecyclerView.ViewHolder{

        public netHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
