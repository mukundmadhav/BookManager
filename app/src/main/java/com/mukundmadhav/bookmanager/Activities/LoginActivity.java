package com.mukundmadhav.bookmanager.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mukundmadhav.bookmanager.R;

public class LoginActivity extends AppCompatActivity {

    Button butSignUp, butLogin;
    EditText editEmail, editPass;
    ProgressBar pbarLogin;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Init Views
        butSignUp = findViewById(R.id.login_signupBtn);
        butLogin = findViewById(R.id.login_Btn);
        editEmail = findViewById(R.id.login_email);
        editPass = findViewById(R.id.login_pass);
        pbarLogin = findViewById(R.id.login_progressBar);
        mAuth = FirebaseAuth.getInstance();

        pbarLogin.setVisibility(View.INVISIBLE);

        butSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });


        butLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pbarLogin.setVisibility(View.VISIBLE);
                butLogin.setVisibility(View.INVISIBLE);

                String email = editEmail.getText().toString();
                String pass = editPass.getText().toString();

                if(email.isEmpty() || pass.isEmpty()) {
                    showMessage("Please fill in the fields correctly");
                    pbarLogin.setVisibility(View.INVISIBLE);
                    butLogin.setVisibility(View.VISIBLE);
                }
                else {
                    loginWith(email,pass);
                }

            }
        });


    }

    private void loginWith(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    showMessage("Logged in!");
                    updateUI();
                }
                else {
                    showMessage("Login Failed "+task.getException().getMessage());
                    pbarLogin.setVisibility(View.INVISIBLE);
                    butLogin.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void updateUI() {
        Intent homeActivity = new Intent(LoginActivity.this,HomeActivity.class);
        startActivity(homeActivity);
        finish();
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
