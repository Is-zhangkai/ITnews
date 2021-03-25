package com.example.tools.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.tools.Activity.NewsDetailsActivity;
import com.example.tools.Activity.WriteActivity;
import com.example.tools.Adapter.CommentAdapter;
import com.example.tools.R;
import com.example.tools.Utils;
import com.example.tools.tools.Comments;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.Response;


public class MyPaperFragment extends Fragment {
    private RelativeLayout go_edit;
    private String token;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_paper, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        go_edit=getActivity().findViewById(R.id.go_edit);
        go_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), WriteActivity.class);
                startActivity(intent);
            }
        });


        //加载新闻

        List<Map<String, Object>> list=new ArrayList<>();
        GetNews(list);


    }

    public void GetNews(List<Map<String, Object>> list){

        try {
            Utils.get_token("http://122.9.2.27/api/self/news-ids", token, new Utils.OkhttpCallBack() {
                @Override
                public void onSuccess(Response response) {
                    try {
                        JSONObject jsonObject=new JSONObject(response.body().string());
                        String msg=jsonObject.getString("msg");
                        JSONObject jsonObject1=jsonObject.getJSONObject("data");
                        JSONArray jsonArray=jsonObject1.getJSONArray("news");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject2=jsonArray.getJSONObject(i);
                            Map<String,Object> map=new HashMap<String, Object>();
                            int id=jsonObject2.getInt("id");
                            String title=jsonObject2.getString("title");
                            map.put("id", id);
                            map.put("title",title);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFail(String error) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}