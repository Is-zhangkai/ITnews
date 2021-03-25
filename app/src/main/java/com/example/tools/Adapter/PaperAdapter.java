package com.example.tools.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tools.Fragment.MyPaperFragment;

import java.util.List;
import java.util.Map;
import com.example.tools.R;
import com.example.tools.tools.Data;

public class PaperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private MyPaperFragment context;
    private List<Map<String, Object>> list;
    private View inflater;
    private static final int no = 0;
    private static final int yes = 1;
    private static final int net=2;
    public PaperAdapter(MyPaperFragment context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
    }


    public void addData(List<Map<String, Object>> addList){
        if (addList!=null){
            list.addAll(addList);
            notifyItemRangeChanged(list.size()-addList.size(),addList.size());
        }
    }


    @Override
    public int getItemViewType(int position) {
        int size=list.get(position).size();
        if(size==1)
        {
            return  no;
        }
        else if(size==3)
        {
            return  net;
        }
        else {
            return  yes;
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==yes)
        {
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType=getItemViewType(position);
        if(viewType==yes)
        {
            PaperAdapter.ViewHolder viewHolder= (PaperAdapter.ViewHolder)holder;
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            paperImage=itemView.findViewById(R.id.item_paper_image);
            paperTitle=itemView.findViewById(R.id.item_news_title);
            paperTag=itemView.findViewById(R.id.item_paper_tag);
            myPaper=itemView.findViewById(R.id.mypaper_item);
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
