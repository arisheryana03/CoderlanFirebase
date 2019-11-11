package com.aries.coderlandfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth auth;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();

        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);
    }

    private void doLogout() {
        auth.signOut();
        startActivity(new Intent(this, MainActivity.class));
        finish();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLogout) {
            doLogout();
        }
    }
}
