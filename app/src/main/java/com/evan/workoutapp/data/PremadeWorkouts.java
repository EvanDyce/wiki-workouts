package com.evan.workoutapp.data;

import java.util.ArrayList;

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

        listOfIDContainedInWorkout.addAll(legsIDSArrayList);
        listOfIDContainedInWorkout.addAll(legs2IDArrayList);
        listOfIDContainedInWorkout.addAll(chestIDSArrayList);
        listOfIDContainedInWorkout.addAll(shouldersIDSArrayList);
        listOfIDContainedInWorkout.addAll(armsIDSArrayList);
        listOfIDContainedInWorkout.addAll(absIDSArrayList);
        listOfIDContainedInWorkout.addAll(backIDSArrayList);
    }

    public  void legs1Add(Exercises.Exercise e) {
        legs1.add(e);
    }
    public  void legs2Add(Exercises.Exercise e) {
        legs1.add(e);
    }
    public  void chestAdd(Exercises.Exercise e) {
        legs1.add(e);
    }
    public  void shouldersAdd(Exercises.Exercise e) {
        legs1.add(e);
    }
    public  void armsAdd(Exercises.Exercise e) {
        legs1.add(e);
    }
    public  void absAdd(Exercises.Exercise e) {
        legs1.add(e);
    }
    public  void backAdd(Exercises.Exercise e) {
        legs1.add(e);
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
