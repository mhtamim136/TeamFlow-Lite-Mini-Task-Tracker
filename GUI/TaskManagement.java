package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TaskManagement extends JFrame {
    private LoginPage loginPage;
    
    public TaskManagement(LoginPage loginPage) {
        super("TeamFlow Lite - Task Management");
        this.loginPage = loginPage;
        
        // Basic frame settings
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        
        // Add a simple label for now - can be enhanced later
        JLabel label = new JLabel("Task Management System", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        this.add(label, BorderLayout.CENTER);
        
        // Make frame visible
        this.setVisible(true);
    }
} 