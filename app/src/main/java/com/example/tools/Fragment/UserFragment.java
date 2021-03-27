package com.example.tools.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.tools.Activity.ChangeActivity;
import com.example.tools.Activity.LoginActivity;
import com.example.tools.Activity.MyCollections;
import com.example.tools.MyData;
import com.example.tools.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserFragment extends Fragment {

    private ConstraintLayout attentions;
    private ConstraintLayout fans;
    private TextView attentions_num;
    private TextView fans_num;
    private TextView logout;
    private Button change;
    private ConstraintLayout history;
    private ConstraintLayout collection;
    private ConstraintLayout update;

    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attentions = view.findViewById(R.id.attention);
        fans = view.findViewById(R.id.fans);
        logout = view.findViewById(R.id.textView16);
        change = view.findViewById(R.id.button);
        history = view.findViewById(R.id.constraintLayout2);
        collection = view.findViewById(R.id.constraintLayout3);
        update = view.findViewById(R.id.constraintLayout4);
        change = view.findViewById(R.id.button);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MyCollections.class);
                Bundle bd=new Bundle();
                bd.putString("name","收藏");
                bd.putInt("num",1);
                intent.putExtras(bd);
                startActivity(intent);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MyCollections.class);
                Bundle bd=new Bundle();
                bd.putString("name","历史记录");
                bd.putInt("num",2);
                intent.putExtras(bd);
                startActivity(intent);
            }
        });
        fans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MyCollections.class);
                Bundle bd=new Bundle();
                bd.putString("name","粉丝");
                bd.putInt("num",3);
                intent.putExtras(bd);
                startActivity(intent);
            }
        });
        attentions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MyCollections.class);
                Bundle bd=new Bundle();
                bd.putString("name","关注");
                bd.putInt("num",4);
                intent.putExtras(bd);
                startActivity(intent);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(getActivity());
                final View view = factory.inflate(R.layout.layout_update, null);

                new AlertDialog.Builder(getActivity())
                        .setView(view)
                        .setPositiveButton("取消",
                                new android.content.DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                    }
                                }).setNegativeButton("更新", null).create().show();
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChangeActivity.class));
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        MyData myData = new MyData(getContext());
        final String my_token= myData.load_token();
        if(my_token!="NO"){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String url = "http://122.9.2.27/api/self/info";
                    OkHttpClient okHttpClient = new OkHttpClient();
                    final Request request = new Request.Builder()
                            .url(url)
                            .get()
                            .addHeader("Authorization", my_token)
                            .build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("1233g", "onFailure: "+e.getMessage());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.d("1233g", "onResponse: " + response.body().string());
                        }
                    });
                }
            }).start();
        }
    }
}