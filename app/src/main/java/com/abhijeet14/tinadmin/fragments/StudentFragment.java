package com.abhijeet14.tinadmin.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abhijeet14.tinadmin.R;
import com.abhijeet14.tinadmin.SignupActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentFragment extends Fragment {
private View v;
private AppCompatActivity a;
private FloatingActionButton signupBtn;
    public StudentFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_student, container, false);
        a = (AppCompatActivity) getActivity();
        a.getSupportActionBar().setTitle("Student");
        NavigationView nav = a.findViewById(R.id.nav_view);
        nav.setCheckedItem(R.id.nav_student);
        signupBtn = v.findViewById(R.id.student_signup_btn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SignupActivity.class).putExtra("type","student"));
            }
        });
        return v;
    }
}
