package com.example.a21l_5195_assignment02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextUtils;



public class MainActivity extends AppCompatActivity implements RestaurantAdapter.ItemSelected {

    RecyclerView.LayoutManager manager;
    RecyclerView recyclerView;
    RecyclerView.Adapter myadapter;

    ArrayList<Restaurant> restaurants;
    ArrayList<Restaurant> filteredRestaurants;
    Button btnAddPerson;
    EditText edtFilterRating;
    EditText edtFilterName;
    EditText edtFilterPhone;
    final int REGISTRATION_ACTIVITY=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAddPerson=findViewById(R.id.btnAddRestaurant);
        recyclerView=findViewById(R.id.list);
        edtFilterRating = findViewById(R.id.edtFilterRating);
        edtFilterName = findViewById(R.id.edtFilterName);
        edtFilterPhone = findViewById(R.id.edtFilterPhone);
        recyclerView.setHasFixedSize(true);
        manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        restaurants=new ArrayList<>();
        filteredRestaurants = new ArrayList<>();
        restaurants.add(new Restaurant("Riaz Restaurant","055-4321566","Model Town","Excellent in Taste","3"));
        restaurants.add(new Restaurant("Pind Restaurant","055-4321566","Gt Road","Excellent in Taste","4"));
        restaurants.add(new Restaurant("Prime Restaurant","055-4321566","Eminabad","Excellent in Taste","2"));
        restaurants.add(new Restaurant("Food Horn","055-4321566","Fazal Centre","Excellent in Taste","5"));
        restaurants.add(new Restaurant("Riaz Restaurant","055-4321566","Model Town","Excellent in Taste","2.3"));
        restaurants.add(new Restaurant("Pind Restaurant","055-4321566","Gt Road","Excellent in Taste","1"));
        restaurants.add(new Restaurant("Prime Restaurant","055-4321566","Eminabad","Excellent in Taste","3.4"));
        restaurants.add(new Restaurant("Food Horn","055-4321566","Fazal Centre","Excellent in Taste","3.2"));
        restaurants.add(new Restaurant("Bowmbay Chowpatty","055-4321566","Fazal Center","Excellent in Taste","3"));


        btnAddPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RegistrationActivity.class);
                startActivityForResult(intent,REGISTRATION_ACTIVITY);
            }
        });
        myadapter=new RestaurantAdapter(this,restaurants);
        recyclerView.setAdapter(myadapter);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,@Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==REGISTRATION_ACTIVITY){
            if(resultCode==RESULT_OK){
                String name=data.getStringExtra("Name");
                String phone=data.getStringExtra("Phone");
                String location=data.getStringExtra("Location");
                String description=data.getStringExtra("Description");
                String rating=data.getStringExtra("Rating");
                restaurants.add(new Restaurant(name,phone,location,description,rating));
                Toast.makeText(this,"Restaurant Added",Toast.LENGTH_SHORT).show();
                myadapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void onItemClicked(int index) {
        Toast.makeText(this, restaurants.get(index).getRating(), Toast.LENGTH_SHORT).show();
    }


    public void filterRestaurants(View view) {
        String ratingFilter = edtFilterRating.getText().toString().trim();
        String nameFilter = edtFilterName.getText().toString().trim();
        String phoneFilter = edtFilterPhone.getText().toString().trim();

        // Clear filtered list before applying new filters
        filteredRestaurants.clear();

        for (Restaurant restaurant : restaurants) {
            // Check if rating matches filter (if filter is not empty)
            if (TextUtils.isEmpty(ratingFilter) || restaurant.getRating().equals(ratingFilter)) {
                // Check if name matches filter (if filter is not empty)
                if (TextUtils.isEmpty(nameFilter) || restaurant.getName().toLowerCase().contains(nameFilter.toLowerCase())) {
                    // Check if phone number matches filter (if filter is not empty)
                    if (TextUtils.isEmpty(phoneFilter) || restaurant.getPhone().contains(phoneFilter)) {
                        // All filters match, add restaurant to filtered list
                        filteredRestaurants.add(restaurant);
                    }
                }
            }
        }

        // Update RecyclerView with filtered list
        myadapter = new RestaurantAdapter(this, filteredRestaurants);
        recyclerView.setAdapter(myadapter);
    }
    public void clearFilters(View view) {
        // Clear filter fields
        edtFilterRating.setText("");
        edtFilterName.setText("");
        edtFilterPhone.setText("");

        // Reset filtered list to original list
        filteredRestaurants.clear();
        filteredRestaurants.addAll(restaurants);

        // Update RecyclerView with original list
        myadapter.notifyDataSetChanged();
    }
}