package com.example.tools.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.tools.Activity.ChatActivity;
import com.example.tools.Activity.MainActivity;
import com.example.tools.MyData;
import com.example.tools.R;
import com.example.tools.SQLite.MessageDate;
import com.example.tools.SQLite.myApplication;
import com.example.tools.SQLite.operation;

import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


public class MessageFragment extends Fragment {
    private ImageView icon_it;
    private ImageView icon_like;
    private ImageView icon_collect;
    private ImageView icon_comment;
    private ImageView icon_focus;
    private ImageView clear_all;
    private TextView red_like;
    private TextView red_collect;
    private TextView red_comment;
    private TextView red_focus;
    private String email;
    private RelativeLayout it;
    private RelativeLayout like;
    private RelativeLayout collect;
    private RelativeLayout comment;
    private RelativeLayout focus;
    private TextView red_all;
    public MessageDate messageDate=new MessageDate(getActivity());
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void setRedNumber(TextView textView, int num)
    {
        if(num==0)
        {
            textView.setVisibility(View.GONE);
        }
        else if(num>0&&num<=99)
        {
            textView.setVisibility(View.VISIBLE);
            textView.setText(String.valueOf(num));
        }
        else
        {
            textView.setVisibility(View.VISIBLE);
            textView.setText("99+");
        }
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
        MyData data=new MyData(getActivity());
        email=data.load_email();
        red_collect=view.findViewById(R.id.red_collect);
        red_like=view.findViewById(R.id.red_like);
        red_comment=view.findViewById(R.id.red_comment);
        red_focus=view.findViewById(R.id.red_focus);
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
        red_all=getActivity().findViewById(R.id.red_all);
        clear_all=view.findViewById(R.id.clear_all);
        clear_all.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("消息提示");
            builder.setMessage("您确定要清空所有未读消息吗？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        DbManager dbManager = x.getDb(((myApplication) getActivity().getApplicationContext()).getDaoConfig());
                        dbManager.update(operation.class, WhereBuilder.b("read","=",1),new KeyValue("read",0));
                        setRedNumber(red_all,0);
                        setRedNumber(red_like,0);
                        setRedNumber(red_comment,0);
                        setRedNumber(red_collect,0);
                        setRedNumber(red_focus,0);

                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            });
            builder.setNegativeButton("取消",null);
            AlertDialog alertDialog = builder.create();
            // 显示对话框
            alertDialog.show();
        });
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


    @Override
    public void onResume() {
        super.onResume();
        Log.i("test", "messfragmentOnstart");
        try {
            DbManager dbManager = x.getDb(((myApplication) getActivity().getApplicationContext()).getDaoConfig());
            List<operation> operations = new ArrayList<>();
            operations = dbManager.selector(operation.class).orderBy("id", true).limit(1000).where("read", "=", 1).and("email", "=", email).findAll();
            if (operations == null) {
                messageDate.setAll_msg(0);
            } else {
                messageDate.setAll_msg(operations.size());
                Log.i("test", String.valueOf(messageDate.getAll_msg())+"fragmentONresume");
            }
            List<operation> operations1 = new ArrayList<>();
            operations1 = dbManager.selector(operation.class).orderBy("id", true).limit(1000).where("type", "=", 2).and("read", "=", 1).and("email", "=", email).findAll();
            if (operations1 == null) {
                messageDate.setLike_msg(0);
            } else {
                messageDate.setLike_msg(operations1.size());

            }
            List<operation> operations2 = new ArrayList<>();
            operations2 = dbManager.selector(operation.class).orderBy("id", true).limit(1000).where("type", "=", 3).and("read", "=", 1).and("email", "=", email).findAll();
            if (operations2 == null) {
                messageDate.setCollect_msg(0);
            } else {
                messageDate.setCollect_msg(operations2.size());
                Log.i("test", String.valueOf(messageDate.getCollect_msg())+"fragmentONresume");

            }
            List<operation> operations3 = new ArrayList<>();
            operations3 = dbManager.selector(operation.class).orderBy("id", true).limit(1000).where("type", "=", 4).and("read", "=", 1).and("email", "=", email).findAll();
            if (operations3 == null) {
                messageDate.setComment_msg(0);
            } else {
                messageDate.setComment_msg(operations3.size());

            }
            List<operation> operations4 = new ArrayList<>();
            operations4 = dbManager.selector(operation.class).orderBy("id", true).limit(1000).where("type", "=", 5).and("read", "=", 1).and("email", "=", email).findAll();
            if (operations4 == null) {
                messageDate.setFocus_msg(0);
            } else {
                messageDate.setFocus_msg(operations4.size());

            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        setRedNumber(red_like,messageDate.getLike_msg());
        setRedNumber(red_collect,messageDate.getCollect_msg());
        setRedNumber(red_comment,messageDate.getComment_msg());
        setRedNumber(red_focus,messageDate.getFocus_msg());

    }
}