package FileIO;

import java.io.*;
import java.util.Scanner;
import Entity.Task;
import java.util.ArrayList;

public class FileIO {
    private static final String USERS_FILE = "./Files/users.txt";
    private static final String TASKS_FILE = "./Files/tasks.txt";
    
    // Method to write a new user to the users.txt file
    public static void writeUserInFile(String userName, String email, String password) {
        File file = new File(USERS_FILE);
        
        // Create directory if it doesn't exist
        File directory = new File("./Files");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        // Count number of existing users
        int userNumber = 0;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                userNumber++;
            }
        } catch (FileNotFoundException fNFE) {
            System.out.println("File not found - will be created");
        }
        
        // Write the new user
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            String line = (userNumber + 1) + ";" + userName + ";" + password + ";" + email + "\n";
            fileWriter.write(line);
        } catch (IOException IOE) {
            System.out.println("I/O Error: " + IOE.getMessage());
        }
    }
    
    // Add missing methods
public static ArrayList<String> getAllUserNames() {
    ArrayList<String> userNames = new ArrayList<>();
    File file = new File(USERS_FILE);
    
    try (Scanner scanner = new Scanner(file)) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] data = line.split(";");
            if (data.length >= 2) {
                userNames.add(data[1]);
            }
        }
    } catch (FileNotFoundException e) {
        System.out.println("File not found: " + e.getMessage());
    }
    
    return userNames;
}

public static boolean userExists(String username) {
    return checkUserByName(username);
}

public static boolean addUser(String username, String password, String email) {
    try {
        writeUserInFile(username, email, password);
        return true;
    } catch (Exception e) {
        System.out.println("Error adding user: " + e.getMessage());
        return false;
    }
}

    
    // Method to check if a user exists with the given username and password
    public static boolean checkUserByNameAndPass(String userName, String userPass) {
        boolean flag = false;
        File file = new File(USERS_FILE);
        
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(";");
                if (userName.equals(data[1]) && userPass.equals(data[2])) {
                    flag = true;
                    break;
                }
            }
        } catch (FileNotFoundException fNFE) {
            System.out.println("File Not Found");
        } catch (Exception ex) {
            System.out.println("Can't Read The File: " + ex.getMessage());
        }
        
        return flag;
    }
    
    // Method to check if a username already exists
    public static boolean checkUserByName(String userName) {
        boolean flag = false;
        File file = new File(USERS_FILE);
        
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(";");
                if (userName.equals(data[1])) {
                    flag = true;
                    break;
                }
            }
        } catch (FileNotFoundException fNFE) {
            System.out.println("File Not Found");
        } catch (Exception ex) {
            System.out.println("Can't Read The File: " + ex.getMessage());
        }
        
        return flag;
    }
    
    // Method to load tasks from the tasks.txt file
    public static void loadTasksFromFile(EntityList.TaskList taskList) {
        taskList.loadFromFile(TASKS_FILE);
    }
    
    // Method to save tasks to the tasks.txt file
    public static void saveTasksToFile(EntityList.TaskList taskList) {
        taskList.saveToFile(TASKS_FILE);
    }
    
    // Method to create necessary files and directories if they don't exist
    public static void initializeFiles() {
        File directory = new File("./Files");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        File usersFile = new File(USERS_FILE);
        if (!usersFile.exists()) {
            try {
                usersFile.createNewFile();
                // Create a default admin user
                writeUserInFile("admin", "admin@teamflow.com", "admin123");
            } catch (IOException e) {
                System.out.println("Error creating users file: " + e.getMessage());
            }
        }
        
        File tasksFile = new File(TASKS_FILE);
        if (!tasksFile.exists()) {
            try {
                tasksFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating tasks file: " + e.getMessage());
            }
        }
    }
}
