import java.io.Serializable;

class User implements Serializable {
    private String email;
    private String password;
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
    private String fitnessCategory;

    public User(String email, String password, String name, int age, String gender, String fitnessGoal,
            String currentLevel,
            String boneOrJointProblems, String diabetes, String heartDisease,
            String surgery, String medication, String fitnessCategory) {
        this.email = email;
        this.password = password;
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
        this.fitnessCategory = fitnessCategory;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    
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

    public String getFitnessCategory() {
        return fitnessCategory;
    }

    // Check if the user has medical conditions
    public boolean hasMedicalConditions() {
        return !boneOrJointProblems.equalsIgnoreCase("no") ||
                !diabetes.equalsIgnoreCase("no") ||
                !heartDisease.equalsIgnoreCase("no") ||
                !surgery.equalsIgnoreCase("no") ||
                !medication.equalsIgnoreCase("no");
    }

    @Override
    public String toString() {
        return "Email: " + email +
                "\nName: " + name +
                "\nAge: " + age +
                "\nGender: " + gender +
                "\nBone/Joint Problems: " + boneOrJointProblems +
                "\nDiabetes: " + diabetes +
                "\nHeart Disease: " + heartDisease +
                "\nSurgery: " + surgery +
                "\nMedication: " + medication +
                "\nFitness Goal: " + fitnessGoal +
                "\nCurrent Level: " + currentLevel +
                "\nFitness Category: " + fitnessCategory +
                "\n";
    }
}
