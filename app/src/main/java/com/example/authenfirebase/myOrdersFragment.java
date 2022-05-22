package com.example.authenfirebase;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.authenfirebase.adapters.MyCartAdapter;
import com.example.authenfirebase.adapters.MyOrderAdapter;
import com.example.authenfirebase.models.MyCartModel;
import com.example.authenfirebase.models.MyOrderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class myOrdersFragment extends Fragment {

    RecyclerView recyclerView;
    MyOrderAdapter orderAdapter;
    List<MyOrderModel> orderModelList;
    FirebaseAuth auth;
    FirebaseFirestore db;
    ProgressBar progressBar;
    ConstraintLayout noOrder;
    ConstraintLayout haveOrder;

    //demo
//    List<MyCartModel> list;
//    MyCartAdapter adapter;

    public myOrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_orders, container, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        progressBar = root.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        noOrder = root.findViewById(R.id.noOrder);
        haveOrder = root.findViewById(R.id.haveOrder);

        recyclerView = root.findViewById(R.id.recycle_view);
        recyclerView.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //demo
//        list = new ArrayList<>();
//        adapter = new MyCartAdapter(getActivity(), list);
//        recyclerView.setAdapter(adapter);

        orderModelList = new ArrayList<>();
        orderAdapter = new MyOrderAdapter(getActivity(),orderModelList);
        recyclerView.setAdapter(orderAdapter);

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("MyOrder").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().getDocuments().size() == 0) {
                        progressBar.setVisibility(View.GONE);
                        haveOrder.setVisibility(View.GONE);
                        noOrder.setVisibility(View.VISIBLE);
                    } else {
                        for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                            String documentId = documentSnapshot.getId();

                            MyOrderModel orderModel = documentSnapshot.toObject(MyOrderModel.class);
                            orderModel.setDocumentId(documentId);
                            orderModelList.add(orderModel);
                            orderAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);

//                            MyCartModel myCartModel = documentSnapshot.toObject(MyCartModel.class);
//                            myCartModel.setDocumentId(documentId);
//                            list.add(myCartModel);
//                            adapter.notifyDataSetChanged();
//                            progressBar.setVisibility(View.GONE);
//                            recyclerView.setVisibility(View.VISIBLE);
                        }

                    }
                }
            }
        });

        return root;
    }
}