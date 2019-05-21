package com.example.kratirastogi.garbageclean;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayTm extends AppCompatActivity {

    Button paytmButton;
    EditText amountText;
    DatabaseReference databaseReference;
  SharedPreferences sharedPreferences,shared;
  SharedPreferences.Editor editor;
  String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_tm);
        shared=getSharedPreferences("details",MODE_PRIVATE);
        id=shared.getString("y","0");

         sharedPreferences=getSharedPreferences("dd",MODE_PRIVATE);


        init();
    }

    private void init() {
        paytmButton = findViewById(R.id.paytm);
        amountText = findViewById(R.id.ed);
        paytmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PayTm.this, "hhh", Toast.LENGTH_SHORT).show();
                processPaytm();
            }
        });
    }

    private void processPaytm() {

        String custID = generateString();
        String orderID = generateString();
        String callBackurl = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=" + orderID;

        final Paytm1 paytm = new Paytm1(
                "nQhoju03065374131878",
                "WAP",
                amountText.getText().toString().trim(),
                "WEBSTAGING",
                callBackurl,
                "Retail",
                orderID,
                custID
        );

        WebServiceCaller.getClient().getChecksum(paytm.getmId(), paytm.getOrderId(), paytm.getCustId()
                , paytm.getChannelId(), paytm.getTxnAmount(), paytm.getWebsite(), paytm.getCallBackUrl(), paytm.getIndustryTypeId()
        ).enqueue(new Callback<Checksum>() {
            @Override
            public void onResponse(Call<Checksum> call, Response<Checksum> response) {
                if (response.isSuccessful()) {
                    processToPay(response.body().getChecksumHash(),paytm);
                }
            }

            @Override
            public void onFailure(Call<Checksum> call, Throwable t) {

            }
        });


    }

    private void processToPay(String checksumHash, Paytm1 paytm) {
        PaytmPGService Service = PaytmPGService.getStagingService();

        HashMap<String, String> paramMap = new HashMap<String,String>();
        paramMap.put( "MID" , paytm.getmId());
// Key in your staging and production MID available in your dashboard
        paramMap.put( "ORDER_ID" , paytm.getOrderId());
        paramMap.put( "CUST_ID" , paytm.getCustId());
        paramMap.put( "CHANNEL_ID" , paytm.getChannelId());
        paramMap.put( "TXN_AMOUNT" , paytm.getTxnAmount());
        paramMap.put( "WEBSITE" , paytm.getWebsite());
// This is the staging value. Production value is available in your dashboard
        paramMap.put( "INDUSTRY_TYPE_ID" , paytm.getIndustryTypeId());
// This is the staging value. Production value is available in your dashboard
        paramMap.put( "CALLBACK_URL", paytm.getCallBackUrl());
        paramMap.put( "CHECKSUMHASH" , checksumHash);
        PaytmOrder Order = new PaytmOrder(paramMap);
        Service.initialize(Order, null);

        Service.startPaymentTransaction(this, true, true, new PaytmPaymentTransactionCallback() {
            /*Call Backs*/
            public void someUIErrorOccurred(String inErrorMessage) {}
            public void onTransactionResponse(Bundle inResponse) {
               // Toast.makeText(PayTm.this, inResponse.toString(), Toast.LENGTH_LONG).show();
              String s= (String) inResponse.get("STATUS");
              if(s.equals("TXN_SUCCESS"))
              {
                  Toast.makeText(PayTm.this, "success", Toast.LENGTH_SHORT).show();


                 DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Collector").child(id);
                  final java.util.Calendar currentdate = java.util.Calendar.getInstance();
                  final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                  final String newdate = sdf.format(currentdate.getTime());
                  databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(DataSnapshot dataSnapshot) {
                          Data1 d1 = dataSnapshot.getValue(Data1.class);

                          com.example.kratirastogi.garbageclean.Date d = d1.getDate();
                          Date1 date1 = d.getD1();
                          Date2 date2 = d.getD2();
                          Date3 date3 = d.getD3();




                          if (newdate.equals(date1.getDate())) {
                          DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Collector").child(id).child("date").child("d1").child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("paymentStatus");
                         ref.setValue("True");
                          }
                         else if (newdate.equals(date2.getDate())) {
                              DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Collector").child(id).child("date").child("d2").child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("paymentStatus");
                              ref.setValue("True");
                          }
                        else  if (newdate.equals(date3.getDate())) {
                              DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Collector").child(id).child("date").child("d3").child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("paymentStatus");
                              ref.setValue("True");
                          }
                      }

                      @Override
                      public void onCancelled(DatabaseError databaseError) {

                      }
                  });

              }



            }
            public void networkNotAvailable() {}
            public void clientAuthenticationFailed(String inErrorMessage) {}
            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {}
            public void onBackPressedCancelTransaction() {}
            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {}
        });

    }

    private String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }


}