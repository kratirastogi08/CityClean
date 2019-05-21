package com.example.kratirastogi.garbageclean;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class Profile extends Fragment {
    CircleImageView propic;
    TextView textView4,textView6,homeaddress;
    DatabaseReference ref;
    StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUrl;
    private StorageTask uploadtask;
    Button edit,homechange,save;
    TextInputLayout textInputLayout5;
    EditText address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        propic = view.findViewById(R.id.propic);
        textView4=view.findViewById(R.id.textView4);
        textView6=view.findViewById(R.id.textView6);
        homeaddress=view.findViewById(R.id.homeaddress);
        address=view.findViewById(R.id.address);
        textInputLayout5=view.findViewById(R.id.textInputLayout5);
        homechange=view.findViewById(R.id.homechange);
        save=view.findViewById(R.id.save);
        edit=view.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),EditProfile.class);
                startActivity(i);
            }
        });
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
                    propic.setImageResource(R.mipmap.profilepicture);
                } else {
                    Glide.with(getContext()).load(u.getImageUrl()).into(propic);
                }
      textView4.setText(u.getName());
                textView6.setText(u.getEmail());
                homeaddress.setText(u.getAdd());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        homechange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             textInputLayout5.setVisibility(View.VISIBLE);
               save.setVisibility(View.VISIBLE);

            }

        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng l= getLocationFromAddress(getContext(),address.getText().toString());
                HashMap<String, Object> map = new HashMap<>();
                map.put("lat",l.latitude);
                map.put("lng",l.longitude);
                map.put("add",address.getText().toString());
                ref.updateChildren(map);
                Toast.makeText(getContext(), "updated", Toast.LENGTH_SHORT).show();
                textInputLayout5.setVisibility(View.INVISIBLE);
                save.setVisibility(View.INVISIBLE);

            }
        });
        propic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
        });
        return view;

    }

    public void openImage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, IMAGE_REQUEST);
    }

    private String printFileExtension(Uri uri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(getContext());
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
                        Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });

        } else {
            Toast.makeText(getContext(), "no image selected", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "upload in progress", Toast.LENGTH_SHORT).show();
            }
            else
            {
                uploadImage();
            }
        }
    }
    public LatLng getLocationFromAddress(Context context, String strAddress) {

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