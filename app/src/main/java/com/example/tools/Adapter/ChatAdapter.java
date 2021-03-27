package com.example.tools.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tools.Activity.ChatActivity;
import com.example.tools.R;

import java.util.List;
import java.util.Map;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ChatActivity context;
    private List<Map<String,Object>> list;
    private View inflater;
    public ChatAdapter(ChatActivity context, List<Map<String,Object>> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater= LayoutInflater.from(context).inflate(R.layout.item_chat,parent,false);
        ViewHolder viewHolder=new ViewHolder(inflater);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewholder = (ViewHolder) holder;
        viewholder.date.setText(list.get(position).get("date").toString());
//            if(list.get(position).get("type")==2)
//            {
//                viewholder.head.setImageResource(R.drawable.message_like);
//                viewholder.msg.setText("您点赞了《"+list.get(position).get("title")+"》这篇文章 ");
//            }
//            else if(list.get(position).get("type")==3)
//            {
//                viewholder.head.setImageResource(R.drawable.message_collect);
//                viewholder.msg.setText("您收藏了《"+list.get(position).get("title")+"》这篇文章 ");
//            }
//            else if(list.get(position).get("type")==4)
//            {
//                viewholder.head.setImageResource(R.drawable.message_comment);
//                viewholder.msg.setText("您评论了《"+list.get(position).get("title")+"》这篇文章 ");
//            }
//            else if(list.get(position).get("type")==3)
//            {
//                viewholder.head.setImageResource(R.drawable.message_focus);
//                viewholder.msg.setText("您关注了"+list.get(position).get("title")+" 这位作者 ");
//            }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView head;
        TextView date;
        TextView msg;
        public ViewHolder(View view) {
            super(view);
            head=view.findViewById(R.id.chat_head);
            date=view.findViewById(R.id.chat_date);
            msg=view.findViewById(R.id.chat_content);

        }
    }
}
