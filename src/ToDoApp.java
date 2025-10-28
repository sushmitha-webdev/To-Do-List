import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder; // For padding
import javax.swing.border.LineBorder; // For button borders
import javax.swing.border.TitledBorder;
import javax.swing.border.CompoundBorder; // For combining borders

public class ToDoApp extends JFrame {
	private DefaultListModel<String> taskListModel;
	private JList<String> taskList;
	private JTextField taskInput;
	private JButton addButton;
	private JButton deleteButton;
	private JButton completeButton;
	private JButton clearAllButton;

	// Define a color palette
	private static final Color PRIMARY_BG = new Color(240, 248, 255); // Alice Blue
	private static final Color PANEL_BG = new Color(224, 236, 255);   // Lighter Blueish
	private static final Color ACCENT_COLOR_ADD = new Color(76, 175, 80); // Green
	private static final Color ACCENT_COLOR_DELETE = new Color(244, 67, 54); // Red
	private static final Color ACCENT_COLOR_COMPLETE = new Color(33, 150, 243); // Blue
	private static final Color ACCENT_COLOR_CLEAR = new Color(200, 200, 200); // Grey
	private static final Color TEXT_COLOR = new Color(50, 50, 50); // Dark Grey
	private static final Color BORDER_COLOR = new Color(180, 200, 220); // Light Steel Blue

	public ToDoApp() 
	{
		setTitle("To-Do List App");
		setSize(550, 450); 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout(15, 15)); 
		getContentPane().setBackground(PRIMARY_BG); // Set main frame background

		// --- North Panel: Task Input and Add Button ---
		JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		inputPanel.setBackground(PANEL_BG);
		inputPanel.setBorder(new CompoundBorder(
				new EmptyBorder(10, 10, 0, 10), // Outer padding
				BorderFactory.createLineBorder(BORDER_COLOR, 1, true))); // Inner line border

		taskInput = new JTextField(25);
		taskInput.setFont(new Font("SansSerif", Font.PLAIN, 14));
		taskInput.setForeground(TEXT_COLOR);
		taskInput.setBorder(new CompoundBorder(
				BorderFactory.createLineBorder(BORDER_COLOR, 1),
				new EmptyBorder(5, 5, 5, 5))); // Padding inside text field

		addButton = new JButton("Add Task");
		styleButton(addButton, ACCENT_COLOR_ADD, Color.WHITE); // Custom styling
		inputPanel.add(taskInput);
		inputPanel.add(addButton);
		add(inputPanel, BorderLayout.NORTH);

		// --- Center Panel: Task List ---
		taskListModel = new DefaultListModel<>();
		taskList = new JList<>(taskListModel);
		taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		taskList.setFont(new Font("SansSerif", Font.PLAIN, 16)); // Larger font
		taskList.setForeground(TEXT_COLOR);
		taskList.setBackground(Color.WHITE); // White background for the list items
		taskList.setFixedCellHeight(25); // Give each row a consistent height for better appearance

		// Custom renderer for task completion (optional, but enhances visual)
		taskList.setCellRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				String taskText = (String) value;
				if (taskText.startsWith("✔ ")) {
					c.setForeground(new Color(100, 100, 100)); // Grey out completed tasks
					// For strikethrough, you'd need a JLabel and set its font with TextAttribute.STRIKETHROUGH
					// This is a simpler visual indication.
				} 
				else {
					c.setForeground(TEXT_COLOR);
				}
				// Maintain selection colors
				if (isSelected) {
					c.setBackground(ACCENT_COLOR_COMPLETE.brighter()); // Lighter blue for selection
				} 
				else {
					c.setBackground(Color.WHITE);
				}
				return c;
			}
		});


		JScrollPane listScrollPane = new JScrollPane(taskList);
		listScrollPane.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
				"Your Tasks",
				TitledBorder.CENTER,
				TitledBorder.TOP,
				new Font("SansSerif", Font.BOLD, 14),
				TEXT_COLOR)); // Styled titled border
		listScrollPane.setBackground(PANEL_BG); // Scroll pane background
		add(listScrollPane, BorderLayout.CENTER);

		// --- South Panel: Action Buttons (Delete, Mark Complete, Clear All) ---
		JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 15, 0)); // 1 row, 3 columns, increased horizontal gap
		buttonPanel.setBackground(PANEL_BG);
		buttonPanel.setBorder(new CompoundBorder(
				new EmptyBorder(0, 10, 10, 10), // Outer padding
				BorderFactory.createLineBorder(BORDER_COLOR, 1, true))); // Inner line border

		deleteButton = new JButton("Delete Selected Task");
		styleButton(deleteButton, ACCENT_COLOR_DELETE, Color.WHITE);

		completeButton = new JButton("Mark Task Complete");
		styleButton(completeButton, ACCENT_COLOR_COMPLETE, Color.WHITE);

		clearAllButton = new JButton("Clear All Tasks");
		styleButton(clearAllButton, ACCENT_COLOR_CLEAR, Color.WHITE);

		buttonPanel.add(deleteButton);
		buttonPanel.add(completeButton);
		buttonPanel.add(clearAllButton);
		add(buttonPanel, BorderLayout.SOUTH);

		// --- Action Listeners ---
		addButton.addActionListener(e -> addTask());
		taskInput.addActionListener(e -> addTask());

		deleteButton.addActionListener(e -> deleteTask());
		completeButton.addActionListener(e -> markTaskComplete());
		clearAllButton.addActionListener(e -> clearAllTasks());
	}

	// Helper method to style buttons
	private void styleButton(JButton button, Color bgColor, Color fgColor) {
		button.setBackground(bgColor);
		button.setForeground(fgColor);
		button.setFont(new Font("SansSerif", Font.BOLD, 12));
		button.setFocusPainted(false); // Remove focus border
		button.setBorder(new CompoundBorder(
				new LineBorder(bgColor.darker(), 1, true), // Darker border matching background
				new EmptyBorder(8, 15, 8, 15))); // Internal padding
		button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
		// Add a hover effect
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setBackground(bgColor.brighter());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setBackground(bgColor);
			}
		});
	}


	private void addTask() {
		String task = taskInput.getText().trim();
		if (task.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Task description cannot be empty!", "Input Error", JOptionPane.WARNING_MESSAGE);
		} 
		else {
			taskListModel.addElement(task);
			taskInput.setText("");
			taskInput.requestFocusInWindow();
		}
	}

	private void deleteTask() {
		int selectedIndex = taskList.getSelectedIndex();
		if (selectedIndex == -1) {
			JOptionPane.showMessageDialog(this, "Please select a task to delete.", "Selection Error", JOptionPane.WARNING_MESSAGE);
		} 
		else {
			taskListModel.remove(selectedIndex);
		}
	}

	private void markTaskComplete() {
		int selectedIndex = taskList.getSelectedIndex();
		if (selectedIndex == -1) {
			JOptionPane.showMessageDialog(this, "Please select a task to mark complete.", "Selection Error", JOptionPane.WARNING_MESSAGE);
		} 
		else {
			String selectedTask = taskListModel.getElementAt(selectedIndex);
			if (!selectedTask.startsWith("✔ ")) {
				taskListModel.setElementAt("✔ " + selectedTask, selectedIndex);
			} 
			else {
				// Optional: allow un-marking
				taskListModel.setElementAt(selectedTask.substring(2), selectedIndex);
			}
		}
	}


	private void clearAllTasks() {
		if (taskListModel.isEmpty()) {
			JOptionPane.showMessageDialog(this, "The list is already empty!", "No Tasks", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		int response = JOptionPane.showConfirmDialog(this,
				"Are you sure you want to clear all tasks?",
				"Confirm Clear All",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE);
		if (response == JOptionPane.YES_OPTION) {
			taskListModel.clear();
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			ToDoApp app = new ToDoApp();
			app.setLocationRelativeTo(null);
			app.setVisible(true);
		});
	}
}