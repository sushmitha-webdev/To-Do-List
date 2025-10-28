# To-Do List App (Java Swing)

A simple yet enhanced To-Do List application built using Java Swing, demonstrating basic GUI development, event handling, and custom component rendering. This app helps users manage tasks with a clear, colorful interface.

## Features

-   **Add Tasks**: Quickly add new tasks to the list.
-   **Delete Tasks**: Remove selected tasks from the list.
-   **Mark Complete**: Toggle tasks as complete (indicated by a checkmark and greyed-out text).
-   **Clear All Tasks**: Empty the entire list with a confirmation dialog.
-   **User-Friendly UI**: Responsive buttons, clear input fields, and informative dialogs.
-   **Color-Themed Interface**: A custom color scheme applied throughout for a pleasant visual experience.

## What's Implemented 

This application uses Java Swing components:
-   **`JFrame`**: The main window.
-   **`DefaultListModel` & `JList`**: To manage and display the list of tasks.
-   **`JTextField`**: For user input to add new tasks.
-   **`JButton`**: For Add, Delete, Mark Complete, and Clear All actions.
-   **Layout Managers**: `BorderLayout` and `GridLayout` are used to organize components effectively.
-   **Event Handling**: `ActionListener`s are set up for buttons and the text field.
-   **Custom Rendering**: A `ListCellRenderer` is implemented to visually differentiate completed tasks (greyed out with a checkmark).
-   **Styling**: Custom `Color`s, `Font`s, `Border`s, and `MouseListener`s (for button hover effects) are used to enhance the application's aesthetic appeal. `JOptionPane` is used for user feedback and confirmation dialogs.

## Example Output

<img width="664" height="554" alt="Screenshot 2025-10-28 120748" src="https://github.com/user-attachments/assets/127add40-9287-47bc-a9b0-1763ee1f80e3" />
