package com.example.tools.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tools.Adapter.CommentAdapter;
import com.example.tools.R;
import com.example.tools.Utils;
import com.example.tools.tools.Comments;
import com.example.tools.tools.Data;
import com.example.tools.tools.InputTextMsgDialog;
import com.google.gson.JsonObject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Response;

public class NewsDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SmartRefreshLayout smartRefreshLayout;
    private CommentAdapter commentAdapter;
    private boolean like,collection;
    private Button btn_like,btn_collection;
    private int id;
    private String title,writer,time;
    private String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MTY2MzQxNTEsImlhdCI6MTYxNjU0Nzc1MSwiaXNzIjoicnVhIiwiZGF0YSI6eyJ1c2VyaWQiOjR9fQ.pj755t5OURu1Q95PMUnW1QyOWRvxBcjTzMNl1oP6irM";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_news_details);
        btn_like=findViewById(R.id.news_like);
        btn_collection=findViewById(R.id.news_collection);
        recyclerView=this.findViewById(R.id.details_recycler);
        smartRefreshLayout=findViewById(R.id.comment_srl);
        recyclerView.setLayoutManager(new LinearLayoutManager(NewsDetailsActivity.this));
        id = getIntent().getIntExtra("id", 1);
        writer=getIntent().getStringExtra("writer");


        List<Comments> list = new ArrayList<>();
        GetData(list);


        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                final List<Comments> list=new ArrayList<>();
                GetData(list);
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                List<Comments> list = new ArrayList<>();
                GetData(list);
                refreshLayout.finishRefresh();
            }
        });








        //评论
        findViewById(R.id.edittext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final InputTextMsgDialog inputTextMsgDialog = new InputTextMsgDialog(NewsDetailsActivity.this, R.style.dialog_center);
                inputTextMsgDialog.setmOnTextSendListener(new InputTextMsgDialog.OnTextSendListener() {
                    @Override
                    public void onTextSend(String msg) {
                        //点击发送按钮后，回调此方法，msg为输入的值
                         String json="{\"content\": \""+msg+"\"}";
                         Log.i("asd",json);

                        try {
                            inputTextMsgDialog.clearText();
                            inputTextMsgDialog.dismiss();
                            Utils.post_json(token, "http://122.9.2.27/api/news/operator/"+id+"/comment", json, new Utils.OkhttpCallBack() {
                                @Override
                                public void onSuccess(Response response) {
                                    try {
                                        JSONObject jsonObject21 = new JSONObject(Objects.requireNonNull(response.body()).string());
                                        final String msg1 = jsonObject21.getString("msg");
                                        Log.i("asd", msg1);

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (msg1.equals("成功")){

                                                    inputTextMsgDialog.clearText();
                                                    inputTextMsgDialog.dismiss();
                                                    time=Utils.getTime();
                                                    Log.i("asd",time);

                                                }
                                            }
                                        });


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                @Override
                                public void onFail(String error) {
                              runOnUiThread(new Runnable() {
                                  @Override
                                  public void run() {
                                      Toast.makeText(NewsDetailsActivity.this,"评论失败",Toast.LENGTH_SHORT).show();
                                  }
                              });
                                }
                            });} catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                //设置评论字数
                inputTextMsgDialog.setMaxNumber(60);
                inputTextMsgDialog .show();
            }
        });



        //点赞
        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    String s=null;
                    Utils.post_json(token, "http://122.9.2.27/api/news/operator/" + id + "/like", s, new Utils.OkhttpCallBack() {
                        @Override
                        public void onSuccess(Response response) {
                            try {
                                JSONObject jsonObject21 = new JSONObject(Objects.requireNonNull(response.body()).string());
                                final String msg2 = jsonObject21.getString("msg");
                                Log.i("asd", msg2);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (msg2.equals("成功")){

                                            if (like){like=false;  btn_like.setBackgroundResource(R.drawable.like_nor);
                                            }else {like=true;  btn_like.setBackgroundResource(R.drawable.like_fill);}
                                        }
                                    }
                                });


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(String error) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(NewsDetailsActivity.this,"点赞失败",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        //收藏
        btn_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                try {
                    String s=null;
                    Utils.post_json(token, "http://122.9.2.27/api/news/operator/" + id + "/star", s, new Utils.OkhttpCallBack() {
                        @Override
                        public void onSuccess(Response response) {
                            try {
                                JSONObject jsonObject21 = new JSONObject(Objects.requireNonNull(response.body()).string());
                                final String msg3 = jsonObject21.getString("msg");
                                Log.i("asd", msg3);


                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (msg3.equals("成功")){
                                            if (collection){collection=false;  btn_collection.setBackgroundResource(R.drawable.collection_nor);
                                            }else {collection=true;  btn_collection.setBackgroundResource(R.drawable.collection_fill);}
                                        }
                                    }
                                });

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(String error) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(NewsDetailsActivity.this,"收藏失败",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        });
        //返回
        findViewById(R.id.news_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    //获取新闻
    public void GetData(final List<Comments> list){


            Utils.get_token("http://122.9.2.27/api/news/info/" + id + "/info-full", token, new Utils.OkhttpCallBack() {
                @Override
                public void onSuccess(Response response) {
                    try {
                        JSONObject jsonObject1=new JSONObject(Objects.requireNonNull(response.body()).string());
                        JSONObject jsonObject2=jsonObject1.getJSONObject("data");
                        Log.i("asd",jsonObject1.getString("msg"));
                         Comments comments=new Comments();
                         title=jsonObject2.getString("title");
                        comments.setTitle(title);
                        comments.setContent(jsonObject2.getString( "content"));
                        comments.setTag(jsonObject2.getInt("tag"));
                        comments.setLike_num(jsonObject2.getInt("like_num"));
                        comments.setComment_num(jsonObject2.getInt( "comment_num"));
                        comments.setStar_num(jsonObject2.getInt( "star_num"));
                        JSONArray jsonArray=jsonObject2.getJSONArray("pics");
                        List<String> imglist=new ArrayList<>();
                        for (int i=0;i<jsonArray.length();i++){
                            imglist.add(jsonArray.getString(i));
                        }
                        comments.setPics(imglist);



                        list.add(comments);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

        commentAdapter = new CommentAdapter(NewsDetailsActivity.this, list);
        recyclerView.setAdapter(commentAdapter);
                            }
                        });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
                @Override
                public void onFail(String error) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(NewsDetailsActivity.this,"连接失败，请刷新重试",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });


    }





}