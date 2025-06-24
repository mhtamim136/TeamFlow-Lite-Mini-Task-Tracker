package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.prefs.Preferences;
import FileIO.FileIO;

public class LoginPage extends JFrame implements ActionListener {
    // Components declaration
    private JTextField userTextField;
    private JPasswordField userPassField;
    private JButton loginButton, registerButton;
    private JCheckBox rememberMeCheckBox;
    private JLabel userLabel, passLabel, titleLabel, registerLabel;
    
    // For storing user preferences
    private Preferences prefs = Preferences.userNodeForPackage(LoginPage.class);
    
    public LoginPage() {
        // Basic frame settings
        super("TeamFlow Lite - Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 350);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(245, 245, 245));
        
        // Title
        titleLabel = new JLabel("TeamFlow Lite");
        titleLabel.setBounds(100, 30, 200, 40);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(41, 128, 185));
        
        // Username label and field
        userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 90, 100, 30);
        
        userTextField = new JTextField();
        userTextField.setBounds(150, 90, 180, 30);
        
        // Password label and field
        passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 140, 100, 30);
        
        userPassField = new JPasswordField();
        userPassField.setBounds(150, 140, 180, 30);
        
        // Remember me checkbox
        rememberMeCheckBox = new JCheckBox("Remember Me");
        rememberMeCheckBox.setBounds(150, 180, 180, 30);
        rememberMeCheckBox.setBackground(new Color(245, 245, 245));
        
        // Login button
        loginButton = new JButton("Login");
        loginButton.setBounds(150, 220, 180, 35);
        loginButton.setBackground(new Color(41, 128, 185));
        loginButton.setForeground(Color.WHITE);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(this);
        
        // Register label and button
        registerLabel = new JLabel("Don't have an account?");
        registerLabel.setBounds(90, 270, 150, 30);
        
        registerButton = new JButton("Sign Up");
        registerButton.setBounds(230, 270, 100, 30);
        registerButton.setBackground(new Color(245, 245, 245));
        registerButton.setBorderPainted(false);
        registerButton.setForeground(new Color(41, 128, 185));
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.addActionListener(this);
        
        // Add components to frame
        this.add(titleLabel);
        this.add(userLabel);
        this.add(userTextField);
        this.add(passLabel);
        this.add(userPassField);
        this.add(rememberMeCheckBox);
        this.add(loginButton);
        this.add(registerLabel);
        this.add(registerButton);
        
        // Load saved credentials if any
        String savedUsername = prefs.get("username", "");
        String savedPassword = prefs.get("password", "");
        
        if (!savedUsername.isEmpty() && !savedPassword.isEmpty()) {
            userTextField.setText(savedUsername);
            userPassField.setText(savedPassword);
            rememberMeCheckBox.setSelected(true);
        }
        
        // Make frame visible
        this.setVisible(true);
        this.toFront();
        this.requestFocus();
    }
    
    // Handle button clicks
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String userName = userTextField.getText();
            String userPass = String.valueOf(userPassField.getPassword());
            
            // Check credentials
            if (userName.isEmpty() || userPass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter username and password.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (FileIO.checkUserByNameAndPass(userName, userPass)) {
                JOptionPane.showMessageDialog(this, "Welcome " + userName.substring(0,1).toUpperCase() + userName.substring(1).toLowerCase());
                
                // Save credentials if remember me is checked
                if (rememberMeCheckBox.isSelected()) {
                    prefs.put("username", userName);
                    prefs.put("password", userPass);
                } else {
                    prefs.remove("username");
                    prefs.remove("password");
                }
                
                // Open main application window
                HomePage homePage = new HomePage(this);
                homePage.setVisible(true);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == registerButton) {
            // Open registration page
            SignUp signUp = new SignUp(this);
            signUp.setVisible(true);
            this.setVisible(false);
        }
    }
    
    // Main method for testing
    public static void main(String[] args) {
        new LoginPage();
    }
}
