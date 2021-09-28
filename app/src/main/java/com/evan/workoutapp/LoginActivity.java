package com.evan.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.evan.workoutapp.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LOGINACTIVITY";

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // on click listener for sign in button
        binding.buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.etEmail.toString();
                String password = binding.etPassword.toString();
                signIn(email, password);
            }
        });

        // create account button onCLickListener
        // Starts the sign up activity
        binding.buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), SignupActivity.class));
            }
        });

        binding.buttonForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // SHow a dialog with teh email it will be sent to
                // ask for confirmation and then send it
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("en");
    }

    @Override
    public void onStart() {
        super.onStart();
        // check if the user is signed in (non-null) and update the UI
        FirebaseUser current = mAuth.getCurrentUser();
        updateUI(current);
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) { return; }

        startActivity(new Intent(this, MainActivity.class));
    }

    private void signIn(String email, String password) {}
}