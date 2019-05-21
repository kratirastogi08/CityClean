package com.example.kratirastogi.garbageclean;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Registration extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView;
    EditText pass, address, email,name;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    Button reg;
    double latitude, longitude;
    String a ,p,e,n;
    LatLng l;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        imageView = findViewById(R.id.imageView);
        Glide.with(this).asGif().load(R.mipmap.garbi).into(imageView);
        ImageView t = findViewById(R.id.imageView9);
        t.setBackgroundColor(Color.argb(50, 255, 140, 0));
        pass = findViewById(R.id.pass);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);
        name=findViewById(R.id.name);
        reg = findViewById(R.id.reg);
        reg.setOnClickListener(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        auth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View v) {
         e = email.getText().toString();
         p = pass.getText().toString();
         a = address.getText().toString();
         n=name.getText().toString();
        if (TextUtils.isEmpty(n)) {
            name.setError("Please enter your name");
        }
        else if (TextUtils.isEmpty(e)) {
            email.setError("Please enter your email");
        }
        else if (TextUtils.isEmpty(p)) {
            pass.setError("Please enter password");
        } else if (pass.length() < 6) {
            Toast.makeText(this, "password must contain atleast 6 characters", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(a)) {
            address.setError("Please enter your home address");
        } else {

   l= getLocationFromAddress(this,a);
            FirebaseUser user = auth.getCurrentUser();
            String id = user.getUid();
            databaseReference.child("Users").child(id).setValue(new UserInfo(e, p, l.latitude,l.longitude,"default",n,a,"False",FirebaseAuth.getInstance().getCurrentUser().getUid(),"Not selected")).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Registration.this, "info saved", Toast.LENGTH_SHORT).show();
                        SharedPreferences s=getSharedPreferences("data",MODE_PRIVATE);
                        SharedPreferences.Editor ed= s.edit();
                        ed.putString("pass",p);
                        ed.apply();
                        Intent i=new Intent(Registration.this,HomeActivity.class);
                        startActivity(i);
                        Registration.this.finish();
                        /*SharedPreferences.Editor sEditor=sp.edit();
                        sEditor.putInt("Str",2);
                        sEditor.apply();

                        Intent i=new Intent(Registration.this,HomeActivity.class);
                        startActivity(i);
                        finish();*/
                    } else {
                        Toast.makeText(Registration.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


        }
    public LatLng getLocationFromAddress(Context context,String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }
    }




