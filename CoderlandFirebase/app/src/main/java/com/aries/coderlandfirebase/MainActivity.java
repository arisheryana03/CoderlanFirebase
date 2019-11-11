package com.aries.coderlandfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;
    private EditText edEmail, edPassword;
    private Button btnLogin, btnRegister;
    private LinearLayout layoutLoading;
    private TextView tvForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        edEmail = (EditText) findViewById(R.id.edEmail);
        edPassword = (EditText) findViewById(R.id.edPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        layoutLoading = findViewById(R.id.layoutLoading);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }

    }


    private void doLogin() {
        layoutLoading.setVisibility(View.VISIBLE);

        auth.signInWithEmailAndPassword(edEmail.getText().toString(), edPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(MainActivity.this, "Login Success.", Toast.LENGTH_LONG).show();
                            layoutLoading.setVisibility(View.GONE);

                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            finish();
                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                Toast.makeText(MainActivity.this, "Weak password, try again.", Toast.LENGTH_LONG).show();
                                edPassword.setError("Weak password.");
                                layoutLoading.setVisibility(View.GONE);
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(MainActivity.this, "Email and or Password wrong, tyr again.", Toast.LENGTH_LONG).show();
                                layoutLoading.setVisibility(View.GONE);
                            } catch (Exception e) {
                            }

                        }
                    }
                });

    }

    private void doRegister() {
        layoutLoading.setVisibility(View.VISIBLE);

        auth.createUserWithEmailAndPassword(edEmail.getText().toString(), edPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(MainActivity.this, "Registration Complete.", Toast.LENGTH_LONG).show();
                            layoutLoading.setVisibility(View.GONE);

                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            finish();
                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                Toast.makeText(MainActivity.this, "Weak password, try again.", Toast.LENGTH_LONG).show();
                                edPassword.setError("Weak password.");
                                layoutLoading.setVisibility(View.GONE);
                            } catch (FirebaseAuthUserCollisionException e) {
                                Toast.makeText(MainActivity.this, "Email is register.", Toast.LENGTH_LONG).show();
                                edEmail.setError("Email is ready registered, choose another.");
                                layoutLoading.setVisibility(View.GONE);
                            } catch (Exception e) {
                            }
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                doLogin();
                break;

            case R.id.btnRegister:
                doRegister();
                break;
            case R.id.tvForgotPassword:
                startActivity(new Intent(MainActivity.this, ForgotPasswordActivity.class));
                break;
        }
    }


}
