package au.edu.sydney.comp5216.cakefactory;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import adapter.PagerAdapter1;

public class CheckOutActivity extends AppCompatActivity {


    private FirebaseFirestore mFirestore;
    Map<String, Object> order = new HashMap<>();
    double price = 0;
    Fragment tab1View;
    Fragment tab2View;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        System.out.println(R.id.toolbar);
        System.out.println(toolbar);

        CustomTabLayout tabLayout = (CustomTabLayout) findViewById(R.id.tab_layout);
        System.out.println(R.id.tab_layout);
        System.out.println(tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("SHIPPING"));
        tabLayout.addTab(tabLayout.newTab().setText("PAYMENT"));
        // tabLayout.addTab(tabLayout.newTab().setText("CONFIRMATION"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        PagerAdapter1 adapter = new PagerAdapter1(getSupportFragmentManager(), tabLayout.getTabCount());

        Intent intent = getIntent();
        price = intent.getDoubleExtra("price", 88.0);

        dialog = new AlertDialog.Builder(this).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
                Intent it = new Intent(CheckOutActivity.this, MainActivity.class);
                finish();
                startActivity(it);
            }
        }).create();

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                System.out.println("##########################tab" + tab.getPosition());
                if (tab.getPosition() == 1) {
                    TextView total = (TextView) viewPager.findViewById(R.id.total2);
                    total.setText(String.valueOf(price));
                    Button payButton = (Button) viewPager.findViewById(R.id.payButton);

                    payButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            EditText cardno = (EditText) viewPager.findViewById(R.id.cardno2);
                            EditText date = (EditText) viewPager.findViewById(R.id.date2);
                            EditText cvv = (EditText) viewPager.findViewById(R.id.cvv2);
                            EditText holder = (EditText) viewPager.findViewById(R.id.name2);

                            order.put("CVV", cvv.getText().toString());
                            order.put("card", cardno.getText().toString());
                            order.put("cardholder", holder.getText().toString());
                            order.put("delivery", "1");
                            order.put("expdate", date.getText().toString());
                            order.put("price", String.valueOf(price));


                            System.out.println("################" + order.toString());
                            mFirestore = FirebaseFirestore.getInstance();
                            mFirestore.collection("orders")
                                    .add(order)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d("OK", "DocumentSnapshot written with ID: " + documentReference.getId());

                                            dialog.setMessage("The payment was successful");
                                            dialog.setTitle("Payment");
                                            dialog.show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("ERROR", "Error adding document", e);
                                            dialog.setMessage("The payment was failed");
                                            dialog.setTitle("Payment");
                                            dialog.show();
                                        }
                                    });


                        }
                    });
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                System.out.println("************************tab" + tab.getPosition());
                EditText firstname = (EditText) viewPager.findViewById(R.id.name2);
                EditText secondname = (EditText) viewPager.findViewById(R.id.secondname2);
                EditText phone = (EditText) viewPager.findViewById(R.id.phoneno2);
                EditText addr = (EditText) viewPager.findViewById(R.id.address2);
                EditText zip = (EditText) viewPager.findViewById(R.id.zip2);
                EditText city = (EditText) viewPager.findViewById(R.id.city2);

                order.put("addr", addr.getText().toString());
                order.put("city", city.getText().toString());
                order.put("delivery", "1");
                order.put("firstname", firstname.getText().toString());
                order.put("phone", phone.getText().toString());
                order.put("secondname", secondname.getText().toString());
                order.put("zipcode", zip.getText().toString());

                System.out.println("################" + order.toString());

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }


        });

    }


}