package com.example.authenfirebase.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.authenfirebase.R;
import com.example.authenfirebase.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    public Button register;
    public TextView registerTranToLogin;
    public EditText email,password,name;
    public FirebaseAuth registerAuth;
    public FirebaseDatabase database;
    ProgressBar progressBarRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressBarRegister = findViewById(R.id.progressbarRegister);
        progressBarRegister.setVisibility(View.GONE);
        name = findViewById(R.id.nameRegister);
        email = findViewById(R.id.emailRegister);
        password = findViewById(R.id.passwordRegister);
        register = findViewById(R.id.btnRegister);
        registerTranToLogin = findViewById(R.id.registerTranToLogin);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerAuth = FirebaseAuth.getInstance();
                database = FirebaseDatabase.getInstance();

                String emailText,passwordText,nameText;
                nameText = name.getText().toString();
                emailText = email.getText().toString();
                passwordText = password.getText().toString();

                if(TextUtils.isEmpty(nameText)) {
                    Toast.makeText(RegisterActivity.this,"Vui lòng nhập họ và tên!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(emailText)) {
                    Toast.makeText(RegisterActivity.this,"Vui lòng nhập email!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(passwordText)) {
                    Toast.makeText(RegisterActivity.this,"Vui lòng nhập mật khẩu!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(passwordText.length() < 6) {
                    Toast.makeText(RegisterActivity.this,"Vui lòng nhập hơn 6 kí tự!",Toast.LENGTH_SHORT).show();
                    return;
                }

                registerAuth.createUserWithEmailAndPassword(emailText, passwordText)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    UserModel userModel = new UserModel(nameText,emailText,passwordText);
                                    String id = task.getResult().getUser().getUid();
                                    database.getReference().child("Users").child(id).setValue(userModel);
                                    progressBarRegister.setVisibility(View.GONE);
                                    Toast.makeText(RegisterActivity.this,"Đăng ký thành công",Toast.LENGTH_SHORT).show();
                                } else {
                                    progressBarRegister.setVisibility(View.GONE);
                                    Toast.makeText(RegisterActivity.this,"Đăng ký thất bại",Toast.LENGTH_SHORT).show();
                                }
                                progressBarRegister.setVisibility(View.VISIBLE);
                            }
                        });
            }
        });

        registerTranToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBarRegister.setVisibility(View.GONE);
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                //Toast.makeText(RegisterActivity.this,"Chuyển tới trang đăng nhập thành công",Toast.LENGTH_SHORT).show();
            }
        });

    }
}