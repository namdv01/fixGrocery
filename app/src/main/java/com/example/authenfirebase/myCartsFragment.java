package com.example.authenfirebase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.authenfirebase.activities.PlaceOrderActivity;
import com.example.authenfirebase.adapters.MyCartAdapter;
import com.example.authenfirebase.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class myCartsFragment extends Fragment {

    RecyclerView recyclerView;
    MyCartAdapter cartAdapter;
    TextView overTotalAmount;
    List<MyCartModel> cartModelList;
    FirebaseAuth auth;
    FirebaseFirestore db;
    ProgressBar progressBar;
    Button buynow;
    ConstraintLayout noItemInCart;
    ConstraintLayout haveItemInCart;


    public myCartsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_carts, container, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        progressBar = root.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        noItemInCart = root.findViewById(R.id.noItemInCart);
        haveItemInCart = root.findViewById(R.id.haveItemInCart);

        recyclerView = root.findViewById(R.id.recycle_view);
        recyclerView.setVisibility(View.GONE);
        buynow = root.findViewById(R.id.buy_now);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        overTotalAmount = root.findViewById(R.id.totalPrice);
//        LocalBroadcastManager.getInstance(getActivity())
//                .registerReceiver(mMessageReceiver,new IntentFilter("MyTotalAmount"));

        cartModelList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(getActivity(),cartModelList);
        recyclerView.setAdapter(cartAdapter);

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().getDocuments().size() == 0) {
                        progressBar.setVisibility(View.GONE);
                        haveItemInCart.setVisibility(View.GONE);
                        noItemInCart.setVisibility(View.VISIBLE);
                    }
                    else {
                        for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                            String documentId = documentSnapshot.getId();

                            MyCartModel cartModel = documentSnapshot.toObject(MyCartModel.class);
                            cartModel.setDocumentId(documentId);
                            cartModelList.add(cartModel);
                            cartAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }

                        calculateTotalAmount(cartModelList);
                    }
                }

            }
        });

        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PlaceOrderActivity.class);
                intent.putExtra("itemList",(Serializable) cartModelList);
                startActivity(intent);
            }
        });

        return root;
    }

    public void calculateTotalAmount(List<MyCartModel> cartModelList){
        double totalAmount = 0;

        for(MyCartModel myCartModel : cartModelList){
            totalAmount += myCartModel.getTotalPrice();
        }

        overTotalAmount.setText("Tổng tiền: " + totalAmount);

    }

//    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            int totalBill = intent.getIntExtra("totalAmount",0);
//            overTotalAmount.setText("Tổng hóa đơn: " + totalBill);
//        }
//    };
}