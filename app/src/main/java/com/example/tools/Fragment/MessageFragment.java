package com.example.tools.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tools.Activity.ChatActivity;
import com.example.tools.R;
import com.example.tools.SQLite.MessageDate;
import com.example.tools.SQLite.myApplication;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import q.rorbin.badgeview.QBadgeView;


public class MessageFragment extends Fragment {
    private ImageView icon_it;
    private ImageView icon_like;
    private ImageView icon_collect;
    private ImageView icon_comment;
    private ImageView icon_focus;
    private RelativeLayout it;
    private RelativeLayout like;
    private RelativeLayout collect;
    private RelativeLayout comment;
    private RelativeLayout focus;
    public MessageDate messageDate=new MessageDate(getActivity());
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        focus=view.findViewById(R.id.message_itemFocus);
        it=view.findViewById(R.id.message_itemIT);
        like=view.findViewById(R.id.message_itemLike);
        collect=view.findViewById(R.id.message_itemCollect);
        comment=view.findViewById(R.id.message_itemComment);
        icon_focus=view.findViewById(R.id.icon_focus);
        icon_it=view.findViewById(R.id.icon_it);
        icon_like=view.findViewById(R.id.icon_like);
        icon_collect=view.findViewById(R.id.icon_collect);
        icon_comment=view.findViewById(R.id.icon_comment);
        it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ChatActivity.class);
                Bundle bd=new Bundle();
                bd.putString("name","ITnews助手");
                bd.putInt("num",1);
                intent.putExtras(bd);
                startActivity(intent);
            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ChatActivity.class);
                Bundle bd=new Bundle();
                bd.putString("name","点赞");
                bd.putInt("num",2);
                intent.putExtras(bd);
                startActivity(intent);
            }
        });
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ChatActivity.class);
                Bundle bd=new Bundle();
                bd.putString("name","收藏");
                bd.putInt("num",3);
                intent.putExtras(bd);
                startActivity(intent);
            }
        });
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ChatActivity.class);
                Bundle bd=new Bundle();
                bd.putString("name","评论");
                bd.putInt("num",4);
                intent.putExtras(bd);
                startActivity(intent);
            }
        });
        focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ChatActivity.class);
                Bundle bd=new Bundle();
                bd.putString("name","关注");
                bd.putInt("num",5);
                intent.putExtras(bd);
                startActivity(intent);
            }
        });

    }
    public void setRedNumber(View view,int number)
    {
        QBadgeView qBadgeView=new QBadgeView(getActivity());
        qBadgeView.bindTarget(view);
        qBadgeView.setBadgeNumber(number);
    }

    @Override
    public void onStart() {
        super.onStart();
        setRedNumber(icon_focus,messageDate.getFocus_msg());
        setRedNumber(icon_it,0);
        setRedNumber(icon_collect,messageDate.getCollect_msg());
        setRedNumber(icon_like,messageDate.getLike_msg());
        setRedNumber(icon_comment,messageDate.getComment_msg());
    }
}