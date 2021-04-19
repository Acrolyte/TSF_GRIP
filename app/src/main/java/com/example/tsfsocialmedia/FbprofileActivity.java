package com.example.tsfsocialmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class FbprofileActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private Button button;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbprofile);

        imageView = findViewById(R.id.fbpropic);
        textView = findViewById(R.id.usrnm);
        button = findViewById(R.id.fblogout_button);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        updateUI(user);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(FbprofileActivity.this,MainActivity.class));
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            textView.setText(user.getDisplayName());
            if (user.getPhotoUrl() != null) {
                String photoUrl = user.getPhotoUrl().toString();
                photoUrl = photoUrl + "?type=large";
                Picasso.get().load(photoUrl).resize(600, 600).into(imageView);
            }
        } else {
            textView.setText("");
            imageView.setImageResource(R.drawable.blank);
        }
    }
}