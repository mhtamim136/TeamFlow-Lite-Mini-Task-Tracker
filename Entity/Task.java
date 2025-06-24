package Entity;
public class Task {
    // Private attributes
    private String memberName;
    private String taskName;
    private int estimatedDays;
    private int progressPercentage;
    
    // Default constructor
    public Task() {
        this.memberName = "";
        this.taskName = "";
        this.estimatedDays = 0;
        this.progressPercentage = 0;
    }
    
    // Parameterized constructor
    public Task(String memberName, String taskName, int estimatedDays, int progressPercentage) {
        this.memberName = memberName;
        this.taskName = taskName;
        this.estimatedDays = estimatedDays;
        this.progressPercentage = progressPercentage;
    }
    
    // Getters
    public String getMemberName() {
        return memberName;
    }
    
    public String getTaskName() {
        return taskName;
    }
    
    public int getEstimatedDays() {
        return estimatedDays;
    }
    
    public int getProgressPercentage() {
        return progressPercentage;
    }
    
    // Setters
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    
    public void setEstimatedDays(int estimatedDays) {
        this.estimatedDays = estimatedDays;
    }
    
    public void setProgressPercentage(int progressPercentage) {
        // Ensure percentage is between 0 and 100
        if (progressPercentage < 0) {
            this.progressPercentage = 0;
        } else if (progressPercentage > 100) {
            this.progressPercentage = 100;
        } else {
            this.progressPercentage = progressPercentage;
        }
    }
    
    // toString method for file I/O and display purposes
    @Override
    public String toString() {
        return memberName + "|" + taskName + "|" + estimatedDays + "|" + progressPercentage + "%";
    }
    
    // Method to parse a string back into a Task object (for reading from file)
    public static Task fromString(String taskString) {
        String[] parts = taskString.split("\\|");
        if (parts.length == 4) {
            String memberName = parts[0].trim();
            String taskName = parts[1].trim();
            int estimatedDays = Integer.parseInt(parts[2].trim());
            int progressPercentage = Integer.parseInt(parts[3].trim().replace("%", ""));
            
            return new Task(memberName, taskName, estimatedDays, progressPercentage);
        }
        return null;
    }
}
