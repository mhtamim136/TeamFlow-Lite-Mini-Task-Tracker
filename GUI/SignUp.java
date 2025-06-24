package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import FileIO.FileIO;

public class SignUp extends JFrame implements ActionListener {
    // Components declaration
    private JLabel userLabel, passLabel, confirmPassLabel, emailLabel, titleLabel, loginLabel;
    private JTextField userTextField, userEmailField;
    private JPasswordField userPassField, userConfirmPass;
    private JButton registerButton, loginButton;
    
    // Reference to previous page for navigation
    private JFrame previousPage;
    
    // Fonts for consistent styling
    private Font titleFont = new Font("Arial", Font.BOLD, 24);
    private Font labelFont = new Font("Arial", Font.PLAIN, 14);
    private Font buttonFont = new Font("Arial", Font.BOLD, 14);
    
    public SignUp(JFrame previousPage) {
        // Basic frame setup
        super("TeamFlow Lite - Register");
        this.previousPage = previousPage;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(420, 450);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(245, 245, 245));
        
        // Title
        titleLabel = new JLabel("Create Account");
        titleLabel.setBounds(100, 30, 220, 40);
        titleLabel.setFont(titleFont);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(41, 128, 185));
        this.add(titleLabel);
        
        // Username
        userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 90, 100, 30);
        userLabel.setFont(labelFont);
        this.add(userLabel);
        
        userTextField = new JTextField();
        userTextField.setBounds(170, 90, 180, 30);
        this.add(userTextField);
        
        // Email
        emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 140, 100, 30);
        emailLabel.setFont(labelFont);
        this.add(emailLabel);
        
        userEmailField = new JTextField();
        userEmailField.setBounds(170, 140, 180, 30);
        this.add(userEmailField);
        
        // Password
        passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 190, 100, 30);
        passLabel.setFont(labelFont);
        this.add(passLabel);
        
        userPassField = new JPasswordField();
        userPassField.setBounds(170, 190, 180, 30);
        this.add(userPassField);
        
        // Confirm Password
        confirmPassLabel = new JLabel("Confirm Password:");
        confirmPassLabel.setBounds(50, 240, 120, 30);
        confirmPassLabel.setFont(labelFont);
        this.add(confirmPassLabel);
        
        userConfirmPass = new JPasswordField();
        userConfirmPass.setBounds(170, 240, 180, 30);
        this.add(userConfirmPass);
        
        // Register Button
        registerButton = new JButton("Register");
        registerButton.setBounds(170, 290, 180, 35);
        registerButton.setFont(buttonFont);
        registerButton.setBackground(new Color(41, 128, 185));
        registerButton.setForeground(Color.WHITE);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.addActionListener(this);
        this.add(registerButton);
        
        // Return to login option
        loginLabel = new JLabel("Already have an account?");
        loginLabel.setBounds(90, 350, 180, 30);
        loginLabel.setFont(labelFont);
        this.add(loginLabel);
        
        loginButton = new JButton("Login");
        loginButton.setBounds(250, 350, 80, 30);
        loginButton.setFont(buttonFont);
        loginButton.setBorder(null);
        loginButton.setForeground(new Color(41, 128, 185));
        loginButton.setBackground(new Color(245, 245, 245));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(this);
        this.add(loginButton);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            // Get user input
            String username = userTextField.getText();
            String email = userEmailField.getText();
            String password = String.valueOf(userPassField.getPassword());
            String confirmPassword = String.valueOf(userConfirmPass.getPassword());
            
            // Validate input
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid email address", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Check if username already exists
            if (FileIO.userExists(username)) {
                JOptionPane.showMessageDialog(this, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Add user to file
            if (FileIO.addUser(username, password, email)) {
                JOptionPane.showMessageDialog(this, "Registration successful! You can now login.", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Return to login page
                previousPage.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } 
        else if (e.getSource() == loginButton) {
            // Return to login page
            if (previousPage != null) {
                previousPage.setVisible(true);
                this.dispose();
            }
        }
    }
    
    // For testing the SignUp page independently
    public static void main(String[] args) {
        new SignUp(null);
    }
}
