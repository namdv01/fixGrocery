package com.example.authenfirebase;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.authenfirebase.activities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class Logout extends Fragment {

    public Logout() {
        // Required empty public constructor
//        FirebaseAuth.getInstance().signOut();
//        Intent intent = new Intent(getContext(), LoginActivity.class);
//        startActivity(intent);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_logout, container, false);
    }
}