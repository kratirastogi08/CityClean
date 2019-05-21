package com.example.kratirastogi.garbageclean;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity implements View.OnClickListener{
    private static final int IMAGE_REQUEST =1 ;
    Button probut,embut,passbut;
    CircleImageView propic1;
    EditText oldpass,newpass,em;
    DatabaseReference ref;
    StorageReference storageReference;
    private Uri imageUrl;
    private StorageTask uploadtask;
    int c=0;
    String s1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        probut=findViewById(R.id.probut);
        probut.setOnClickListener(this);
        embut=findViewById(R.id.embut);
        passbut=findViewById(R.id.passbut);
        em=findViewById(R.id.em);
        oldpass=findViewById(R.id.oldpass);
        newpass=findViewById(R.id.newpass);




        newpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
       String f=oldpass.getText().toString();
                if(f.length()>0)
                {






                }
                else
                {oldpass.setError("Please enter value");
                }
            }
        });

       propic1=findViewById(R.id.propic1);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String id = user.getUid();
        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        ref = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInfo u = dataSnapshot.getValue(UserInfo.class);
                if (u.getImageUrl().equals("default")) {
                    propic1.setImageResource(R.mipmap.profilepicture);
                } else {
                    Glide.with(EditProfile.this).load(u.getImageUrl()).into(propic1);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        embut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=em.getText().toString();
                HashMap<String, Object> map = new HashMap<>();
                map.put("email",s);
                ref.updateChildren(map);
                Toast.makeText(EditProfile.this, "updated", Toast.LENGTH_SHORT).show();

            }
        });
        passbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s1=oldpass.getText().toString().trim();

                if(s1.length()==0  )
                {
                    Toast.makeText(EditProfile.this, "enter old password first", Toast.LENGTH_SHORT).show();
                }
                else {

                    ref.addValueEventListener(new ValueEventListener() {


                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            UserInfo u = dataSnapshot.getValue(UserInfo.class);
                            if (s1.equals(u.getPass())) {
                                String s11 =newpass.getText().toString();
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("pass",s11);
                                ref.updateChildren(map);

                                Toast.makeText(EditProfile.this, "updated", Toast.LENGTH_SHORT).show();
                                c=-3;


                            }
                            else {

                                if (c <0) {
                                    Toast.makeText(EditProfile.this, "data updated", Toast.LENGTH_SHORT).show();

                                } else {
  c=1;
                                    Toast.makeText(EditProfile.this, "password mismatched", Toast.LENGTH_SHORT).show();
                                    oldpass.setText("");
                                    newpass.setText("");
                                }
                            }
                            }



                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        openImage();
    }
    public void openImage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, IMAGE_REQUEST);
    }

    private String printFileExtension(Uri uri) {
        ContentResolver contentResolver = this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading image");
        pd.show();
        if (imageUrl != null) {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + printFileExtension(imageUrl));
            uploadtask = fileReference.putFile(imageUrl);
            uploadtask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUrl = task.getResult();
                        String mUri = downloadUrl.toString();
                        ref = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("imageUrl", mUri);
                        ref.updateChildren(map);
                        pd.dismiss();
                    } else {
                        Toast.makeText(EditProfile.this, "failed", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });

        } else {
            Toast.makeText(EditProfile.this, "no image selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUrl = data.getData();
            if(uploadtask!=null && uploadtask.isInProgress())
            {
                Toast.makeText(EditProfile.this, "upload in progress", Toast.LENGTH_SHORT).show();
            }
            else
            {
                uploadImage();
            }
        }
    }
}
