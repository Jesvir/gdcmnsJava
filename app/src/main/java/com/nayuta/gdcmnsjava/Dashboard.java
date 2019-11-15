package com.nayuta.gdcmnsjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {

    TextView led;
    TextView humidity;
    TextView temperature;
    DatabaseReference ref2;
    Button btnOff;
    Button btnOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard3);

        btnOn = (Button)findViewById(R.id.onButton);
        btnOff = (Button)findViewById(R.id.offButton);
        temperature = (TextView)findViewById(R.id.temp3);
        humidity = (TextView)findViewById(R.id.hum);
        led = (TextView)findViewById(R.id.dbEmail);
        ref2 = FirebaseDatabase.getInstance().getReference();
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String leed = dataSnapshot.child("LED_STATUS").getValue().toString();
                String status = dataSnapshot.child("Temperature").getValue().toString();
                String status2 = dataSnapshot.child("Humidity").getValue().toString();
                temperature.setText(status+" °C");
                humidity.setText(status2 +" RH");
                led.setText(leed);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Dashboard.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("LED_STATUS");
                myRef.setValue(1);
            }
        });

        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("LED_STATUS");
                myRef.setValue(0);
            }
        });

    }




}
