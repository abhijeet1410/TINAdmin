package com.abhijeet14.tinadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
private EditText usernameText, passwordText;
private DatabaseReference dbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameText = findViewById(R.id.login_username);
        passwordText = findViewById(R.id.login_password);
        dbRef=FirebaseDatabase.getInstance().getReference().child("users").child("admin");
    }

    public void doLogin(final View view) {
        final String user = usernameText.getText().toString().trim();
        final String pass = passwordText.getText().toString().trim();
        if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)){
            Snackbar.make(view, "Fields can't be left blank", Snackbar.LENGTH_LONG).show();
        }else {
            Pattern pa = Pattern.compile("^[a-zA-Z1-9 ]+$");
            Matcher m = pa.matcher(user);
            if(!m.matches()){
                Snackbar.make(view, "Invalid User format", Snackbar.LENGTH_LONG).show();
            }else{
                final ProgressDialog p= new ProgressDialog(this);
                p.setMessage("Please wait while we are logging you in");
                p.setCancelable(false);
                p.show();
                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        p.dismiss();
                        String user1=dataSnapshot.child("email").getValue(String.class);
                        String pass1=dataSnapshot.child("password").getValue(String.class);
                        if(user1.equals(user)){
                            if(pass1.equals(pass)){
                                SharedPreferences s=getSharedPreferences("login",MODE_PRIVATE);
                                SharedPreferences.Editor editor=s.edit();
                                editor.putBoolean("login",true);
                                editor.apply();
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(LoginActivity.this, "Incorrect user name", Toast.LENGTH_SHORT).show();
                        }
                        dbRef.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(getSharedPreferences("login",MODE_PRIVATE).getBoolean("login",false)){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }
    }

}
