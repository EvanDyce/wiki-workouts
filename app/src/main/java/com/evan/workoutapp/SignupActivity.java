package com.evan.workoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.evan.workoutapp.databinding.ActivityLoginBinding;
import com.evan.workoutapp.databinding.ActivitySignupBinding;
import com.evan.workoutapp.utils.DialogMessage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private final String TAG = "SignUp";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // get instance of firebase auth
        mAuth = FirebaseAuth.getInstance();

        binding.buttonAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), LoginActivity.class));
            }
        });

        // onclick listener for the create account button
        binding.buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.etName.toString();
                String email = binding.etEmail.toString();
                String password = binding.etPassword.toString();
                String confirmPassword = binding.etConfirmPassword.toString();

                createAccount(name, email, password, confirmPassword);
            }
        });
    }

    private void createAccount(String name, String email, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) { DialogMessage.Failure(this, "Given Passwords Do Not Match"); }

        if (name.length() == 0 || email.length() == 0 || password.length() == 0 || confirmPassword.length() == 0) {
            DialogMessage.Failure(this, "Please Fill Out ALl Fields");
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User created successfully");
                            // mauth will update if task is successful
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.d(TAG, "User creation failed");

                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                            switch (errorCode) {
                                case "ERROR_INVALID_CREDENTIAL":
                                    DialogMessage.Failure(SignupActivity.this, "The authentication credential is malformed or expired.");
                                    break;

                                case "ERROR_INVALID_EMAIL":
                                    binding.etEmail.setError("The email address is badly formatted.");
                                    binding.etEmail.requestFocus();
                                    break;

                                case "ERROR_WRONG_PASSWORD":
                                    DialogMessage.Failure(SignupActivity.this,"The password entered is incorrect.");
                                    binding.etPassword.setText("");
                                    break;

                                case "ERROR_USER_MISMATCH":
                                    DialogMessage.Failure(SignupActivity.this,"The supplied credentials do not correspond to the previously signed in user.");
                                    break;

                                case "ERROR_REQUIRES_RECENT_LOGIN":
                                    DialogMessage.Failure(SignupActivity.this,"This operation is sensitive and requires recent authentication. Log in again before retrying this request.");
                                    break;

                                case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                                    DialogMessage.Failure(SignupActivity.this,"An account already exists with the same email address but different sign-in credentials.");
                                    break;

                                case "ERROR_EMAIL_ALREADY_IN_USE":
                                    DialogMessage.Failure(SignupActivity.this, "The email address is already in use by another account.");

                                    break;

                                case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                                    DialogMessage.Failure(SignupActivity.this,"This email is already associated with a different account.");
                                    break;

                                case "ERROR_USER_DISABLED":
                                    DialogMessage.Failure(SignupActivity.this,"This account has been disabled by an administrator");
                                    break;

                                case "ERROR_USER_TOKEN_EXPIRED":
                                    DialogMessage.Failure(SignupActivity.this,"User's credentials have expired. Please sign in again");
                                    break;

                                case "ERROR_USER_NOT_FOUND":
                                    DialogMessage.Failure(SignupActivity.this,"There is no account with this email. Please create an account.");
                                    break;

                                case "ERROR_INVALID_USER_TOKEN":
                                    DialogMessage.Failure(SignupActivity.this,"Please sign in again");
                                    break;

                                case "ERROR_OPERATION_NOT_ALLOWED":
                                    DialogMessage.Failure(SignupActivity.this,"This operation is not allowed.");
                                    break;

                                case "ERROR_WEAK_PASSWORD":
                                    binding.etPassword.setError("The password is invalid it must be at least 6 characters");
                                    binding.etPassword.requestFocus();
//                                    mTxtPassword.setError("The password is invalid it must 6 characters at least");
//                                    mTxtPassword.requestFocus();
                                    break;




                            }
                            DialogMessage.Failure(SignupActivity.this, task.getException().getMessage());
                        }
                    }
                });
    }

    /**
     * Will update the UI if user is signed in. Else if it is null it will reload the page
     * @param user current user that is signed in
     */
    private void updateUI(FirebaseUser user) {
        if (user == null) {
            finish();
            startActivity(getIntent());
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}