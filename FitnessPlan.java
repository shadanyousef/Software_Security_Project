
class FitnessPlan {
    private String category;
    private int minDuration;
    private String level;
    private String goal;

    public FitnessPlan(String category, int minDuration, String level, String goal) {
        this.category = category;
        this.minDuration = minDuration;
        this.level = level;
        this.goal = goal;
    }

    //getters
    public String getCategory() {
        return category;
    }

    public int getMinDuration() {
        return minDuration;
    }

    public String getLevel() {
        return level;
    }

    public String getGoal() {
        return goal;
    }
}
