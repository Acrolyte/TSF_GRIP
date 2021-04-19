package com.example.tsfsocialmedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    ImageView dp;
    TextView txt;
    Button logout;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dp = findViewById(R.id.propic2);
        txt = findViewById(R.id.gmaid);
        logout = findViewById(R.id.glogout_button);
        firebaseAuth = FirebaseAuth.getInstance(); 
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            String photoUrl = firebaseUser.getPhotoUrl().toString();
            String originalpic = "s96-c/photo.jpg";
            String newpic = "s800-c/photo.jpg";
            if(photoUrl!=null){
                photoUrl = photoUrl.replace(originalpic,newpic);
            }
            Picasso.get().load(photoUrl).resize(600,600).into(dp);
            txt.setText(firebaseUser.getDisplayName());
        }

        googleSignInClient = GoogleSignIn.getClient(ProfileActivity.this, GoogleSignInOptions.DEFAULT_SIGN_IN);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            firebaseAuth.signOut();
                            LoginManager.getInstance().logOut();
                            startActivity(new Intent(ProfileActivity.this,MainActivity.class));
                            Toast.makeText(getApplicationContext(),"Logout successfully",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}