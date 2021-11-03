package com.evan.workoutapp.ui.exercises;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.evan.workoutapp.R;
import com.evan.workoutapp.data.Exercises;

import java.util.ArrayList;
import java.util.Set;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.Viewholder> {

    private Context context;
    private ArrayList<String> categoryNames;
    private CategoryClickedListener categoryListener;

    public ExerciseAdapter(Context context,ArrayList<String> categories, CategoryClickedListener listener) {
        this.context = context;
        this.categoryNames = categories;
        this.categoryListener = listener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate layout for each item in recycler view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new Viewholder(view, this.categoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        String category = categoryNames.get(position);

        holder.exerciseCategory.setText(category);

        switch (category) {
            case "Legs":
                holder.exerciseIV.setImageResource(R.drawable.legs_image);
                break;

            case "Arms":
                holder.exerciseIV.setImageResource(R.drawable.arms_image);
                break;

            case "Abs":
                holder.exerciseIV.setImageResource(R.drawable.abs_image);
                break;

            case "Chest":
                holder.exerciseIV.setImageResource(R.drawable.chest_image);
                break;

            case "Back":
                holder.exerciseIV.setImageResource(R.drawable.back_image);
                break;

            case "Calves":
                holder.exerciseIV.setImageResource(R.drawable.calf_image);
                break;

            case "Shoulders":
                holder.exerciseIV.setImageResource(R.drawable.shoulder_image);
                break;
        }
    }

    @Override
    public int getItemCount() {
        // show number of cards in recyler view
        return categoryNames.size();
    }

    // View holder class for initializing views (TextView & ImageView)
    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView exerciseIV;
        private TextView exerciseCategory;
        CategoryClickedListener listener;

        public Viewholder(@NonNull View itemView, CategoryClickedListener listener) {
            super(itemView);
            exerciseIV = itemView.findViewById(R.id.workoutImageCategory);
            exerciseCategory = itemView.findViewById(R.id.workoutTitleCategory);

            // getting teh listener passed with it's own implementation and setting it
            // to teh itemview
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.listener.onCategoryClicked(getAdapterPosition());
        }
    }

    public interface CategoryClickedListener {
        void onCategoryClicked(int position);
    }
}
