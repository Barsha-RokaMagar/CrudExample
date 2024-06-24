package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    TextView profileusername, profilename, profilepass, profileemail;
    TextView titlename, titleusername;
    Button signout, editprofile, deleteprofile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        profilename = findViewById(R.id.profilename);
        profileusername = findViewById(R.id.profileusername);
        profileemail = findViewById(R.id.profileemail);
        profilepass = findViewById(R.id.profilepass);

        titlename = findViewById(R.id.titlename);
        titleusername = findViewById(R.id.titleusername);

        signout = findViewById(R.id.SignOut);
        editprofile = findViewById(R.id.editprofile);
        deleteprofile = findViewById(R.id.deleteprofile);
        showDataUser();

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        deleteprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mainusername = profilename.getText().toString().trim();
                DatabaseReference reference = FirebaseDatabase.getInstance()
                        .getReference("users");

                Query checkuserdata= reference.orderByChild("username")
                        .equalTo(mainusername);
                checkuserdata.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            snapshot.child(mainusername).getRef().removeValue();
                            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passDataUser();

            }
        });
    }
    public void passDataUser(){
        String mainusername = profilename.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("users");

        Query checkuserdata= reference.orderByChild("username")
                .equalTo(mainusername);
        checkuserdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String usernameDB = snapshot.child(mainusername).child("username")
                            .getValue(String.class);
                    String passDB = snapshot.child(mainusername).child("password")
                            .getValue(String.class);
                    String nameDB = snapshot.child(mainusername).child("name")
                            .getValue(String.class);
                    String emailDB = snapshot.child(mainusername).child("email")
                            .getValue(String.class);
                    Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
                    intent.putExtra("name", nameDB);
                    intent.putExtra("email", emailDB);
                    intent.putExtra("pass", passDB);
                    intent.putExtra("username", usernameDB);
                    startActivity(intent);
                    finish();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void showDataUser(){
        Intent intent = getIntent();
        titlename.setText("Welcome" +intent.getStringExtra("name"));
        titleusername.setText(intent.getStringExtra("username"));
        profileusername.setText(intent.getStringExtra("username"));
        profileemail.setText(intent.getStringExtra("email"));
        profilename.setText(intent.getStringExtra("name"));
        profilepass.setText(intent.getStringExtra("password"));


    }
}