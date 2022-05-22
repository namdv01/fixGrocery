package com.example.authenfirebase.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.authenfirebase.R;
import com.example.authenfirebase.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    ImageView detailed_img,add_item,remove_item;
    TextView price,rating,desc,quantity;
    Button addToCart;
    ImageView rollback;
    ViewAllModel viewAllModel = null;
    int totalQuantity = 1;
    int totalPrice = 0;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof ViewAllModel){
            viewAllModel = (ViewAllModel) object;
        }

        rollback = findViewById(R.id.btnRollBack);

        detailed_img = findViewById(R.id.detailed_img);
        price = findViewById(R.id.detailed_price);
        rating = findViewById(R.id.detailed_rating);
        desc = findViewById(R.id.detailed_desc);
        quantity = findViewById(R.id.quantity);

        if(viewAllModel != null) {
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailed_img);
            rating.setText(viewAllModel.getRating());
            desc.setText(viewAllModel.getDesc());
            totalPrice = viewAllModel.getPrice() * totalQuantity;
            if(viewAllModel.getType().equals("egg")) {
                price.setText("Giá:" + viewAllModel.getPrice() + "/tá");
            }
            else if(viewAllModel.getType().equals("milk")) {
                price.setText("Giá:" + viewAllModel.getPrice() + "/lít");
            }
            else {
                price.setText("Giá:" + viewAllModel.getPrice() + "/kg");
            }


        }

        add_item = findViewById(R.id.add_img);
        remove_item = findViewById(R.id.remove_img);

        addToCart = findViewById(R.id.add_to_cart);

        rollback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity < 10) {
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice = viewAllModel.getPrice() * totalQuantity;
                }
            }
        });

        remove_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity > 1) {
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice = viewAllModel.getPrice() * totalQuantity;
                }
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addedToCart();
            }
        });

    }

    public void addedToCart() {
        String saveCurDate,saveCurTime;
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat curTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurTime = curTime.format(calendar.getTime());

        SimpleDateFormat curDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurDate = curDate.format(calendar.getTime());

        final HashMap<String,Object> cartMap = new HashMap<>();
        cartMap.put("productName",viewAllModel.getName());
        cartMap.put("productPrice",price.getText().toString());
        cartMap.put("currentDate",saveCurDate);
        cartMap.put("currentTime",saveCurTime);
        cartMap.put("totalQuantity",quantity.getText().toString());
        cartMap.put("totalPrice",totalPrice);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(getApplicationContext(),"Đã thêm sản phẩm vào giỏ hàng",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}