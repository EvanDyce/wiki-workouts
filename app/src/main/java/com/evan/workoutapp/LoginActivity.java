package com.evan.workoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.evan.workoutapp.data.FirestoreFunctions;
import com.evan.workoutapp.databinding.ActivityLoginBinding;
import com.evan.workoutapp.utils.DialogMessage;
import com.evan.workoutapp.utils.UI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
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
                String email = binding.etEmail.getText().toString();
                String password = binding.etPassword.getText().toString();

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
        if (current != null) {
            setContentView(R.layout.loading_screen);

            FirestoreFunctions.retrieveExercisesFromFirestore(new FirestoreFunctions.FirestoreCallback() {
                @Override
                public void dataRetrieved() {
                    UI.updateUI(LoginActivity.this, current);
                }

                @Override
                public void dataRetrievalFailed() {
                    DialogMessage.Failure(LoginActivity.this, "This is google's fault not mine");
                }
            });
        }
    }


    private void signIn(String email, String password) {
        if (email == null || email.length() == 0 || password == null || password.length() == 0) {
            DialogMessage.Failure(this, "Please enter a valid email and password");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // if signed in properly
                        if (task.isSuccessful()) {
                            // if user is signed in then retrieve the data from firestore
                            FirestoreFunctions.retrieveExercisesFromFirestore(new FirestoreFunctions.FirestoreCallback() {
                                @Override
                                public void dataRetrieved() {
                                    // exercise data got retrieved successfully
                                    // updates UI to the main activity
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    UI.updateUI(LoginActivity.this, user);
                                }

                                @Override
                                public void dataRetrievalFailed() {
                                    DialogMessage.Failure(LoginActivity.this, "This is google's fault not me");
                                }
                            });

                        } else {
                            String errorCode;
                            if (task.getException() instanceof FirebaseAuthException) {
                                errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                                switch (errorCode) {
                                    case "ERROR_INVALID_CREDENTIAL":
                                        DialogMessage.Failure(LoginActivity.this, "The authentication credential is malformed or expired.");
                                        break;

                                    case "ERROR_INVALID_EMAIL":
                                        binding.etEmail.setError("The email address is badly formatted.");
                                        binding.etEmail.requestFocus();
                                        break;

                                    case "ERROR_WRONG_PASSWORD":
                                        DialogMessage.Failure(LoginActivity.this,"The password entered is incorrect.");
                                        binding.etPassword.setText("");
                                        break;

                                    case "ERROR_USER_MISMATCH":
                                        DialogMessage.Failure(LoginActivity.this,"The supplied credentials do not correspond to the previously signed in user.");
                                        break;

                                    case "ERROR_REQUIRES_RECENT_LOGIN":
                                        DialogMessage.Failure(LoginActivity.this,"This operation is sensitive and requires recent authentication. Log in again before retrying this request.");
                                        break;

                                    case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                                        DialogMessage.Failure(LoginActivity.this,"An account already exists with the same email address but different sign-in credentials.");
                                        break;

                                    case "ERROR_EMAIL_ALREADY_IN_USE":
                                        DialogMessage.Failure(LoginActivity.this, "The email address is already in use by another account.");

                                        break;

                                    case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                                        DialogMessage.Failure(LoginActivity.this,"This email is already associated with a different account.");
                                        break;

                                    case "ERROR_USER_DISABLED":
                                        DialogMessage.Failure(LoginActivity.this,"This account has been disabled by an administrator");
                                        break;

                                    case "ERROR_USER_TOKEN_EXPIRED":
                                        DialogMessage.Failure(LoginActivity.this,"User's credentials have expired. Please sign in again");
                                        break;

                                    case "ERROR_USER_NOT_FOUND":
                                        DialogMessage.Failure(LoginActivity.this,"There is no account with this email. Please create an account.");
                                        break;

                                    case "ERROR_INVALID_USER_TOKEN":
                                        DialogMessage.Failure(LoginActivity.this,"Please sign in again");
                                        break;

                                    case "ERROR_OPERATION_NOT_ALLOWED":
                                        DialogMessage.Failure(LoginActivity.this,"This operation is not allowed.");
                                        break;

                                    case "ERROR_WEAK_PASSWORD":
                                        binding.etPassword.setError("The password is invalid it must be at least 6 characters");
                                        binding.etPassword.requestFocus();
                                        break;
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "This is fucked up shit brotha", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}