-- Sample data for testing
INSERT INTO employee (name, email, department) VALUES
('John Doe', 'john.doe@example.com', 'IT'),
('Jane Smith', 'jane.smith@example.com', 'HR'),
('Bob Johnson', 'bob.johnson@example.com', 'Finance');

INSERT INTO task (title, description, deadline, status, employee_id) VALUES
('Fix login bug', 'Resolve the authentication issue in the login module', '2024-12-31', 'Pending', 1),
('Update employee records', 'Update contact information for all employees', '2024-11-30', 'In Progress', 2),
('Prepare financial report', 'Generate quarterly financial statements', '2024-10-31', 'Completed', 3);