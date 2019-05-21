package com.example.kratirastogi.garbageclean;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

public class OTPActivity extends AppCompatActivity implements View.OnClickListener{
ImageView imageView;
FirebaseAuth auth;
EditText mobilen;
Button but;
String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        imageView=findViewById(R.id.imageView);
        Glide.with(this).asGif().load(R.mipmap.garbi).into(imageView);
        ImageView t=findViewById(R.id.imageView9);
        t.setBackgroundColor(Color.argb(50,255,140,0));
        mobilen=findViewById(R.id.mobilen);
        auth=FirebaseAuth.getInstance();
        but=findViewById(R.id.but);
        but.setOnClickListener(this);

    }
    public void sendsms(View view)
    {  number=mobilen.getText().toString();
        Intent i=new Intent(this,OTPActivity2.class);
        i.putExtra("code",number);
        startActivity(i);
        finish();

    }
    @Override
    public void onClick(View v) {
sendsms(v);
    }
}
