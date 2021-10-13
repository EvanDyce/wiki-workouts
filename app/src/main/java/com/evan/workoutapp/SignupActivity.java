package com.evan.workoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.evan.workoutapp.databinding.ActivityLoginBinding;
import com.evan.workoutapp.databinding.ActivitySignupBinding;
import com.evan.workoutapp.user.User;
import com.evan.workoutapp.utils.DialogMessage;
import com.evan.workoutapp.utils.UI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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
                String name = binding.etName.getText().toString();
                String email = binding.etEmail.getText().toString();
                String password = binding.etPassword.getText().toString();
                String confirmPassword = binding.etConfirmPassword.getText().toString();

                createAccount(name, email, password, confirmPassword);
            }
        });
    }

    private void createAccount(String name, String email, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            Toast.makeText(SignupActivity.this, password + "   " + confirmPassword, Toast.LENGTH_SHORT).show();
            DialogMessage.Failure(this, "Given Passwords Do Not Match");
            return;
        }

        if (name.length() == 0 || email.length() == 0 || password.length() == 0 || confirmPassword.length() == 0) {
            DialogMessage.Failure(this, "Please Fill Out All Fields");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User created successfully");
                            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();

                            mAuth.getCurrentUser().updateProfile(profileUpdate)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("Name Set", "Username set in auth");
                                                insertUserIntoFirestore(name, email);
                                            } else {
                                                Log.e("USERNAME", "Username couldn't be set");
                                            }
                                        }
                                    });
//                            insertUserIntoFirestore(name, email);
                        } else {
                            Log.d(TAG, "User creation failed");

                            String errorCode;

                            if (task.getException() instanceof FirebaseAuthException) {
                                errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

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
                                        break;
                                }
                            } else {
                                Toast.makeText(SignupActivity.this, "This is fucked up shit brotha", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    /**
     * adds the user information into the firestore database for retrieval later
     * if successful calls updateUI to load the main activity with logged in user
     * @param email email for the user. Used as document identity
     */
    private void insertUserIntoFirestore(String name, String email) {
        User user = new User(name, email);

        Map<String, Object> data = new HashMap<>();
        data.put("name", user.getName());
        data.put("email", user.getEmail());
        data.put("workouts_completed", user.getWorkoutsCompleted());
        data.put("custom_workouts", user.getUserWorkouts());

        db.collection("users")
                .document(user.getEmail())
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "User added to firestore successfully");
                        FirebaseUser user = mAuth.getCurrentUser();
                        UI.updateUI(SignupActivity.this, user);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "User couldn't be added to firestore.");
                    }
                });
    }
}