package com.example.a21l_5195_assignment02;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextUtils;
import java.util.Collections;
import java.util.Comparator;


public class MainActivity extends AppCompatActivity implements RestaurantAdapter.ItemSelected {

    private static final String PREF_NAME = "restaurant_preferences";
    private static final String KEY_RESTAURANTS = "restaurants";

    RecyclerView.LayoutManager manager;
    RecyclerView recyclerView;
    RecyclerView.Adapter myadapter;

    ArrayList<Restaurant> restaurants;
    ArrayList<Restaurant> filteredRestaurants;
    Button btnAddPerson;
    EditText edtFilterRating;
    EditText edtFilterName;
    final int REGISTRATION_ACTIVITY = 1;

    SharedPreferences sharedPreferences;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnAddPerson = findViewById(R.id.btnAddRestaurant);
        recyclerView = findViewById(R.id.list);
        edtFilterRating = findViewById(R.id.edtFilterRating);
        edtFilterName = findViewById(R.id.edtFilterName);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
        loadRestaurants(); // Load restaurants from SharedPreferences



        btnAddPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivityForResult(intent, REGISTRATION_ACTIVITY);
            }
        });

        myadapter = new RestaurantAdapter(this, restaurants);
        recyclerView.setAdapter(myadapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REGISTRATION_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                String name = data.getStringExtra("Name");
                String phone = data.getStringExtra("Phone");
                String location = data.getStringExtra("Location");
                String description = data.getStringExtra("Description");
                String rating = data.getStringExtra("Rating");
                restaurants.add(new Restaurant(name, phone, location, description, rating));
                if(TextUtils.isEmpty(edtFilterName.getText())&&TextUtils.isEmpty(edtFilterRating.getText())){
                    filteredRestaurants.add(new Restaurant(name, phone, location, description, rating));
                }
                else if (!TextUtils.isEmpty(edtFilterRating.getText()) ||
                        !TextUtils.isEmpty(edtFilterName.getText())) {
                    applyFilters();
                }
                Toast.makeText(this, "Restaurant Added", Toast.LENGTH_SHORT).show();
                myadapter.notifyDataSetChanged();
                saveRestaurants(); // Save updated restaurants to SharedPreferences
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

        // Clear filtered list before applying new filters
        filteredRestaurants.clear();

        for (Restaurant restaurant : restaurants) {
            if (TextUtils.isEmpty(ratingFilter) || Double.parseDouble(restaurant.getRating()) >= Double.parseDouble(ratingFilter)) {
                if (TextUtils.isEmpty(nameFilter) || restaurant.getName().toLowerCase().contains(nameFilter.toLowerCase())) {
                    filteredRestaurants.add(restaurant);
                }
            }
        }

        Collections.sort(filteredRestaurants, new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant r1, Restaurant r2) {
                double rating1 = Double.parseDouble(r1.getRating());
                double rating2 = Double.parseDouble(r2.getRating());
                return Double.compare(rating1, rating2);
            }
        });

        // Update RecyclerView with filtered list
        myadapter = new RestaurantAdapter(this, filteredRestaurants);
        recyclerView.setAdapter(myadapter);
    }


    public void clearFilters(View view) {
        edtFilterRating.setText("");
        edtFilterName.setText("");

        filteredRestaurants.clear();
        filteredRestaurants.addAll(restaurants);

        myadapter.notifyDataSetChanged();
    }

    private void loadRestaurants() {
        String json = sharedPreferences.getString(KEY_RESTAURANTS, null);
        if (json != null) {
            Type type = new TypeToken<List<Restaurant>>() {}.getType();
            restaurants = gson.fromJson(json, type);
        } else {
            restaurants = new ArrayList<>();
        }
        filteredRestaurants = new ArrayList<>(restaurants);
    }

    private void saveRestaurants() {
        String json = gson.toJson(restaurants);
        sharedPreferences.edit().putString(KEY_RESTAURANTS, json).apply();
    }

    private void applyFilters() {
        String ratingFilter = edtFilterRating.getText().toString().trim();
        String nameFilter = edtFilterName.getText().toString().trim().toLowerCase();
        filteredRestaurants.clear();

        for (Restaurant restaurant : restaurants) {
            if (TextUtils.isEmpty(ratingFilter) || Double.parseDouble(restaurant.getRating()) >= Double.parseDouble(ratingFilter)) {
                if (TextUtils.isEmpty(nameFilter) || restaurant.getName().toLowerCase().contains(nameFilter.toLowerCase())) {
                    filteredRestaurants.add(restaurant);
                }
            }
        }
        myadapter.notifyDataSetChanged();
    }
}
