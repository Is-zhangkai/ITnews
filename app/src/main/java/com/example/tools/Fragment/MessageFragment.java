package com.example.tools.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.tools.SQLite.operation;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

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
        Log.i("test","messfragmentOnstart");
        try {
            DbManager dbManager = x.getDb(((myApplication) getActivity().getApplicationContext()).getDaoConfig());
            List<operation> operations = new ArrayList<>();
            operations = dbManager.selector(operation.class).orderBy("id", true).limit(1000).where("read", "=", 1).findAll();
            if (operations == null) {
                messageDate.setAll_msg(0);
            } else {
                messageDate.setAll_msg(operations.size());
                Log.i("test",String.valueOf(messageDate.getAll_msg()));
            }
            List<operation> operations1 = new ArrayList<>();
            operations1 = dbManager.selector(operation.class).orderBy("id", true).limit(1000).where("type", "=", 2).and("read", "=", 1).findAll();
            if (operations1 == null) {
                messageDate.setLike_msg(0);
            } else {
                messageDate.setLike_msg(operations1.size());
                ;
            }
            List<operation> operations2 = new ArrayList<>();
            operations2 = dbManager.selector(operation.class).orderBy("id", true).limit(1000).where("type", "=", 3).and("read", "=", 1).findAll();
            if (operations2 == null) {
                messageDate.setCollect_msg(0);
            } else {
                messageDate.setCollect_msg(operations2.size());
                ;
            }
            List<operation> operations3 = new ArrayList<>();
            operations3 = dbManager.selector(operation.class).orderBy("id", true).limit(1000).where("type", "=", 4).and("read", "=", 1).findAll();
            if (operations3 == null) {
                messageDate.setComment_msg(0);
            } else {
                messageDate.setComment_msg(operations3.size());
                ;
            }
            List<operation> operations4 = new ArrayList<>();
            operations4 = dbManager.selector(operation.class).orderBy("id", true).limit(1000).where("type", "=", 5).and("read", "=", 1).findAll();
            if (operations4 == null) {
                messageDate.setFocus_msg(0);
            } else {
                messageDate.setFocus_msg(operations4.size());
                ;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        setRedNumber(icon_focus, messageDate.getFocus_msg());
        setRedNumber(icon_it, 0);
        setRedNumber(icon_collect, messageDate.getCollect_msg());
        setRedNumber(icon_like, messageDate.getLike_msg());
        setRedNumber(icon_comment, messageDate.getComment_msg());
    }
}