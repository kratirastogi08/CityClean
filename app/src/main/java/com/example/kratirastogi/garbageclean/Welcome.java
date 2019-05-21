package com.example.kratirastogi.garbageclean;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Welcome extends AppCompatActivity {
SharedPreferences sharedPreferences;
ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        imageView=findViewById(R.id.imageView);
        Glide.with(this).asGif().load(R.mipmap.garbi).into(imageView);
        sharedPreferences=getSharedPreferences("dib",MODE_PRIVATE);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               String s= sharedPreferences.getString("reg","");
                String s1= sharedPreferences.getString("log","");
               if(s.equals("saved")&& !s1.equals("log"))
               {
                   Intent i=new Intent(Welcome.this,HomeActivity.class)  ;
                   startActivity(i);
                   Welcome.this.finish();
               }
             else if(s.equals("saved") && s1.equals("log"))
               {
                   Intent i = new Intent(Welcome.this, OTPActivity.class);
                   startActivity(i);
                   Welcome.this.finish();
               }
               else {
                   Intent i = new Intent(Welcome.this, OTPActivity.class);
                   startActivity(i);
                   Welcome.this.finish();
               }
            }
        },10000);
    }
}
