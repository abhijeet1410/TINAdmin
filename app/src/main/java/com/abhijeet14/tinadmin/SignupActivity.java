package com.abhijeet14.tinadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.abhijeet14.tinadmin.dialog.PasswordDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class SignupActivity extends AppCompatActivity {
    private EditText nameText,emailText,addressText,phoneText,yearText,passwordText,confirmPasswordText;
    private CircleImageView pic;
    private Spinner categorySelect,year,rollNum;
    private LinearLayout rollLayout;
    private TextInputLayout nameInput,emailInput,addressInput,phoneInput,yearInput,passwordInput,confirmPasswordInput;
    private String emptyErrorMsg = "Field Must not be left empty";
    private boolean hasPic=false;
    private CircleImageView img;
    private byte[] mbyte;
    private FirebaseAuth mAuth;
    private String adminEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth=FirebaseAuth.getInstance();
        nameText = findViewById(R.id.signup_name);
        emailText = findViewById(R.id.signup_email);
        addressText = findViewById(R.id.signup_address);
        phoneText = findViewById(R.id.signup_phone);
        yearText = findViewById(R.id.signup_year);
        passwordText = findViewById(R.id.signup_password);
        confirmPasswordText = findViewById(R.id.signup_confirm_password);
        pic = findViewById(R.id.signup_pic);
        categorySelect = findViewById(R.id.signup_category);
        year = findViewById(R.id.signup_roll_year);
        rollNum = findViewById(R.id.signup_roll_number);
        rollLayout = findViewById(R.id.signup_roll_layout);
        img=findViewById(R.id.signup_dp);
        adminEmail=mAuth.getCurrentUser().getEmail();
        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nameInput.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        emailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailInput.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        addressText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addressInput.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        phoneText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phoneInput.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        yearText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                yearInput.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordInput.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        confirmPasswordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                confirmPasswordInput.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        nameInput = findViewById(R.id.name_input);
        emailInput = findViewById(R.id.email_input);
        addressInput = findViewById(R.id.address_input);
        phoneInput = findViewById(R.id.contact_input);
        yearInput = findViewById(R.id.year_input);
        passwordInput = findViewById(R.id.password_input);
        confirmPasswordInput = findViewById(R.id.confirm_password_input);

        categorySelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getSupportActionBar().setTitle("Add a "+categorySelect.getSelectedItem().toString());
                if(categorySelect.getSelectedItem().toString().equals("Teacher")){
                    rollLayout.setVisibility(View.GONE);
                    yearText.setVisibility(View.GONE);
                }else{
                    rollLayout.setVisibility(View.VISIBLE);
                    yearText.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Bundle b = getIntent().getExtras();
        String type = b.getString("type");
        if(type.equals("teacher"))
            categorySelect.setSelection(1);
        getSupportActionBar().setTitle("Add a "+type);
    }
    public void doSignup(View view) {
        if(validate()){
            new PasswordDialog(this);
        }
    }
    public void createUser(final String pass){
        final ProgressDialog p=new ProgressDialog(this);
        p.setMessage("Please Wait");
        p.setCancelable(false);
        p.show();
        mAuth.signInWithEmailAndPassword(adminEmail,pass).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar.make(img,"Can't verify the admin",Snackbar.LENGTH_LONG).show();
                p.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                final String name=nameText.getText().toString().trim();
                final String email=emailText.getText().toString().trim();
                final String address=addressText.getText().toString().trim();
                final String number=phoneText.getText().toString().trim();
                String password=passwordText.getText().toString().trim();
                final String roll=year.getSelectedItem().toString()+"IMCA"+rollNum.getSelectedItem().toString();
                final String year=yearText.getText().toString().trim();
                mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        final String uid=authResult.getUser().getUid();
                        StorageReference dpStore=FirebaseStorage.getInstance().getReference().child("dp").child(uid+".jpg");
                        dpStore.putBytes(mbyte).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                p.dismiss();
                                Snackbar.make(img,e.getMessage(),Snackbar.LENGTH_LONG).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                String downloadUrl=taskSnapshot.getDownloadUrl().toString();
                                Map<String,Object> m=new HashMap<>();
                                m.put("name",name);
                                m.put("email",email);
                                m.put("address",address);
                                m.put("number",number);
                                m.put("image",downloadUrl);
                                DatabaseReference userRef;
                                if(categorySelect.getSelectedItem().toString().equals("student")){
                                    m.put("roll",roll);
                                    m.put("year",year);
                                    userRef=FirebaseDatabase.getInstance().getReference().child("users").child("student").child(uid);
                                }else{
                                    userRef=FirebaseDatabase.getInstance().getReference().child("users").child("teacher").child(uid);
                                }
                                userRef.updateChildren(m).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        p.dismiss();
                                        Snackbar.make(img,e.getMessage(),Snackbar.LENGTH_LONG).show();
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        mAuth.getCurrentUser().updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(categorySelect.getSelectedItem().toString()).build()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                mAuth.signOut();
                                                mAuth.signInWithEmailAndPassword(adminEmail,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                                    @Override
                                                    public void onSuccess(AuthResult authResult) {
                                                        p.dismiss();
                                                        Toast.makeText(SignupActivity.this, "User successfully created", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        p.dismiss();
                                                        Snackbar.make(img,e.getMessage(),Snackbar.LENGTH_LONG).show();
                                                    }
                                                });
                                            }
                                        });

                                    }
                                });

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(img,e.getMessage(),Snackbar.LENGTH_LONG).show();
                        p.dismiss();
                    }
                });
            }
        });
    }
    boolean validate(){
        if(TextUtils.isEmpty(nameText.getText().toString().trim())){
            nameText.requestFocus();
            nameInput.setError(emptyErrorMsg);
            return false;
        } if(TextUtils.isEmpty(emailText.getText().toString().trim())){
            emailText.requestFocus();
            emailInput.setError(emptyErrorMsg);
            return false;
        } if(TextUtils.isEmpty(addressText.getText().toString().trim())){
            addressText.requestFocus();
            addressInput.setError(emptyErrorMsg);
            return false;
        } if(TextUtils.isEmpty(phoneText.getText().toString().trim())){
            phoneText.requestFocus();
            phoneInput.setError(emptyErrorMsg);
            return false;
        } if(categorySelect.getSelectedItem().toString().equals("student") && TextUtils.isEmpty(yearText.getText().toString().trim())){
            yearText.requestFocus();
            yearInput.setError(emptyErrorMsg);
            return false;
        } if(TextUtils.isEmpty(passwordText.getText().toString().trim())){
            passwordText.requestFocus();
            passwordInput.setError(emptyErrorMsg);
            return false;
        } if(TextUtils.isEmpty(confirmPasswordText.getText().toString().trim())){
            confirmPasswordText.requestFocus();
            confirmPasswordInput.setError(emptyErrorMsg);
            return false;
        } if(!(confirmPasswordText.getText().toString().trim().equals(passwordText.getText().toString().trim()))){
            confirmPasswordText.requestFocus();
            confirmPasswordInput.setError("Passwords do not match");
            return false;
        }
        if(!hasPic){
            Snackbar.make(img,"You must select a picture",Snackbar.LENGTH_LONG).setAction("choose", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(1,1)
                            .start(SignupActivity.this);
                }
            }).show();
            return false;
        }
        return true;
    }

    public void upload(View view) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                File thumb_filePath = new File(resultUri.getPath());
                Bitmap thumb_bitmap = null;
                try {
                    thumb_bitmap = new Compressor(this)
                            .setMaxHeight(200)
                            .setMaxWidth(200)
                            .setQuality(10)
                            .compressToBitmap(thumb_filePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                mbyte= byteArrayOutputStream.toByteArray();
                img.setImageBitmap(thumb_bitmap);
                hasPic=true;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(SignupActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
