package com.example.kratirastogi.garbageclean;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CollectorInfo extends AppCompatActivity {
    TextView date, name, numplate, number, time, nam, mobno, plate, textmsg;
    DatabaseReference ref, ref1;
    FirebaseAuth auth;
    CardView car, cardView, card;
    String h, g;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_info);
        date = findViewById(R.id.date);
        name = findViewById(R.id.name);
        numplate = findViewById(R.id.numplate);
        number = findViewById(R.id.number);
        time = findViewById(R.id.time);
        nam = findViewById(R.id.nam);
        mobno = findViewById(R.id.mobno);
        plate = findViewById(R.id.plate);
        car = findViewById(R.id.car);
        cardView = findViewById(R.id.cardView);
        card = findViewById(R.id.card);
        textmsg = findViewById(R.id.textmsg);
        sharedPreferences = getSharedPreferences("details", MODE_PRIVATE);
        h = sharedPreferences.getString("id2", "");
        g = sharedPreferences.getString("id1", "");
        if (h == null && g == null) {
            textmsg.setVisibility(View.VISIBLE);

        } else {

            if (h.equals("2") && g.equals("3")) {
                cardView.setVisibility(View.VISIBLE);
                card.setVisibility(View.VISIBLE);
                car.setVisibility(View.VISIBLE);
                ref = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("collector");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            CollectorData1 c = d.getValue(CollectorData1.class);
                            date.setText(c.getDate());
                            name.setText(c.getName());
                            numplate.setText(c.getNumplate());
                            number.setText(c.getNumber());


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                ref1 = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("collectorschedule");


                ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            CollectorData c1 =d.getValue(CollectorData.class);


                            nam.setText(c1.getName());
                            time.setText(c1.getTime());
                            mobno.setText(c1.getNumber());
                            plate.setText(c1.getNumplate());

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
            else if(h.equals("2"))
            {    cardView.setVisibility(View.VISIBLE);
                card.setVisibility(View.VISIBLE);
                car.setVisibility(View.GONE);
                ref = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("collector");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            CollectorData1 c = d.getValue(CollectorData1.class);
                            date.setText(c.getDate());
                            name.setText(c.getName());
                            numplate.setText(c.getNumplate());
                            number.setText(c.getNumber());


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            else if(g.equals("3"))
            {   car.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.GONE);
            car.setVisibility(View.GONE);
                ref1 = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("collectorschedule");


                ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot d : dataSnapshot.getChildren())
                        {
                            CollectorData c1 = d.getValue(CollectorData.class);

                            car.setVisibility(View.VISIBLE);
                            nam.setText(c1.getName());
                            time.setText(c1.getTime());
                            mobno.setText(c1.getNumber());
                            plate.setText(c1.getNumplate());

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            else
            {    car.setVisibility(View.INVISIBLE);
            cardView.setVisibility(View.INVISIBLE);
            card.setVisibility(View.INVISIBLE);
                textmsg.setVisibility(View.VISIBLE);
            }
        }
    }
}
