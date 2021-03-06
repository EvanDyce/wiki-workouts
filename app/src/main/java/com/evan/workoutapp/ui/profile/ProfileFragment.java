package com.evan.workoutapp.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.evan.workoutapp.databinding.FragmentProfileBinding;
import com.evan.workoutapp.user.CurrentUserSingleton;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.tvNameUpper.setText(CurrentUserSingleton.getInstance().getName());
        binding.tvName.setText(CurrentUserSingleton.getInstance().getName());
        binding.tvEmail.setText(CurrentUserSingleton.getInstance().getEmail());
        binding.tvWorkoutsCompleted.setText(String.valueOf(CurrentUserSingleton.getInstance().getFinishedWorkouts().size()));
        binding.tvWorkoutsCreated.setText(String.valueOf(CurrentUserSingleton.getInstance().getUserWorkouts().size()));

        binding.buttonLeaveReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = "evandyce.developer@gmail.com";
                view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + email + "?subject=Workout App Review")));
            }
        });
        return root;
    }
}
