package com.example.authenfirebase.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.authenfirebase.R;
import com.example.authenfirebase.adapters.NavCategoryDetailedAdapter;
import com.example.authenfirebase.models.NavCategoryDetailedModel;
import com.example.authenfirebase.models.RecommendedModel;
import com.example.authenfirebase.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NavCategoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<NavCategoryDetailedModel> list;
    NavCategoryDetailedAdapter adapter;
    FirebaseFirestore db;
    ImageView rollback;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_category);
        String type = getIntent().getStringExtra("type");
        db = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.nav_category_det_rec);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new NavCategoryDetailedAdapter(this,list);
        recyclerView.setAdapter(adapter);
        rollback = findViewById(R.id.btnRollBack);

        rollback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //get vegetable
        if(type != null && type.equalsIgnoreCase("drink")){
            db.collection("NavCategoryDetailed").whereEqualTo("type","drink").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        NavCategoryDetailedModel navCategoryDetailedModel = documentSnapshot.toObject(NavCategoryDetailedModel.class);
                        list.add(navCategoryDetailedModel);
                        adapter.notifyDataSetChanged();
                        recyclerView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

    }
}