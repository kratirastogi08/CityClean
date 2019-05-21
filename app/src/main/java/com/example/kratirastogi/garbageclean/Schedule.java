package com.example.kratirastogi.garbageclean;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;


public class Schedule extends Fragment {

RadioButton five,three,seven,daily;
Button schedule,schedulenow,cancel,can,can2,can1,can0;
String data,strDate,newTime,id,state;
DatabaseReference ref,ref1;
double lat,lng;
String n,add,pay;
ArrayList<Pair> a;
ArrayList<Double> f;
ArrayList<Pair1>k;
TextView text,texto,texti,texto1,texto2;
SharedPreferences sharedPreferences,shared;
SharedPreferences.Editor edit,editor;
  Pair p1;
Calendar calendar;
    private ScheduledExecutorService scheduleTaskExecutor,scheduleTaskExecutor1,scheduleTaskExecutor2;
    static double PI_RAD = Math.PI / 180.0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =inflater.inflate(R.layout.fragment_schedule, container, false);
       five= view.findViewById(R.id.five);
       three =view.findViewById(R.id.three);
       seven=view.findViewById(R.id.seven);
        daily=view.findViewById(R.id.daily);
       cancel=view.findViewById(R.id.cancel);
       text=view.findViewById(R.id.text);
        texto=view.findViewById(R.id.texto);
        texti=view.findViewById(R.id.texti);
        texto1=view.findViewById(R.id.texto1);
        texto2=view.findViewById(R.id.texto2);
        can=view.findViewById(R.id.can);
        can1=view.findViewById(R.id.can1);
        can2=view.findViewById(R.id.can2);
        can0=view.findViewById(R.id.can0);
        sharedPreferences=getActivity().getSharedPreferences("details",Context.MODE_PRIVATE);

        edit=sharedPreferences.edit();
       schedulenow=view.findViewById(R.id.schedulenow);
        a=new ArrayList<>();
        f=new ArrayList<>();
        k=new ArrayList<>();
       ref= FirebaseDatabase.getInstance().getReference("Collector");
       ref1=FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
       ref1.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               UserInfo u=dataSnapshot.getValue(UserInfo.class);
               lat= u.getLat();
               lng=u.getLng();
               n=u.getName();
               add=u.getAdd();
               pay=u.getPaymentStatus();
               id=u.getId();
               state=u.getState();


           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

       schedule=view.findViewById(R.id.schedule);
      String da= sharedPreferences.getString("id","");
     if(da.equals("daily"))
     {
         daily.setEnabled(true);
         daily.setChecked(true);
         texti.setVisibility(View.VISIBLE);
         can0.setVisibility(View.VISIBLE);
         three.setEnabled(false);
         seven.setEnabled(false);
         five.setEnabled(false);
         can0.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 sharedPreferences.edit().remove("id").commit();
                 edit.putString("id","def");
                 edit.commit();
                 sharedPreferences.edit().remove("id2").commit();
                 String i= sharedPreferences.getString("y","");
                 //  scheduleTaskExecutor.shutdownNow();
                 ref.child(i).child("date").child("d1").child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                 ref.child(i).child("date").child("d2").child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                 ref.child(i).child("date").child("d3").child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                 ref1.child("collector").removeValue();


                 five.setEnabled(true);
                 three.setEnabled(true);
                 seven.setEnabled(true);
                 texti.setVisibility(View.GONE);
                 can0.setVisibility(View.GONE);
                 /* Gson gson = new Gson();
                  String json = sharedPreferences.getString("MyObject", "");
                  ScheduledExecutorService obj = gson.fromJson(json, ScheduledExecutorService.class);
                  obj.shutdown();*/
                 Toast.makeText(getContext(), "service cancelled", Toast.LENGTH_SHORT).show();
             }
         });

     }
     else if(da.equals("three"))
      {   three.setEnabled(true);
      three.setChecked(true);
          texto.setVisibility(View.VISIBLE);
          can.setVisibility(View.VISIBLE);
          five.setEnabled(false);
          seven.setEnabled(false);
          daily.setEnabled(false);
          can.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  edit.putString("id","def");
                  edit.apply();
                  sharedPreferences.edit().remove("id2").commit();
   String i= sharedPreferences.getString("y","");
                //  scheduleTaskExecutor.shutdownNow();
                  ref.child(i).child("date").child("d1").child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                  ref.child(i).child("date").child("d2").child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                  ref.child(i).child("date").child("d3").child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                  ref1.child("collector").removeValue();


                daily.setEnabled(true);
                  five.setEnabled(true);
                  seven.setEnabled(true);
                  texto.setVisibility(View.GONE);
                  can.setVisibility(View.GONE);
                 /* Gson gson = new Gson();
                  String json = sharedPreferences.getString("MyObject", "");
                  ScheduledExecutorService obj = gson.fromJson(json, ScheduledExecutorService.class);
                  obj.shutdown();*/
                  Toast.makeText(getContext(), "service cancelled", Toast.LENGTH_SHORT).show();
              }
          });
      }
      else if(da.equals("five"))
      {
          five.setEnabled(true);
          five.setChecked(true);
          texto1.setVisibility(View.VISIBLE);
          can1.setVisibility(View.VISIBLE);
          three.setEnabled(false);
          seven.setEnabled(false);
          daily.setEnabled(false);
          can1.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  edit.putString("id","def");
                  edit.apply();
                  sharedPreferences.edit().remove("id2").commit();
                  String i= sharedPreferences.getString("y","");
                  //  scheduleTaskExecutor.shutdownNow();
                  ref.child(i).child("date").child("d1").child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                  ref.child(i).child("date").child("d2").child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                  ref.child(i).child("date").child("d3").child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                  ref1.child("collector").removeValue();


                  daily.setEnabled(true);
                  three.setEnabled(true);
                  seven.setEnabled(true);
                  texto1.setVisibility(View.GONE);
                  can1.setVisibility(View.GONE);
                 /* Gson gson = new Gson();
                  String json = sharedPreferences.getString("MyObject", "");
                  ScheduledExecutorService obj = gson.fromJson(json, ScheduledExecutorService.class);
                  obj.shutdown();*/
                  Toast.makeText(getContext(), "service cancelled", Toast.LENGTH_SHORT).show();
              }
          });
      }

         else if(da.equals("seven"))
          {

              seven.setEnabled(true);
              seven.setChecked(true);
              texto2.setVisibility(View.VISIBLE);
              can2.setVisibility(View.VISIBLE);
              three.setEnabled(false);
              five.setEnabled(false);
              daily.setEnabled(false);
              can2.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      edit.putString("id","def");
                      edit.apply();
                      sharedPreferences.edit().remove("id2").commit();
                      String i= sharedPreferences.getString("y","");
                      //  scheduleTaskExecutor.shutdownNow();
                      ref.child(i).child("date").child("d1").child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                      ref.child(i).child("date").child("d2").child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                      ref.child(i).child("date").child("d3").child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                      ref1.child("collector").removeValue();


                      daily.setEnabled(true);
                      three.setEnabled(true);
                      five.setEnabled(true);
                      texto2.setVisibility(View.GONE);
                      can2.setVisibility(View.GONE);
                 /* Gson gson = new Gson();
                  String json = sharedPreferences.getString("MyObject", "");
                  ScheduledExecutorService obj = gson.fromJson(json, ScheduledExecutorService.class);
                  obj.shutdown();*/
                      Toast.makeText(getContext(), "service cancelled", Toast.LENGTH_SHORT).show();
                  }
              });
          }
  /* else {
         can0.setVisibility(View.INVISIBLE);
         texti.setVisibility(View.INVISIBLE);
         can.setVisibility(View.INVISIBLE);
         texto.setVisibility(View.INVISIBLE);
         can1.setVisibility(View.INVISIBLE);
         texto1.setVisibility(View.INVISIBLE);
         can2.setVisibility(View.INVISIBLE);
         texto2.setVisibility(View.INVISIBLE);

          }*/
              schedule.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      if(daily.isChecked())
                      {    three.setEnabled(false);
                          five.setEnabled(false);
                          seven.setEnabled(false);
            ref1.child("state").setValue("1");
           state="1";
                          scheduleTaskExecutor = Executors.newScheduledThreadPool(1);

                          //Schedule a task to run every 5 seconds (or however long you want)
                          scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
                              @Override
                              public void run() {
                                  // Do stuff here!
                                  /**/
                                  getActivity().runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          // Do stuff to update UI here!
                                          final java.util.Calendar currentdate = java.util.Calendar.getInstance();
                                          final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                          //  currentdate.add(java.util.Calendar.DAY_OF_MONTH,1);
                                          final String newdate = sdf.format(currentdate.getTime());

                                          ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                 @Override
                                                                                 public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                     for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                                                                         Data1 d1 = ds.getValue(Data1.class);

                                                                                         com.example.kratirastogi.garbageclean.Date d = d1.getDate();
                                                                                         Date1 date1 = d.getD1();
                                                                                         Date2 date2 = d.getD2();
                                                                                         Date3 date3 = d.getD3();


                                                                                         Toast.makeText(getContext(), "" + date3.getDate(), Toast.LENGTH_SHORT).show();
                                                                                         Log.d("ht", date3.getDate());
                                                                                         if (newdate.equals(date1.getDate())) {
                                                                                             a.add(new Pair("d1", d1.getName(), date1.getLat(), date1.getLng(), d1.getId(), d1.getNumplate(), d1.getNumber(), newdate));
                                                                                             double r = distance(lat, lng, date1.getLat(), date1.getLng());
                                                                                             f.add(r);

                                                                                         } else if (newdate.equals(date2.getDate())) {
                                                                                             a.add(new Pair("d2", d1.getName(), date2.getLat(), date2.getLng(), d1.getId(), d1.getNumplate(), d1.getNumber(), newdate));
                                                                                             double r = distance(lat, lng, date2.getLat(), date2.getLng());
                                                                                             f.add(r);
                                                                                         } else if (newdate.equals(date3.getDate())) {
                                                                                             a.add(new Pair("d3", d1.getName(), date3.getLat(), date3.getLng(), d1.getId(), d1.getNumplate(), d1.getNumber(), newdate));
                                                                                             double r = distance(lat, lng, date3.getLat(), date3.getLng());
                                                                                             f.add(r);
                                                                                         }

                                                                                     }

                                                                                     Log.d("jh", String.valueOf(f.size()));

                                                                                     double s = f.get(0);
                                                                                     int pos = 0;
                                                                                     for (int i = 1; i < f.size(); i++) {

                                                                                         if (s > f.get(i)) {
                                                                                             s = f.get(i);
                                                                                             pos = i;
                                                                                         }


                                                                                     }
                                                                                     p1 = a.get(pos);
                                                                                     Log.d("ss", p1.id);
                                                                                     ref.child(p1.id).child("date").child(p1.date).child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new Data(n, add,pay,id,state));
                                                                                     edit.putString("y", p1.id);
                                                                                     edit.apply();
                                                                                     ref1.child("collector").child(p1.id).setValue(new CollectorData1(p1.name, p1.numplate, p1.number, newdate));
                                                                                     f.clear();
                                                                                     a.clear();

                                                                                 }


                                                                                 @Override
                                                                                 public void onCancelled(DatabaseError databaseError) {

                                                                                 }


                                                                             }

                                          );


                                          edit.putString("id", "daily");
                                          edit.putString("id2", "2");
                                          edit.apply();

                                          Toast.makeText(getContext(), "Its been 5 seconds", Toast.LENGTH_SHORT).show();
                                      }
                                  });

                              }
                          }, 0, 1, TimeUnit.DAYS);
                         /* Gson gson = new Gson();
                          String json = gson.toJson(scheduleTaskExecutor);
                          edit.putString("MyObject", json);
                          edit.commit();*/
                          texti.setVisibility(View.VISIBLE);
                          can0.setVisibility(View.VISIBLE);
                          can0.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View v) {
                                  ref.child(p1.id).child("date").child("d1").child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                                  ref.child(p1.id).child("date").child("d2").child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                                  ref.child(p1.id).child("date").child("d3").child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                                  ref1.child("collector").removeValue();
                                  scheduleTaskExecutor.shutdown();
                                  texti.setVisibility(View.GONE);
                                  can0.setVisibility(View.GONE);
                                  three.setEnabled(true);
                                  five.setEnabled(true);
                                  seven.setEnabled(true);
                                  edit.putString("id","def");
                                  edit.commit();
                                  sharedPreferences.edit().remove("id2").commit();

                                  Toast.makeText(getContext(), "service cancelled", Toast.LENGTH_SHORT).show();

                              }
                          });
                      }
                    else  if (three.isChecked()) {
                          // data=three.getText().toString();
                          five.setEnabled(false);
                          seven.setEnabled(false);
                          daily.setEnabled(false);
                          ref1.child("state").setValue("3");
                          state="3";

                          scheduleTaskExecutor = Executors.newScheduledThreadPool(3);

                          //Schedule a task to run every 5 seconds (or however long you want)
                          scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
                              @Override
                              public void run() {
                                  // Do stuff here!
                                  /**/
                                  getActivity().runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          // Do stuff to update UI here!
                                          final java.util.Calendar currentdate = java.util.Calendar.getInstance();
                                          final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                          //  currentdate.add(java.util.Calendar.DAY_OF_MONTH,1);
                                          final String newdate = sdf.format(currentdate.getTime());

                                          ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                 @Override
                                                                                 public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                     for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                                                                         Data1 d1 = ds.getValue(Data1.class);

                                                                                         com.example.kratirastogi.garbageclean.Date d = d1.getDate();
                                                                                         Date1 date1 = d.getD1();
                                                                                         Date2 date2 = d.getD2();
                                                                                         Date3 date3 = d.getD3();


                                                                                         Toast.makeText(getContext(), "" + date3.getDate(), Toast.LENGTH_SHORT).show();
                                                                                         Log.d("ht", date3.getDate());
                                                                                         if (newdate.equals(date1.getDate())) {
                                                                                             a.add(new Pair("d1", d1.getName(), date1.getLat(), date1.getLng(), d1.getId(), d1.getNumplate(), d1.getNumber(), newdate));
                                                                                             double r = distance(lat, lng, date1.getLat(), date1.getLng());
                                                                                             f.add(r);

                                                                                         } else if (newdate.equals(date2.getDate())) {
                                                                                             a.add(new Pair("d2", d1.getName(), date2.getLat(), date2.getLng(), d1.getId(), d1.getNumplate(), d1.getNumber(), newdate));
                                                                                             double r = distance(lat, lng, date2.getLat(), date2.getLng());
                                                                                             f.add(r);
                                                                                         } else if (newdate.equals(date3.getDate())) {
                                                                                             a.add(new Pair("d3", d1.getName(), date3.getLat(), date3.getLng(), d1.getId(), d1.getNumplate(), d1.getNumber(), newdate));
                                                                                             double r = distance(lat, lng, date3.getLat(), date3.getLng());
                                                                                             f.add(r);
                                                                                         }

                                                                                     }

                                                                                     Log.d("jh", String.valueOf(f.size()));

                                                                                     double s = f.get(0);
                                                                                     int pos = 0;
                                                                                     for (int i = 1; i < f.size(); i++) {

                                                                                         if (s > f.get(i)) {
                                                                                             s = f.get(i);
                                                                                             pos = i;
                                                                                         }


                                                                                     }
                                                                                     p1 = a.get(pos);
                                                                                     Log.d("ss", p1.id);
                                                                                     ref.child(p1.id).child("date").child(p1.date).child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new Data(n, add,pay,id,state));
                                                                                     edit.putString("y", p1.id);
                                                                                     edit.apply();
                                                                                     ref1.child("collector").child(p1.id).setValue(new CollectorData1(p1.name, p1.numplate, p1.number, newdate));
                                                                                     f.clear();
                                                                                     a.clear();

                                                                                 }


                                                                                 @Override
                                                                                 public void onCancelled(DatabaseError databaseError) {

                                                                                 }


                                                                             }

                                          );


                                          edit.putString("id", "three");
                                          edit.putString("id2", "2");
                                          edit.apply();

                                          Toast.makeText(getContext(), "Its been 5 seconds", Toast.LENGTH_SHORT).show();
                                      }
                                  });

                              }
                          }, 0, 3, TimeUnit.DAYS);
                         /* Gson gson = new Gson();
                          String json = gson.toJson(scheduleTaskExecutor);
                          edit.putString("MyObject", json);
                          edit.commit();*/
                          texto.setVisibility(View.VISIBLE);
                          can.setVisibility(View.VISIBLE);
                          can.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View v) {
                                  ref.child(p1.id).child("date").child("d1").child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                                  ref.child(p1.id).child("date").child("d2").child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                                  ref.child(p1.id).child("date").child("d3").child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                                  ref1.child("collector").removeValue();
                                  scheduleTaskExecutor.shutdown();
                                  texto.setVisibility(View.GONE);
                                  can.setVisibility(View.GONE);
                                  five.setEnabled(true);
                                  seven.setEnabled(true);
                                  daily.setEnabled(true);
                                  edit.putString("id","def");
                                  edit.commit();
                                  sharedPreferences.edit().remove("id2").commit();
                                  Toast.makeText(getContext(), "service cancelled", Toast.LENGTH_SHORT).show();

                              }
                          });

                      } else if (five.isChecked()) {
                          // data=five.getText().toString();
                          three.setEnabled(false);
                          daily.setEnabled(false);
                          seven.setEnabled(false);
                          ref1.child("state").setValue("5");
                          state="5";

                          scheduleTaskExecutor = Executors.newScheduledThreadPool(5);

                          //Schedule a task to run every 5 seconds (or however long you want)
                          scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
                              @Override
                              public void run() {
                                  // Do stuff here!
                                  /**/
                                  getActivity().runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          // Do stuff to update UI here!
                                          final java.util.Calendar currentdate = java.util.Calendar.getInstance();
                                          final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                          //  currentdate.add(java.util.Calendar.DAY_OF_MONTH,1);
                                          final String newdate = sdf.format(currentdate.getTime());

                                          ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                 @Override
                                                                                 public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                     for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                                                                         Data1 d1 = ds.getValue(Data1.class);

                                                                                         com.example.kratirastogi.garbageclean.Date d = d1.getDate();
                                                                                         Date1 date1 = d.getD1();
                                                                                         Date2 date2 = d.getD2();
                                                                                         Date3 date3 = d.getD3();


                                                                                         Toast.makeText(getContext(), "" + date3.getDate(), Toast.LENGTH_SHORT).show();
                                                                                         Log.d("ht", date3.getDate());
                                                                                         if (newdate.equals(date1.getDate())) {
                                                                                             a.add(new Pair("d1", d1.getName(), date1.getLat(), date1.getLng(), d1.getId(), d1.getNumplate(), d1.getNumber(), newdate));
                                                                                             double r = distance(lat, lng, date1.getLat(), date1.getLng());
                                                                                             f.add(r);

                                                                                         } else if (newdate.equals(date2.getDate())) {
                                                                                             a.add(new Pair("d2", d1.getName(), date2.getLat(), date2.getLng(), d1.getId(), d1.getNumplate(), d1.getNumber(), newdate));
                                                                                             double r = distance(lat, lng, date2.getLat(), date2.getLng());
                                                                                             f.add(r);
                                                                                         } else if (newdate.equals(date3.getDate())) {
                                                                                             a.add(new Pair("d3", d1.getName(), date3.getLat(), date3.getLng(), d1.getId(), d1.getNumplate(), d1.getNumber(), newdate));
                                                                                             double r = distance(lat, lng, date3.getLat(), date3.getLng());
                                                                                             f.add(r);
                                                                                         }

                                                                                     }

                                                                                     Log.d("jh", String.valueOf(f.size()));

                                                                                     double s = f.get(0);
                                                                                     int pos = 0;
                                                                                     for (int i = 1; i < f.size(); i++) {

                                                                                         if (s > f.get(i)) {
                                                                                             s = f.get(i);
                                                                                             pos = i;
                                                                                         }


                                                                                     }
                                                                                     p1 = a.get(pos);
                                                                                     Log.d("ss", p1.id);
                                                                                     ref.child(p1.id).child("date").child(p1.date).child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new Data(n, add,pay,id,state));
                                                                                     edit.putString("y", p1.id);
                                                                                     edit.apply();
                                                                                     ref1.child("collector").child(p1.id).setValue(new CollectorData1(p1.name, p1.numplate, p1.number, newdate));
                                                                                     f.clear();
                                                                                     a.clear();

                                                                                 }


                                                                                 @Override
                                                                                 public void onCancelled(DatabaseError databaseError) {

                                                                                 }


                                                                             }

                                          );


                                          edit.putString("id", "five");
                                          edit.putString("id2", "2");
                                          edit.apply();

                                          Toast.makeText(getContext(), "Its been 5 seconds", Toast.LENGTH_SHORT).show();
                                      }
                                  });

                              }
                          }, 0, 5, TimeUnit.DAYS);
                         /* Gson gson = new Gson();
                          String json = gson.toJson(scheduleTaskExecutor);
                          edit.putString("MyObject", json);
                          edit.commit();*/
                          texto1.setVisibility(View.VISIBLE);
                          can1.setVisibility(View.VISIBLE);
                          can1.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View v) {
                                  ref.child(p1.id).child("date").child("d1").child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                                  ref.child(p1.id).child("date").child("d2").child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                                  ref.child(p1.id).child("date").child("d3").child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                                  ref1.child("collector").removeValue();
                                  scheduleTaskExecutor.shutdown();
                                  texto1.setVisibility(View.GONE);
                                  can1.setVisibility(View.GONE);
                                  three.setEnabled(true);
                                  daily.setEnabled(true);
                                  seven.setEnabled(true);
                                  edit.putString("id","def");
                                  edit.commit();
                                  sharedPreferences.edit().remove("id2").commit();
                                  Toast.makeText(getContext(), "service cancelled", Toast.LENGTH_SHORT).show();

                              }
                          });


                      } else if (seven.isChecked()) {
                          // data=seven.getText().toString();
                          three.setEnabled(false);
                          five.setEnabled(false);
                          daily.setEnabled(false);
                          ref1.child("state").setValue("7");
                          state="7";

                          scheduleTaskExecutor = Executors.newScheduledThreadPool(7);

                          //Schedule a task to run every 5 seconds (or however long you want)
                          scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
                              @Override
                              public void run() {
                                  // Do stuff here!
                                  /**/
                                  getActivity().runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          // Do stuff to update UI here!
                                          final java.util.Calendar currentdate = java.util.Calendar.getInstance();
                                          final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                          //  currentdate.add(java.util.Calendar.DAY_OF_MONTH,1);
                                          final String newdate = sdf.format(currentdate.getTime());

                                          ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                 @Override
                                                                                 public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                     for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                                                                         Data1 d1 = ds.getValue(Data1.class);

                                                                                         com.example.kratirastogi.garbageclean.Date d = d1.getDate();
                                                                                         Date1 date1 = d.getD1();
                                                                                         Date2 date2 = d.getD2();
                                                                                         Date3 date3 = d.getD3();


                                                                                         Toast.makeText(getContext(), "" + date3.getDate(), Toast.LENGTH_SHORT).show();
                                                                                         Log.d("ht", date3.getDate());
                                                                                         if (newdate.equals(date1.getDate())) {
                                                                                             a.add(new Pair("d1", d1.getName(), date1.getLat(), date1.getLng(), d1.getId(), d1.getNumplate(), d1.getNumber(), newdate));
                                                                                             double r = distance(lat, lng, date1.getLat(), date1.getLng());
                                                                                             f.add(r);

                                                                                         } else if (newdate.equals(date2.getDate())) {
                                                                                             a.add(new Pair("d2", d1.getName(), date2.getLat(), date2.getLng(), d1.getId(), d1.getNumplate(), d1.getNumber(), newdate));
                                                                                             double r = distance(lat, lng, date2.getLat(), date2.getLng());
                                                                                             f.add(r);
                                                                                         } else if (newdate.equals(date3.getDate())) {
                                                                                             a.add(new Pair("d3", d1.getName(), date3.getLat(), date3.getLng(), d1.getId(), d1.getNumplate(), d1.getNumber(), newdate));
                                                                                             double r = distance(lat, lng, date3.getLat(), date3.getLng());
                                                                                             f.add(r);
                                                                                         }

                                                                                     }

                                                                                     Log.d("jh", String.valueOf(f.size()));

                                                                                     double s = f.get(0);
                                                                                     int pos = 0;
                                                                                     for (int i = 1; i < f.size(); i++) {

                                                                                         if (s > f.get(i)) {
                                                                                             s = f.get(i);
                                                                                             pos = i;
                                                                                         }


                                                                                     }
                                                                                     p1 = a.get(pos);
                                                                                     Log.d("ss", p1.id);
                                                                                     ref.child(p1.id).child("date").child(p1.date).child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new Data(n, add,pay,id,state));
                                                                                     edit.putString("y", p1.id);
                                                                                     edit.apply();
                                                                                     ref1.child("collector").child(p1.id).setValue(new CollectorData1(p1.name, p1.numplate, p1.number, newdate));
                                                                                     f.clear();
                                                                                     a.clear();

                                                                                 }


                                                                                 @Override
                                                                                 public void onCancelled(DatabaseError databaseError) {

                                                                                 }


                                                                             }

                                          );


                                          edit.putString("id", "seven");
                                          edit.putString("id2", "2");
                                          edit.apply();

                                          Toast.makeText(getContext(), "Its been 5 seconds", Toast.LENGTH_SHORT).show();
                                      }
                                  });

                              }
                          }, 0, 7, TimeUnit.DAYS);
                         /* Gson gson = new Gson();
                          String json = gson.toJson(scheduleTaskExecutor);
                          edit.putString("MyObject", json);
                          edit.commit();*/
                          texto.setVisibility(View.VISIBLE);
                          can2.setVisibility(View.VISIBLE);
                          can2.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View v) {
                                  ref.child(p1.id).child("date").child("d1").child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                                  ref.child(p1.id).child("date").child("d2").child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                                  ref.child(p1.id).child("date").child("d3").child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                                  ref1.child("collector").removeValue();
                                  scheduleTaskExecutor.shutdown();
                                  texto2.setVisibility(View.GONE);
                                  can2.setVisibility(View.GONE);
                                  three.setEnabled(true);
                                  five.setEnabled(true);
                                  daily.setEnabled(true);
                                  edit.putString("id","def");
                                  edit.commit();
                                  sharedPreferences.edit().remove("id2").commit();
                                  Toast.makeText(getContext(), "service cancelled", Toast.LENGTH_SHORT).show();

                              }
                          });


                      } else {
                          data = null;
                      }
                  }
              });

       schedulenow.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
  ref.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
          for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
          {
           Data1 d=dataSnapshot1.getValue(Data1.class);
           k.add(new Pair1(d.getLat(),d.getLng(),d.getStatus(),d.getName(),d.getId(),d.getNumplate(),d.getNumber()));
           double r=distance(lat,lng,d.getLat(),d.getLng());
              Toast.makeText(getContext(), ""+r, Toast.LENGTH_SHORT).show();
           f.add(r);
              Log.d("jgf",String.valueOf(f.size()));
          }

          double s = f.get(0);
          int pos=0;
          for (int i = 1; i < f.size(); i++) {
  Pair1 u=k.get(i);
              if (s > f.get(i) &&  u.status.equals("Inactive")) {
                  s = f.get(i);
                  pos = i;
              }


          }
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
               calendar = Calendar.getInstance();
          }
          SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");


          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
               strDate = mdformat.format(calendar.getTime());
              Toast.makeText(getContext(), ""+strDate, Toast.LENGTH_SHORT).show();
          }

          final Pair1 p = k.get(pos);
          double b=f.get(pos);
          int n1= (int) ((b/30)*60);
          SimpleDateFormat df = new SimpleDateFormat("HH:mm");
          Date d = null;
          try {
              d = df.parse(strDate);
          } catch (ParseException e) {
              e.printStackTrace();
          }
          Calendar cal = null;
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
              cal = Calendar.getInstance();
          }
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
              cal.setTime(d);
              cal.add(Calendar.MINUTE, n1);
              newTime = df.format(cal.getTime());
          }


       ref1.child("collectorschedule").child(p.id).setValue(new CollectorData(p.name,newTime,p.numplate,p.number));
      ref.child(p.id).child("status").setValue("Busy");
      ref.child(p.id).child("urgentuser").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new Data(n, add,pay,id,state));
      edit.putString("id1","3");
      edit.commit();
          f.clear();

                  k.clear();
                  text.setVisibility(View.VISIBLE);
                  cancel.setVisibility(View.VISIBLE);


                  cancel.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          ref1.child("collectorschedule").removeValue();
                          ref.child(p.id).child("urgentuser").removeValue();
                          sharedPreferences.edit().remove("id1").commit();
                          text.setVisibility(View.INVISIBLE);
                          cancel.setVisibility(View.INVISIBLE);
                      }
                  });
          Handler handler=new Handler();
          handler.postDelayed(new Runnable() {
              @Override
              public void run() {
                  text.setVisibility(View.GONE);
                 cancel.setVisibility(View.GONE);
              }
          },60000);
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
  });
           }
       });
       return view;
    }
   /* private double distance ( double lat1, double lon1, double lat2, double lon2)
    {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return dist;
    }

    private double rad2deg ( double rad){
        return (rad * 180.0 / Math.PI);
    }

    private double deg2rad ( double deg){
        return (deg * Math.PI / 180.0);
    }*/
   public double distance(double lat1, double long1, double lat2, double long2) {
       double phi1 = lat1 * PI_RAD;
       double phi2 = lat2 * PI_RAD;
       double lam1 = long1 * PI_RAD;
       double lam2 = long2 * PI_RAD;


       return 6371.01 * acos(sin(phi1) * sin(phi2) + cos(phi1) * cos(phi2) * cos(lam2 - lam1));
   }

}
class Pair
{
    String date,name,numplate,number,d;
    double lat,lng;

   String id;
    public Pair(String date, String name, double lat, double lng,String id,String numplate,String number,String d) {
        this.date = date;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.id=id;
        this.numplate=numplate;
        this.number=number;
        this.d=d;



    }

}
class Pair1
{
    double lat,lng;
    String id;
    String status,name,numplate,number;

    public Pair1(double lat, double lng, String status,String name,String id,String numplate,String number) {
        this.lat = lat;
        this.lng = lng;
        this.status = status;
        this.name=name;
        this.id=id;
        this.numplate=numplate;
        this.number=number;

    }
}