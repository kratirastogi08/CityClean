package com.example.kratirastogi.garbageclean;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class OTPActivity2 extends AppCompatActivity implements View.OnClickListener {
    EditText otp;
    String verificationcode;
    Button button2;
    FirebaseAuth auth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callback;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp2);
        Intent i=getIntent();
        String num= i.getStringExtra("code");
        otp=findViewById(R.id.otp);
        button2=findViewById(R.id.button2);
        button2.setOnClickListener(this);
        auth=FirebaseAuth.getInstance();
  databaseReference=FirebaseDatabase.getInstance().getReference("Users");
   sharedPreferences=getSharedPreferences("dib",MODE_PRIVATE);
        edit=sharedPreferences.edit();
        callback=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Log.d("complete", "onVerificationCompleted:" + phoneAuthCredential);
                signinwithPhone(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w("failed", "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(OTPActivity2.this, "invalid request", Toast.LENGTH_SHORT).show();
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    //
                    Toast.makeText(OTPActivity2.this, "The SMS quota for the project has been exceeded", Toast.LENGTH_SHORT).show();
                    // ...
                }
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationcode=s;
                Toast.makeText(getApplicationContext(), "code sent to the number", Toast.LENGTH_SHORT).show();
            }
        };
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                num,60, TimeUnit.SECONDS,this,callback
        );
    }
    public void signinwithPhone(PhoneAuthCredential credential)
    {
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {   FirebaseUser user = task.getResult().getUser();
                    databaseReference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists())
                            {
                                Toast.makeText(OTPActivity2.this, "already exist", Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(OTPActivity2.this,HomeActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    Toast.makeText(getApplicationContext(), "user is succefully signed in", Toast.LENGTH_SHORT).show();
                    edit.putString("reg","saved");
                    edit.apply();
                    sharedPreferences.edit().remove("log").commit();
                    Intent i=new Intent(OTPActivity2.this,Registration.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(OTPActivity2.this, "The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                    }
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Intent i=new Intent(OTPActivity2.this,OTPActivity.class);

                    }
                }
            }
        });
    }
    public void verifyphonenumber(String verifycode,String input)
    {
        PhoneAuthCredential phoneAuthCredential=PhoneAuthProvider.getCredential(verifycode,input);
        signinwithPhone(phoneAuthCredential);

    }
    @Override
    public void onClick(View v) {
        String inputcode=otp.getText().toString();
        if(verificationcode!=null)
        {
            verifyphonenumber(verificationcode,inputcode);
        }

    }
}
