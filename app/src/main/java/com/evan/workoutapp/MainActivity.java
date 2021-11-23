package com.evan.workoutapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.evan.workoutapp.user.CurrentUserSingleton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.evan.workoutapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    public static final int EXERCISES_FRAGMENT = 0;
    public static final int WORKOUT_FRAGMENT = 1;
    public static final int CUSTOM_WORKOUT_FRAGMENT = 2;
    public static final int HISTORY_FRAGMENT = 3;
    public static final int PROFILE_FRAGMENT = 4;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // gets the binding and sets active fragment as first in activity_main_drawer.xml menu file
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        sets the action bar as app_bar_main.toolbar
//        binding allows you to access with . notation
        setSupportActionBar(binding.appBarMain.toolbar);
//        set on click listener for the floating action button
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//        accessing the drawer layout and the nav view
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        View headerView = navigationView.getHeaderView(0);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        TextView name = headerView.findViewById(R.id.navbarUsername);
        name.setText(mAuth.getCurrentUser().getDisplayName());

        TextView email = headerView.findViewById(R.id.navbarEmail);
        email.setText(mAuth.getCurrentUser().getEmail());

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_exercises, R.id.nav_workouts, R.id.nav_custom_workouts, R.id.nav_history, R.id.nav_profile, R.id.nav_sign_out)
                .setOpenableLayout(drawer)
                .build();

//        gets the navcontroller from the content_main.xml file and the main fragment
        // navcontroller is the mobile_navigation.xml that is navGraph in file
        // links the text to the proper fragment
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        int fragment_index = getIntent().getIntExtra("fragment", 0);
        Toast.makeText(this, fragment_index, Toast.LENGTH_SHORT).show();
        switch (fragment_index) {
            case 0:
                navController.navigate(R.id.nav_exercises);
                break;

            case 1:
                navController.navigate(R.id.nav_workouts);
                break;

            case 2:
                navController.navigate(R.id.nav_custom_workouts);
                break;

            case 3:
                navController.navigate(R.id.nav_history);
                break;

            case 4:
                navController.navigate(R.id.nav_profile);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        // update the text at teh top with the current user information
        // has to be here not onCreate because then View is null
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * function called when the sign out button within the navbar is called
     * @param item MenuItem reference to the sign_out item
     */
    public void onSignOutClicked(MenuItem item) {
        CurrentUserSingleton.signOutCurrentUser();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, LoginActivity.class));
    }

}