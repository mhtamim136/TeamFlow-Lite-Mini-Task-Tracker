package GUI;

import Entity.Task;
import EntityList.TaskList;
import FileIO.FileIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class HomePage extends JFrame implements ActionListener {
    // Components declaration
    private JLabel titleLabel, memberLabel, taskLabel, daysLabel, progressLabel;
    private JTextField taskField, daysField;
    private JComboBox<String> memberComboBox;
    private JSlider progressSlider;
    private JButton addButton, updateButton, deleteButton, clearButton, logoutButton, summaryButton;
    private JTable taskTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    
    // Data management
    private TaskList taskList;
    private int selectedRow = -1;
    private LoginPage loginPage;
    
    public HomePage(LoginPage loginPage) {
        // Basic frame settings
        super("TeamFlow Lite - Task Management");
        this.loginPage = loginPage;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(245, 245, 245));
        
        // Initialize task list
        taskList = new TaskList();
        taskList.loadFromFile("Tasks.txt");
        
        // Title
        titleLabel = new JLabel("TeamFlow Lite - Task Management");
        titleLabel.setBounds(200, 20, 400, 40);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(41, 128, 185));
        
        // Task input section
        memberLabel = new JLabel("Member Name:");
        memberLabel.setBounds(50, 80, 100, 25);
        
        // Load member names from Users.txt
        ArrayList<String> members = FileIO.getAllUserNames();
        String[] memberArray = members.toArray(new String[0]);
        memberComboBox = new JComboBox<>(memberArray);
        memberComboBox.setBounds(150, 80, 180, 25);
        
        taskLabel = new JLabel("Task Name:");
        taskLabel.setBounds(50, 110, 100, 25);
        
        taskField = new JTextField();
        taskField.setBounds(150, 110, 180, 25);
        
        daysLabel = new JLabel("Estimated Days:");
        daysLabel.setBounds(50, 140, 100, 25);
        
        daysField = new JTextField();
        daysField.setBounds(150, 140, 180, 25);
        
        progressLabel = new JLabel("Progress (%):");
        progressLabel.setBounds(50, 170, 100, 25);
        
        progressSlider = new JSlider(0, 100, 0);
        progressSlider.setBounds(150, 170, 190, 40);
        progressSlider.setPaintTicks(true);
        progressSlider.setPaintLabels(true);
        progressSlider.setMajorTickSpacing(25);
        
        // Buttons
        addButton = new JButton("Add Task");
        addButton.setBounds(50, 240, 120, 30);
        addButton.setBackground(new Color(41, 128, 185));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(this);
        
        updateButton = new JButton("Update");
        updateButton.setBounds(180, 240, 120, 30);
        updateButton.setBackground(new Color(46, 204, 113));
        updateButton.setForeground(Color.WHITE);
        updateButton.addActionListener(this);
        
        deleteButton = new JButton("Delete");
        deleteButton.setBounds(50, 280, 120, 30);
        deleteButton.setBackground(new Color(231, 76, 60));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(this);
        
        clearButton = new JButton("Clear");
        clearButton.setBounds(180, 280, 120, 30);
        clearButton.setBackground(new Color(149, 165, 166));
        clearButton.setForeground(Color.WHITE);
        clearButton.addActionListener(this);
        
        summaryButton = new JButton("View Summary");
        summaryButton.setBounds(50, 320, 250, 30);
        summaryButton.setBackground(new Color(155, 89, 182));
        summaryButton.setForeground(Color.WHITE);
        summaryButton.addActionListener(this);
        
        logoutButton = new JButton("Logout");
        logoutButton.setBounds(680, 20, 80, 30);
        logoutButton.setBackground(new Color(243, 156, 18));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener(this);
        
        // Task table
        String[] columns = {"Member Name", "Task Name", "Est. Days", "Progress"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells non-editable
            }
        };
        
        taskTable = new JTable(tableModel);
        taskTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskTable.getTableHeader().setReorderingAllowed(false);
        taskTable.getTableHeader().setBackground(new Color(52, 152, 219));
        taskTable.getTableHeader().setForeground(Color.WHITE);
        
        scrollPane = new JScrollPane(taskTable);
        scrollPane.setBounds(350, 80, 400, 450);
        
        // Add selection listener for table
        taskTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedRow = taskTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Task task = taskList.getTaskByIndex(selectedRow);
                    memberComboBox.setSelectedItem(task.getMemberName());
                    taskField.setText(task.getTaskName());
                    daysField.setText(String.valueOf(task.getEstimatedDays()));
                    progressSlider.setValue(task.getProgressPercentage());
                }
            }
        });
        
        // Add components to frame
        this.add(titleLabel);
        this.add(memberLabel);
        this.add(memberComboBox);
        this.add(taskLabel);
        this.add(taskField);
        this.add(daysLabel);
        this.add(daysField);
        this.add(progressLabel);
        this.add(progressSlider);
        this.add(addButton);
        this.add(updateButton);
        this.add(deleteButton);
        this.add(clearButton);
        this.add(summaryButton);
        this.add(logoutButton);
        this.add(scrollPane);
        
        // Populate table with existing tasks
        refreshTable();
        
        // Make frame visible
        this.setVisible(true);
    }
    
    // Refresh the task table
    private void refreshTable() {
        tableModel.setRowCount(0);
        ArrayList<Task> tasks = taskList.getAllTasks();
        
        for (Task task : tasks) {
            String[] rowData = {
                task.getMemberName(),
                task.getTaskName(),
                String.valueOf(task.getEstimatedDays()),
                task.getProgressPercentage() + "%"
            };
            tableModel.addRow(rowData);
        }
    }
    
    // Clear input fields
    private void clearFields() {
        memberComboBox.setSelectedIndex(0);
        taskField.setText("");
        daysField.setText("");
        progressSlider.setValue(0);
        selectedRow = -1;
        taskTable.clearSelection();
    }
    
    // Handle button clicks
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String memberName = (String) memberComboBox.getSelectedItem();
            String taskName = taskField.getText();
            
            if (taskName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a task name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int estimatedDays;
            try {
                estimatedDays = Integer.parseInt(daysField.getText());
                if (estimatedDays <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number of days.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int progress = progressSlider.getValue();
            
            Task newTask = new Task(memberName, taskName, estimatedDays, progress);
            taskList.addTask(newTask);
            taskList.saveToFile("Tasks.txt");
            refreshTable();
            clearFields();
            
            JOptionPane.showMessageDialog(this, "Task added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
        } else if (e.getSource() == updateButton) {
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a task to update.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String memberName = (String) memberComboBox.getSelectedItem();
            String taskName = taskField.getText();
            
            if (taskName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a task name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int estimatedDays;
            try {
                estimatedDays = Integer.parseInt(daysField.getText());
                if (estimatedDays <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number of days.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int progress = progressSlider.getValue();
            
            Task updatedTask = new Task(memberName, taskName, estimatedDays, progress);
            taskList.updateTask(selectedRow, updatedTask);
            taskList.saveToFile("Tasks.txt");
            refreshTable();
            clearFields();
            
            JOptionPane.showMessageDialog(this, "Task updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
        } else if (e.getSource() == deleteButton) {
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a task to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this task?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                taskList.removeTask(selectedRow);
                taskList.saveToFile("Tasks.txt");
                refreshTable();
                clearFields();
                
                JOptionPane.showMessageDialog(this, "Task deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } else if (e.getSource() == clearButton) {
            clearFields();
            
        } else if (e.getSource() == summaryButton) {
            // Open the summary page
            SummaryPage summaryPage = new SummaryPage(this, taskList);
            summaryPage.setVisible(true);
            
        } else if (e.getSource() == logoutButton) {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose();
                loginPage.setVisible(true);
            }
        }
    }
}
