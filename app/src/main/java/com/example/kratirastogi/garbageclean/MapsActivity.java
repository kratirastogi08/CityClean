package com.example.kratirastogi.garbageclean;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
   
UserInfo l;
 double lat1,lng2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.googlemap);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        databaseReference=FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
       /* auth=FirebaseAuth.getInstance();*/
      //  Toast.makeText(this, "mey", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

     /*  FirebaseUser user=auth.getCurrentUser();
        String id= user.getUid();*/
    databaseReference.addValueEventListener(new ValueEventListener() {

        @Override

        public void onDataChange(DataSnapshot dataSnapshot) {
        l=  dataSnapshot.getValue(UserInfo.class);
        lat1=l.getLat();
        lng2=l.getLng();

            Toast.makeText(MapsActivity.this, ""+lat1+" "+lng2, Toast.LENGTH_SHORT).show();
            mMap.addMarker(new MarkerOptions().position(new LatLng(lat1,lng2)).title("Home")).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.garbihouse));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat1,lng2)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat1,lng2),18));
        }


        @Override
        public void onCancelled(DatabaseError databaseError) {

        }

    });


        //Toast.makeText(this, ""+l.email, Toast.LENGTH_SHORT).show();
       // Toast.makeText(this, ""+l.getLat()+" "+l.lng, Toast.LENGTH_SHORT).show();
        // Add a marker in Sydney and move the camera

    }
}
