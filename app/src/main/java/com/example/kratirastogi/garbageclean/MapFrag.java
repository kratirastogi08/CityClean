package com.example.kratirastogi.garbageclean;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.service.autofill.SaveRequest;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapFrag extends Fragment implements OnMapReadyCallback{
    private GoogleMap mMap;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    UserInfo l;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;
  FirebaseAuth.AuthStateListener authStateListener;
    FloatingActionButton fab1,fab4,fab2,fab3;
    double lat1,lng2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.mapfrag, null);
        fab1=view.findViewById(R.id.fab1);
        fab4=view.findViewById(R.id.fab4);
        fab2=view.findViewById(R.id.fab2);
        fab3=view.findViewById(R.id.fab3);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           Intent i=new Intent(getContext(),CollectorInfo.class);
           startActivity(i);
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),GarbageInfo.class);
                startActivity(i);
            }
        });
        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferences=getActivity().getSharedPreferences("dib", Context.MODE_PRIVATE);
                edit=sharedPreferences.edit();
                edit.putString("log","log");
                edit.apply();
                FirebaseAuth.getInstance().signOut();

                Intent i=new Intent(getContext(),OTPActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),PayTm.class);
                startActivity(i);
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
return  view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

     /*  FirebaseUser user=auth.getCurrentUser();
        String id= user.getUid();*/
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                l = dataSnapshot.getValue(UserInfo.class);
                lat1 = l.getLat();
                lng2 = l.getLng();

               // Toast.makeText(MapsActivity.this, "" + lat1 + " " + lng2, Toast.LENGTH_SHORT).show();
                mMap.addMarker(new MarkerOptions().position(new LatLng(lat1, lng2)).title("Home")).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.address));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat1, lng2)));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat1, lng2), 18));
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


        //Toast.makeText(this, ""+l.email, Toast.LENGTH_SHORT).show();
        // Toast.makeText(this, ""+l.getLat()+" "+l.lng, Toast.LENGTH_SHORT).show();
        // Add a marker in Sydney and move the camera


    }
  /*  public void setupFirebaseListener()
    {
       authStateListener= new FirebaseAuth.AuthStateListener() {
           @Override
           public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               FirebaseUser user=firebaseAuth.getCurrentUser();
               if(user!=null)
               {

               }
               else
               {
                   Intent i=new Intent(getContext(),OTPActivity.class);
                   i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   startActivity(i);
               }
           }
       };
    }*/
}
