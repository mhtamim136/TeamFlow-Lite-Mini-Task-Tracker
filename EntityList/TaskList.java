package EntityList;
import Entity.Task;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class TaskList {
    // Private attribute to store tasks
    private ArrayList<Task> tasks;
    
    // Constructor
    public TaskList() {
        tasks = new ArrayList<>();
    }
    
    // Method to add a task
    public void addTask(Task task) {
        tasks.add(task);
    }
    
    // Method to remove a task
    public void removeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
        }
    }
    
    // Method to update a task
    public void updateTask(int index, Task updatedTask) {
        if (index >= 0 && index < tasks.size()) {
            tasks.set(index, updatedTask);
        }
    }
    
    // Method to get all tasks
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }
    
    // Method to search tasks by member name
    public ArrayList<Task> searchByMember(String memberName) {
        ArrayList<Task> result = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getMemberName().equalsIgnoreCase(memberName)) {
                result.add(task);
            }
        }
        return result;
    }
    
    // Method to get task count by member
    public int getTaskCountByMember(String memberName) {
        int count = 0;
        for (Task task : tasks) {
            if (task.getMemberName().equalsIgnoreCase(memberName)) {
                count++;
            }
        }
        return count;
    }
    
    // Method to calculate average progress for a member
    public double getAverageProgressByMember(String memberName) {
        ArrayList<Task> memberTasks = searchByMember(memberName);
        if (memberTasks.isEmpty()) {
            return 0;
        }
        
        int totalProgress = 0;
        for (Task task : memberTasks) {
            totalProgress += task.getProgressPercentage();
        }
        
        return (double) totalProgress / memberTasks.size();
    }
    
    // Method to get task by index
    public Task getTaskByIndex(int index) {
        if (index >= 0 && index < tasks.size()) {
            return tasks.get(index);
        }
        return null;
    }
    
    // Method to load tasks from file
    public void loadFromFile(String filename) {
        tasks.clear();
        File file = new File(filename);
        
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task task = Task.fromString(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    
    // Method to save tasks to file
    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Task task : tasks) {
                writer.println(task.toString());
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    
    // Method to get size of task list
    public int size() {
        return tasks.size();
    }
    
    // Method to clear all tasks
    public void clear() {
        tasks.clear();
    }
}
