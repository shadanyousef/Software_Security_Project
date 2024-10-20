class User {
    //private int id;
    private String name;
    private int age;
    private String gender;
    private String fitnessGoal;
    private String currentLevel;
    private String boneOrJointProblems;
    private String diabetes;
    private String heartDisease;
    private String surgery;
    private String medication;

    public User(String name, int age, String gender, String fitnessGoal, String currentLevel,
            String boneOrJointProblems, String diabetes, String heartDisease,
            String surgery, String medication) {
        //this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.fitnessGoal = fitnessGoal;
        this.currentLevel = currentLevel;
        this.boneOrJointProblems = boneOrJointProblems;
        this.diabetes = diabetes;
        this.heartDisease = heartDisease;
        this.surgery = surgery;
        this.medication = medication;
    }

    // Getters
    // public int getId() {
    //     return id;
    // }
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getFitnessGoal() {
        return fitnessGoal;
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    public String getBoneOrJointProblems() {
        return boneOrJointProblems;
    }

    public String getDiabetes() {
        return diabetes;
    }

    public String getHeartDisease() {
        return heartDisease;
    }

    public String getSurgery() {
        return surgery;
    }

    public String getMedication() {
        return medication;
    }

    // Check if the user has medical conditions
    public boolean hasMedicalConditions() {
        return !boneOrJointProblems.equalsIgnoreCase("no") ||
                !diabetes.equalsIgnoreCase("no") ||
                !heartDisease.equalsIgnoreCase("no") ||
                !surgery.equalsIgnoreCase("no") ||
                !medication.equalsIgnoreCase("no");
    }

    // Additional helper methods for specific medical conditions
    public boolean hasHeartDisease() {
        return heartDisease.equalsIgnoreCase("yes");
    }
}
