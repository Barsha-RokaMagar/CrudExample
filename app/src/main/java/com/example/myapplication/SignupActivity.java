package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    EditText name, username, password, email;
    TextView loginlink;
    Button signupbtn;
  //  FirebaseDatabase database;
   // DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize views
        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        loginlink = findViewById(R.id.loginlink);
        signupbtn = findViewById(R.id.signup);

        // Handle login link click
        loginlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Handle signup button click
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize Firebase components
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("customers");
                // giving reference to users path that we are creating

                // Retrieve user input
                String nameuser = name.getText().toString().trim();
                String emailuser = email.getText().toString().trim();
                String userusername = username.getText().toString().trim();
                String passuser = password.getText().toString().trim();

                // Validate input fields
                if (nameuser.isEmpty() || emailuser.isEmpty() || userusername.isEmpty() || passuser.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // Create a new user object (Model class)
                    Model user = new Model(nameuser, emailuser, userusername, passuser);

                    // Store user data in Firebase Database
                    reference.child(userusername).setValue(user);

                    // Display success message
                    Toast.makeText(SignupActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();

                    // Redirect to MainActivity and pass user data
                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                    intent.putExtra("name", nameuser);
                    intent.putExtra("email", emailuser);
                    intent.putExtra("password", passuser);
                    intent.putExtra("username", userusername);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
