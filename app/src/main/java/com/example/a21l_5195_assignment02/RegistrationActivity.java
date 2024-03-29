package com.example.a21l_5195_assignment02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class RegistrationActivity extends AppCompatActivity {

    private EditText nameEditText, locationEditText, phoneEditText, descriptionEditText;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        nameEditText = findViewById(R.id.nameEditText);
        locationEditText = findViewById(R.id.locationEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name=nameEditText.getText().toString().trim();
                String Location=locationEditText.getText().toString().trim();
                String Phone=phoneEditText.getText().toString().trim();
                String Description=descriptionEditText.getText().toString().trim();
                Random random = new Random();
                int ratingInt = random.nextInt(5) + 1;
                String rating = Integer.toString(ratingInt);

                if(Name.isEmpty()){nameEditText.setError("Field cannot be empty");}
                if(Location.isEmpty()){locationEditText.setError("Field cannot be empty");}
                if(Phone.isEmpty()){phoneEditText.setError("Field cannot be empty");}
                if(Description.isEmpty()){descriptionEditText.setError("Field cannot be empty");}
                else{
                // Intent to move to another activity (e.g., MainActivity)
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                intent.putExtra("Name",Name);
                intent.putExtra("Location",Location);
                intent.putExtra("Phone",Phone);
                intent.putExtra("Description",Description);
                intent.putExtra("Rating",rating);
                setResult(RESULT_OK,intent);
                finish();}
            }
        });
    }
}
