package com.nayuta.gdcmnsjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class Signup extends AppCompatActivity {

    EditText fname,lname,em,phonenum,dob,pass,cpass;
    RadioGroup gender;
    RadioButton usergender;
    Button signup,selectDate;

    DatabaseReference ref;
    Calendar myCalendar;
    int day,month,year;
    User user;
    long maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        AddUser();
    };

    public void AddUser(){
        fname =(EditText)findViewById(R.id.firstname);
        lname =(EditText)findViewById(R.id.lastname);
        em =(EditText)findViewById(R.id.dbEmail);
        phonenum =(EditText)findViewById(R.id.phone_num);
        selectDate = (Button)findViewById(R.id.select_dob);
        dob = (EditText)findViewById(R.id.dob);

        myCalendar = Calendar.getInstance();
        day = myCalendar.get(Calendar.DAY_OF_MONTH);
        month = myCalendar.get(Calendar.MONTH);
        month = month;
        year = myCalendar.get(Calendar.YEAR);
        dob.setText(month + "/" + day + "/" + year);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(Signup.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month;
                        dob.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                },year,month,day);
                dpd.show();
            }
        });

        pass =(EditText) findViewById(R.id.password);
        cpass = (EditText)findViewById(R.id.confirm_password);
        gender = (RadioGroup)findViewById(R.id.gender);
        signup =(Button)findViewById(R.id.register_button);



        user = new User();
        ref = FirebaseDatabase.getInstance().getReference().child("User");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    maxid=(dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int genderSelected = gender.getCheckedRadioButtonId();
                usergender = (RadioButton)findViewById(genderSelected);
                user.setFirstname(fname.getText().toString().trim());
                user.setLastname(lname.getText().toString().trim());
                user.setEmail(em.getText().toString().trim());
                user.setPhoneNumber(phonenum.getText().toString().trim());
                user.setDateOfBirth(dob.getText().toString().trim());
                user.setGender(usergender.getText().toString().trim());
                user.setPassword(pass.getText().toString().trim());
                String pass1 = pass.getText().toString().trim();
                String pass2 = cpass.getText().toString().trim();


                if (fname.length() == 0 || lname.length() == 0 || em.length() == 0 ||  pass.length() == 0 || cpass.length() == 0){
                    Toast.makeText(Signup.this,"Empty Fields found!",Toast.LENGTH_LONG).show();
                    return;
                }
                else if (phonenum.length() < 11){
                    Toast.makeText(Signup.this,"Incorrect Phone number",Toast.LENGTH_LONG).show();
                    return;
                }
                else if (phonenum.length() > 13){
                    Toast.makeText(Signup.this,"Incorrect Phone number",Toast.LENGTH_LONG).show();
                    return;
                }
                else if ( pass.length() <=5 ){
                    Toast.makeText(Signup.this,"Password required 6 characters!",Toast.LENGTH_LONG).show();
                    return;
                }

                else{
                    ref.child(String.valueOf(maxid+1)).setValue(user);
                    //ref.push().setValue(user);
                    Toast.makeText(Signup.this,"Registered Successfully!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Signup.this, MainActivity.class);
                    startActivity(intent);
                }


            }
        });

    }
}

