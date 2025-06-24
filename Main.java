import GUI.LoginPage;
import FileIO.FileIO;

public class Main {
    public static void main(String[] args) {
        System.out.println("TeamFlow Lite starting...");
        
        try {
            // Initialize necessary files first
            FileIO.initializeFiles();
            System.out.println("Files initialized successfully.");
            
            // Launch the application with the login page
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    try {
                        System.out.println("Launching LoginPage...");
                        LoginPage loginPage = new LoginPage();
                        System.out.println("LoginPage created successfully.");
                        System.out.println("LoginPage is visible: " + loginPage.isVisible());
                        System.out.println("LoginPage is displayable: " + loginPage.isDisplayable());
                    } catch (Exception e) {
                        System.err.println("Error creating LoginPage: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });
            
            System.out.println("Main method completed.");
            
            // Keep the main thread alive to see the GUI
            Thread.sleep(2000);
            System.out.println("Application should be running...");
            
        } catch (Exception e) {
            System.err.println("Error in main: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
