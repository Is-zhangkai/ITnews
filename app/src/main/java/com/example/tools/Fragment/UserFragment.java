package com.example.tools.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tools.Activity.InterActivity;
import com.example.tools.Activity.LoginActivity;
import com.example.tools.Activity.MainActivity;
import com.example.tools.Activity.RegisterActivity;
import com.example.tools.R;

public class UserFragment extends Fragment {

    private TextView attentions;
    private TextView fans;
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
        attentions = view.findViewById(R.id.textView8);
        fans = view.findViewById(R.id.textView4);
        logout = view.findViewById(R.id.textView16);
        change = view.findViewById(R.id.button);
        history = view.findViewById(R.id.constraintLayout2);
        collection = view.findViewById(R.id.constraintLayout3);
        update = view.findViewById(R.id.constraintLayout4);
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
                                                        int which) { }
                                }).setNegativeButton("更新", null).create().show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }
}