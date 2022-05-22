package com.example.authenfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.authenfirebase.activities.LoginActivity;
import com.example.authenfirebase.activities.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    public TextView tranToLogin,tranToRegister;
    ProgressBar progressBarMain;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        tranToLogin = findViewById(R.id.mainTranToLogin);
        tranToRegister = findViewById(R.id.mainTranToRegister);
        progressBarMain = findViewById(R.id.progressbarMain);
        progressBarMain.setVisibility(View.GONE);
        if(auth.getCurrentUser() != null) {
            progressBarMain.setVisibility(View.VISIBLE);
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
            Toast.makeText(MainActivity.this,"Đợi chút,bạn đang đăng nhập",Toast.LENGTH_SHORT).show();
            finish();
        }

        tranToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBarMain.setVisibility(View.GONE);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                progressBarMain.setVisibility(View.VISIBLE);
                finish();
            }
        });

        tranToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBarMain.setVisibility(View.GONE);
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                progressBarMain.setVisibility(View.VISIBLE);
                finish();
            }
        });
    }
}