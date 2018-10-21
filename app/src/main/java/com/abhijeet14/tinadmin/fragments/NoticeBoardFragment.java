package com.abhijeet14.tinadmin.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.abhijeet14.tinadmin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeBoardFragment extends Fragment {
    private View v;
    private AppCompatActivity a;
    private EditText titleText, bodyText, urlText;
    private Button addNoticeBtn;
    private CheckBox firstYearCheck, secondYearCheck, thirdYearCheck, fourthYearCheck, fifthYearCheck, teachersCheck;
    private TextInputLayout titleInput, bodyInput;
    private String emptyErrorMsg = "Field Can't be left blank";
    private FirebaseAuth mAuth;
    private DatabaseReference noticeRef,notificationRef;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_notice_board, container, false);
        a = (AppCompatActivity) getActivity();
        a.getSupportActionBar().setTitle("Notice Board");
        NavigationView nav = a.findViewById(R.id.nav_view);
        nav.setCheckedItem(R.id.nav_noticeBoard);
        titleText = v.findViewById(R.id.notice_title);
        notificationRef=FirebaseDatabase.getInstance().getReference().child("notification");
        noticeRef=FirebaseDatabase.getInstance().getReference().child("notice");
        bodyText = v.findViewById(R.id.notice_body);
        urlText = v.findViewById(R.id.notice_url);
        firstYearCheck = v.findViewById(R.id.check_first_year);
        secondYearCheck = v.findViewById(R.id.check_second_year);
        thirdYearCheck = v.findViewById(R.id.check_third_year);
        fourthYearCheck = v.findViewById(R.id.check_fourth_year);
        fifthYearCheck = v.findViewById(R.id.check_fifth_year);
        teachersCheck = v.findViewById(R.id.check_teachers);
        titleInput = v.findViewById(R.id.title_input);
        bodyInput = v.findViewById(R.id.body_input);
        addNoticeBtn = v.findViewById(R.id.add_notice_btn);
        mAuth = FirebaseAuth.getInstance();
        addNoticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(validate()){
                    final String title = titleText.getText().toString();
                    final String body = bodyText.getText().toString();
                    final String url = urlText.getText().toString();
                    Map<String,Object> m = new HashMap<>();
                    SimpleDateFormat sd=new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    String date=sd.format(new Date());
                    m.put("title",title);
                    m.put("body",body);
                    m.put("url",url);
                    m.put("by","Admin");
                    m.put("time",date);
                    final String key=notificationRef.push().getKey();
                    notificationRef.child(key).updateChildren(m).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            if(firstYearCheck.isChecked())
                                noticeRef.child("first").child(Long.toString(-1*Calendar.getInstance().getTimeInMillis())).child("id").setValue(key);
                            if(secondYearCheck.isChecked())
                                noticeRef.child("second").child(Long.toString(-1*Calendar.getInstance().getTimeInMillis())).child("id").setValue(key);
                            if(thirdYearCheck.isChecked())
                                noticeRef.child("third").child(Long.toString(-1*Calendar.getInstance().getTimeInMillis())).child("id").setValue(key);
                            if(fourthYearCheck.isChecked())
                                noticeRef.child("fourth").child(Long.toString(-1*Calendar.getInstance().getTimeInMillis())).child("id").setValue(key);
                            if(fifthYearCheck.isChecked())
                                noticeRef.child("fifth").child(Long.toString(-1*Calendar.getInstance().getTimeInMillis())).child("id").setValue(key);
                            if(teachersCheck.isChecked())
                                noticeRef.child("teacher").child(Long.toString(-1*Calendar.getInstance().getTimeInMillis())).child("id").setValue(key);
                            Snackbar.make(bodyText,"Notice posted succesfylly",Snackbar.LENGTH_LONG).show();
                            titleText.setText("");
                            bodyText.setText("");
                            urlText.setText("");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(bodyText,e.getMessage(),Snackbar.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
        return v;
    }

    boolean validate() {
        if (TextUtils.isEmpty(titleText.getText().toString().trim())) {
            titleText.requestFocus();
            titleInput.setError(emptyErrorMsg);
            return false;
        }
        if (TextUtils.isEmpty(bodyText.getText().toString().trim())) {
            bodyText.requestFocus();
            bodyInput.setError(emptyErrorMsg);
            return false;
        }
        return true;
    }
}