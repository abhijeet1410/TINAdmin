package com.abhijeet14.tinadmin.dialog;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.abhijeet14.tinadmin.R;
import com.abhijeet14.tinadmin.SignupActivity;

public class PasswordDialog extends Dialog {
    EditText passText;
    Button create;
    public PasswordDialog( @NonNull final SignupActivity a) {
        super(a);
        setContentView(R.layout.password_dialog);
        setCanceledOnTouchOutside(false);
        passText=findViewById(R.id.dialog_password);
        create=findViewById(R.id.create_acc_btn);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass=passText.getText().toString().trim();
                if(TextUtils.isEmpty(pass)){
                    Snackbar.make(create,"Password can't be empty",Snackbar.LENGTH_SHORT).show();
                }else{
                    dismiss();
                    a.createUser(pass);
                }
            }
        });
        show();
    }

}
