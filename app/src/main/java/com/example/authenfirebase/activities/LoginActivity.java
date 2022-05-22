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

import com.example.authenfirebase.HomeActivity;
import com.example.authenfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    public EditText email,password;
    public Button login;
    public TextView loginTranToRegister;
    public FirebaseAuth loginAuth;
    ProgressBar progressBarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBarLogin = findViewById(R.id.progressbarLogin);
        progressBarLogin.setVisibility(View.GONE);
        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.passwordLogin);
        login = findViewById(R.id.btnLogin);
        loginTranToRegister = findViewById(R.id.loginTranToRegister);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginAuth = FirebaseAuth.getInstance();

                String emailText,passwordText;
                emailText = email.getText().toString();
                passwordText = password.getText().toString();

                if(TextUtils.isEmpty(emailText)) {
                    Toast.makeText(LoginActivity.this,"Vui lòng nhập email!",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(passwordText)) {
                    Toast.makeText(LoginActivity.this,"Vui lòng nhập mật khẩu!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(passwordText.length() < 6) {
                    Toast.makeText(LoginActivity.this,"Vui lòng nhập hơn 6 kí tự!",Toast.LENGTH_SHORT).show();
                    return;
                }

                loginAuth.signInWithEmailAndPassword(emailText,passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            progressBarLogin.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this,"Đăng nhập thành công!",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            progressBarLogin.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this,"Tài khoản hoặc mật khẩu không chính xác!",Toast.LENGTH_SHORT).show();
                        }
                        progressBarLogin.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        loginTranToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBarLogin.setVisibility(View.GONE);
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
                //Toast.makeText(LoginActivity.this,"chuyển tới trang đăng ký thành công",Toast.LENGTH_SHORT).show();
            }
        });
    }
}