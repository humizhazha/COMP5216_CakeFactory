package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import adapter.OrderAdapter;
import model.OrderModel;

public class Orders extends AppCompatActivity {

    private OrderAdapter orderAdapter;
    private RecyclerView recyclerView;
    private ArrayList<OrderModel> orderModelArrayList = new ArrayList<>();

    private Integer[] imgs  = {
            R.drawable.img1,
            R.drawable.img12,
            R.drawable.img12,
            R.drawable.img1};
    private String[] titles  = {
            "Cocobolo Poolside Bar + Grill",
            "Palm Beach Seafood Restaurant",
            "Shin Minori Japanese Restaurant",
            "Shin Minori Japanese Restaurant"};
    private String[] sizes = {
            "7 inches",
            "8 inches",
            "9 inches",
            "12 inches"};
    private String[] dates = {
            "25 Nov 2017 10 : 30 AM",
            "27 Nov 2017 10 : 30 AM",
            "28 Nov 2017 10 : 30 AM",
            "29 Nov 2017 10 : 30 AM"};
    private String[] prices = {
            "$ 312.00",
            "$ 312.00",
            "$ 312.00",
            "$ 312.00"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders);

        TextView toolbar = findViewById(R.id.toolbar);
        toolbar.setText("Orders");

        recyclerView = findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Orders.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        for (int i = 0; i < 3; i++) {
            orderModelArrayList.add(
                    new OrderModel(imgs[i], titles[i], sizes[i], dates[i], prices[i]));
        }

        orderAdapter = new OrderAdapter(Orders.this, orderModelArrayList);
        recyclerView.setAdapter(orderAdapter);

        ImageView goBack = findViewById(R.id.backArrow);
        goBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Orders.this, Profile.class);
                startActivity(i);
            }
        });
    }

}
