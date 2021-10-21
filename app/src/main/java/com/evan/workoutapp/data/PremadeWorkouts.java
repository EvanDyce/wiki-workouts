package com.evan.workoutapp.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.evan.workoutapp.data.workout.Workout;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PremadeWorkouts {
    private  ArrayList<Workout> workouts;

    private  ArrayList<String> listOfIDContainedInWorkout = new ArrayList<>();
    private  ArrayList<String> legsIDSArrayList = new ArrayList<>();
    private  ArrayList<String> legs2IDArrayList = new ArrayList<>();
    private  ArrayList<String> chestIDSArrayList = new ArrayList<>();
    private  ArrayList<String> shouldersIDSArrayList = new ArrayList<>();
    private  ArrayList<String> armsIDSArrayList = new ArrayList<>();
    private  ArrayList<String> absIDSArrayList = new ArrayList<>();
    private  ArrayList<String> backIDSArrayList = new ArrayList<>();

    private  String[] Legs1IDS = {"111", "113", "791", "114", "117", "102", "308"};
    private  String[] Legs2IDS = {"351", "408", "914", "154", "300", "411", "776"};
    private  String[] ChestIDS = {"790", "210", "206", "122", "192"};
    private  String[] ShouldersIDS = {"119", "124", "148", "150", "394", "233"};
    private  String[] ArmsIDS = {"162", "768", "86", "89", "193"};
    private  String[] AbsIDS = {"125", "238", "544", "879", "303", "343"};
    private  String[] BackIDS = {"105", "107", "109", "188", "330", "376"};

    private  ArrayList<Exercises.Exercise> legs1 = new ArrayList<>();
    private  ArrayList<Exercises.Exercise> legs2 = new ArrayList<>();
    private  ArrayList<Exercises.Exercise> chest = new ArrayList<>();
    private  ArrayList<Exercises.Exercise> shoulders = new ArrayList<>();
    private  ArrayList<Exercises.Exercise> arms = new ArrayList<>();
    private  ArrayList<Exercises.Exercise> abs = new ArrayList<>();
    private  ArrayList<Exercises.Exercise> back = new ArrayList<>();

    ArrayList<ArrayList<Exercises.Exercise>> arrayListofArraylist = new ArrayList<>();


    public PremadeWorkouts() {
        for (String id : Legs1IDS) {
            legsIDSArrayList.add(id);
        }

        for (String id : Legs2IDS) {
            legs2IDArrayList.add(id);
        }

        for (String id : ChestIDS) {
            chestIDSArrayList.add(id);
        }

        for (String id : ShouldersIDS) {
            shouldersIDSArrayList.add(id);
        }
        for (String id : ArmsIDS) {
            armsIDSArrayList.add(id);
        }
        for (String id : AbsIDS) {
            absIDSArrayList.add(id);
        }
        for (String id : BackIDS) {
            backIDSArrayList.add(id);
        }

        arrayListofArraylist.add(legs1);
        arrayListofArraylist.add(legs2);
        arrayListofArraylist.add(chest);
        arrayListofArraylist.add(shoulders);
        arrayListofArraylist.add(back);
        arrayListofArraylist.add(abs);
        arrayListofArraylist.add(arms);

        listOfIDContainedInWorkout.addAll(legsIDSArrayList);
        listOfIDContainedInWorkout.addAll(legs2IDArrayList);
        listOfIDContainedInWorkout.addAll(chestIDSArrayList);
        listOfIDContainedInWorkout.addAll(shouldersIDSArrayList);
        listOfIDContainedInWorkout.addAll(armsIDSArrayList);
        listOfIDContainedInWorkout.addAll(absIDSArrayList);
        listOfIDContainedInWorkout.addAll(backIDSArrayList);
    }

    public void pushToFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // maybe add it as a map?
//        ArrayList<Exercises.Exercise> legs = new ArrayList<>();
//        for (Exercises.Exercise exercise : back) {
//            legs.add(exercise);
//        }
        Workout workout = new Workout("Ab Workout", "Workout for your abs", "Abs", abs);
        Map<String, Object> data = new HashMap<>();
        data.put("name", workout.getName());
        data.put("description", workout.getDescription());
        data.put("category", workout.getCategory());
        data.put("exercises", workout.getExercisesInWorkout());
        db.collection("workouts")
                .document(workout.getName())
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("FUCK", "DocumentSnapshot W");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("FUCK", "DocumentSnapshot L");
                    }
                });
//        for (ArrayList<Exercises.Exercise> list : arrayListofArraylist) {
//
//            for (Exercises.Exercise exercise : list) {
//                Map<String, Object> data = new HashMap<>();
//                data.put("id", exercise.getId());
//                data.put("name", exercise.getName());
//                data.put("description", exercise.getDescription());
//                data.put("category", exercise.getCategory());
//                data.put("equipment", exercise.getEquipment());
//            }
//        }
    }

    public  void legs1Add(Exercises.Exercise e) {
        legs1.add(e);
    }
    public  void legs2Add(Exercises.Exercise e) {
        legs2.add(e);
    }
    public  void chestAdd(Exercises.Exercise e) {
        chest.add(e);
    }
    public  void shouldersAdd(Exercises.Exercise e) {
        shoulders.add(e);
    }
    public  void armsAdd(Exercises.Exercise e) {
        arms.add(e);
    }
    public  void absAdd(Exercises.Exercise e) {
        abs.add(e);
    }
    public  void backAdd(Exercises.Exercise e) {
        back.add(e);
    }

    public  ArrayList<Workout> getWorkouts() {
        return workouts;
    }

    public  ArrayList<String> getListOfIDContainedInWorkout() {
        return listOfIDContainedInWorkout;
    }

    public  ArrayList<String> getLegsIDSArrayList() {
        return legsIDSArrayList;
    }

    public  ArrayList<String> getLegs2IDArrayList() {
        return legs2IDArrayList;
    }

    public  ArrayList<String> getChestIDSArrayList() {
        return chestIDSArrayList;
    }

    public  ArrayList<String> getShouldersIDSArrayList() {
        return shouldersIDSArrayList;
    }

    public  ArrayList<String> getArmsIDSArrayList() {
        return armsIDSArrayList;
    }

    public  ArrayList<String> getAbsIDSArrayList() {
        return absIDSArrayList;
    }

    public  ArrayList<String> getBackIDSArrayList() {
        return backIDSArrayList;
    }



}
