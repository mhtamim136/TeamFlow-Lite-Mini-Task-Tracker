package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import EntityList.TaskList;
import Entity.Task;
import java.util.ArrayList;

public class SummaryPage extends JFrame {
    private JFrame parentFrame;
    private TaskList taskList;
    
    public SummaryPage(JFrame parentFrame, TaskList taskList) {
        super("TeamFlow Lite - Summary");
        this.parentFrame = parentFrame;
        this.taskList = taskList;
        
        // Basic frame settings
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(600, 400);
        this.setLocationRelativeTo(parentFrame);
        this.setLayout(new BorderLayout());
        
        // Create summary content
        createSummaryContent();
        
        // Make frame visible
        this.setVisible(true);
    }
    
    private void createSummaryContent() {
        // Title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Task Summary Report");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(41, 128, 185));
        titlePanel.add(titleLabel);
        this.add(titlePanel, BorderLayout.NORTH);
        
        // Summary content
        JTextArea summaryArea = new JTextArea();
        summaryArea.setEditable(false);
        summaryArea.setFont(new Font("Arial", Font.PLAIN, 14));
        summaryArea.setMargin(new Insets(10, 10, 10, 10));
        
        StringBuilder summary = new StringBuilder();
        summary.append("=== TASK SUMMARY REPORT ===\n\n");
        summary.append("Total Tasks: ").append(taskList.size()).append("\n\n");
        
        // Get all unique members
        ArrayList<String> members = new ArrayList<>();
        for (Task task : taskList.getAllTasks()) {
            if (!members.contains(task.getMemberName())) {
                members.add(task.getMemberName());
            }
        }
        
        // Summary for each member
        for (String member : members) {
            int taskCount = taskList.getTaskCountByMember(member);
            double avgProgress = taskList.getAverageProgressByMember(member);
            
            summary.append("Member: ").append(member).append("\n");
            summary.append("  - Total Tasks: ").append(taskCount).append("\n");
            summary.append("  - Average Progress: ").append(String.format("%.1f", avgProgress)).append("%\n\n");
        }
        
        summaryArea.setText(summary.toString());
        
        JScrollPane scrollPane = new JScrollPane(summaryArea);
        this.add(scrollPane, BorderLayout.CENTER);
        
        // Close button
        JPanel buttonPanel = new JPanel();
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> this.dispose());
        buttonPanel.add(closeButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }
} 