const employeeForm = document.getElementById("employeeForm");
const taskForm = document.getElementById("taskForm");
const employeesTableBody = document.getElementById("employeesTableBody");
const employeesEmpty = document.getElementById("employeesEmpty");
const employeesTableWrap = document.getElementById("employeesTableWrap");
const taskCards = document.getElementById("taskCards");
const tasksEmpty = document.getElementById("tasksEmpty");
const taskEmployeeSelect = document.getElementById("taskEmployee");
const employeeFilter = document.getElementById("employeeFilter");
const toast = document.getElementById("toast");
const heroEmployeeCount = document.getElementById("heroEmployeeCount");
const heroTaskCount = document.getElementById("heroTaskCount");

document.getElementById("resetEmployeeBtn").addEventListener("click", resetEmployeeForm);
document.getElementById("resetTaskBtn").addEventListener("click", resetTaskForm);
document.getElementById("refreshEmployeesBtn").addEventListener("click", loadEmployees);
document.getElementById("refreshTasksBtn").addEventListener("click", loadTasks);
employeeFilter.addEventListener("change", loadTasks);

employeeForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    const employeeId = document.getElementById("employeeId").value;
    const payload = {
        name: document.getElementById("employeeName").value.trim(),
        email: document.getElementById("employeeEmail").value.trim(),
        department: document.getElementById("employeeDepartment").value.trim(),
        designation: document.getElementById("employeeDesignation").value.trim()
    };

    const method = employeeId ? "PUT" : "POST";
    const url = employeeId ? `/api/employees/${employeeId}` : "/api/employees";

    try {
        await request(url, { method, body: JSON.stringify(payload) });
        showToast(employeeId ? "Employee updated successfully." : "Employee created successfully.");
        resetEmployeeForm();
        await loadEmployees();
        await loadTasks();
    } catch (error) {
        showToast(error.message, true);
    }
});

taskForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    const taskId = document.getElementById("taskId").value;
    const payload = {
        title: document.getElementById("taskTitle").value.trim(),
        description: document.getElementById("taskDescription").value.trim(),
        assignedEmployeeId: Number(document.getElementById("taskEmployee").value),
        deadline: document.getElementById("taskDeadline").value,
        status: document.getElementById("taskStatus").value,
        priority: document.getElementById("taskPriority").value
    };

    const method = taskId ? "PUT" : "POST";
    const url = taskId ? `/api/tasks/${taskId}` : "/api/tasks";

    try {
        await request(url, { method, body: JSON.stringify(payload) });
        showToast(taskId ? "Task updated successfully." : "Task created successfully.");
        resetTaskForm();
        await loadTasks();
    } catch (error) {
        showToast(error.message, true);
    }
});

async function loadEmployees() {
    try {
        const employees = await request("/api/employees/list");
        heroEmployeeCount.textContent = `${employees.length} Employees`;
        renderEmployeeOptions(employees);
        renderEmployees(employees);
    } catch (error) {
        showToast(error.message, true);
    }
}

async function loadTasks() {
    try {
        const filterEmployeeId = employeeFilter.value;
        const endpoint = filterEmployeeId ? `/api/tasks/employee/${filterEmployeeId}/list` : "/api/tasks/list";
        const tasks = await request(endpoint);
        heroTaskCount.textContent = `${tasks.length} Tasks`;
        renderTasks(tasks);
    } catch (error) {
        showToast(error.message, true);
    }
}

function renderEmployees(employees) {
    employeesTableBody.innerHTML = "";

    if (!employees.length) {
        employeesEmpty.classList.remove("hidden");
        employeesTableWrap.classList.add("hidden");
        return;
    }

    employeesEmpty.classList.add("hidden");
    employeesTableWrap.classList.remove("hidden");

    employees.forEach((employee) => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${escapeHtml(employee.name)}</td>
            <td>${escapeHtml(employee.email)}</td>
            <td>${escapeHtml(employee.department)}</td>
            <td>${escapeHtml(employee.designation)}</td>
            <td>
                <div class="actions">
                    <button class="chip-btn" type="button" data-edit-employee="${employee.id}">Edit</button>
                    <button class="danger-btn" type="button" data-delete-employee="${employee.id}">Delete</button>
                </div>
            </td>
        `;
        employeesTableBody.appendChild(row);
    });

    employeesTableBody.querySelectorAll("[data-edit-employee]").forEach((button) => {
        button.addEventListener("click", async () => {
            const employee = await request(`/api/employees/${button.dataset.editEmployee}`);
            document.getElementById("employeeId").value = employee.id;
            document.getElementById("employeeName").value = employee.name;
            document.getElementById("employeeEmail").value = employee.email;
            document.getElementById("employeeDepartment").value = employee.department;
            document.getElementById("employeeDesignation").value = employee.designation;
            window.scrollTo({ top: 0, behavior: "smooth" });
        });
    });

    employeesTableBody.querySelectorAll("[data-delete-employee]").forEach((button) => {
        button.addEventListener("click", async () => {
            const confirmed = window.confirm("Delete this employee? Make sure no tasks are assigned first.");
            if (!confirmed) {
                return;
            }

            try {
                await request(`/api/employees/${button.dataset.deleteEmployee}`, { method: "DELETE" });
                showToast("Employee deleted successfully.");
                await loadEmployees();
                await loadTasks();
            } catch (error) {
                showToast(error.message, true);
            }
        });
    });
}

function renderEmployeeOptions(employees) {
    const options = employees
        .map((employee) => `<option value="${employee.id}">${escapeHtml(employee.name)} - ${escapeHtml(employee.department)}</option>`)
        .join("");

    const selectedTaskEmployee = taskEmployeeSelect.value;
    const selectedFilter = employeeFilter.value;

    taskEmployeeSelect.innerHTML = `<option value="">Select employee</option>${options}`;
    employeeFilter.innerHTML = `<option value="">All Employees</option>${options}`;

    taskEmployeeSelect.value = employees.some((employee) => String(employee.id) === selectedTaskEmployee) ? selectedTaskEmployee : "";
    employeeFilter.value = employees.some((employee) => String(employee.id) === selectedFilter) ? selectedFilter : "";
}

function renderTasks(tasks) {
    taskCards.innerHTML = "";

    if (!tasks.length) {
        tasksEmpty.classList.remove("hidden");
        return;
    }

    tasksEmpty.classList.add("hidden");

    tasks.forEach((task) => {
        const statusClass = task.status === "IN_PROGRESS"
            ? "in-progress"
            : task.status.toLowerCase();

        const card = document.createElement("article");
        card.className = "task-card";
        card.innerHTML = `
            <div class="status-row">
                <span class="pill ${statusClass}">${formatStatus(task.status)}</span>
                ${task.overdue ? '<span class="pill overdue">Overdue</span>' : ""}
            </div>
            <h3>${escapeHtml(task.title)}</h3>
            <p>${escapeHtml(task.description)}</p>
            <div class="task-meta">
                <div>
                    <strong>Assigned To</strong>
                    <span>${escapeHtml(task.assignedEmployee.name)}</span>
                </div>
                <div>
                    <strong>Department</strong>
                    <span>${escapeHtml(task.assignedEmployee.department)}</span>
                </div>
                <div>
                    <strong>Deadline</strong>
                    <span>${escapeHtml(task.deadline)}</span>
                </div>
                <div>
                    <strong>Priority</strong>
                    <span>${escapeHtml(task.priority)}</span>
                </div>
            </div>
            <div class="actions">
                <button class="chip-btn" type="button" data-edit-task="${task.id}">Edit</button>
                <button class="chip-btn" type="button" data-progress-task="${task.id}">Mark In Progress</button>
                <button class="chip-btn" type="button" data-complete-task="${task.id}">Mark Completed</button>
                <button class="danger-btn" type="button" data-delete-task="${task.id}">Delete</button>
            </div>
        `;
        taskCards.appendChild(card);
    });

    taskCards.querySelectorAll("[data-edit-task]").forEach((button) => {
        button.addEventListener("click", async () => {
            const task = await request(`/api/tasks/${button.dataset.editTask}`);
            document.getElementById("taskId").value = task.id;
            document.getElementById("taskTitle").value = task.title;
            document.getElementById("taskDescription").value = task.description;
            document.getElementById("taskEmployee").value = task.assignedEmployee.id;
            document.getElementById("taskDeadline").value = task.deadline;
            document.getElementById("taskStatus").value = task.status;
            document.getElementById("taskPriority").value = task.priority;
            window.scrollTo({ top: 0, behavior: "smooth" });
        });
    });

    taskCards.querySelectorAll("[data-progress-task]").forEach((button) => {
        button.addEventListener("click", () => updateTaskStatus(button.dataset.progressTask, "IN_PROGRESS"));
    });

    taskCards.querySelectorAll("[data-complete-task]").forEach((button) => {
        button.addEventListener("click", () => updateTaskStatus(button.dataset.completeTask, "COMPLETED"));
    });

    taskCards.querySelectorAll("[data-delete-task]").forEach((button) => {
        button.addEventListener("click", async () => {
            const confirmed = window.confirm("Delete this task?");
            if (!confirmed) {
                return;
            }

            try {
                await request(`/api/tasks/${button.dataset.deleteTask}`, { method: "DELETE" });
                showToast("Task deleted successfully.");
                await loadTasks();
            } catch (error) {
                showToast(error.message, true);
            }
        });
    });
}

async function updateTaskStatus(taskId, status) {
    try {
        await request(`/api/tasks/${taskId}/status`, {
            method: "PATCH",
            body: JSON.stringify({ status })
        });
        showToast(`Task status updated to ${formatStatus(status)}.`);
        await loadTasks();
    } catch (error) {
        showToast(error.message, true);
    }
}

async function request(url, options = {}) {
    const response = await fetch(url, {
        headers: {
            "Content-Type": "application/json"
        },
        ...options
    });

    if (!response.ok) {
        const errorBody = await response.json().catch(() => null);
        const validationErrors = errorBody?.validationErrors
            ? Object.values(errorBody.validationErrors).join(", ")
            : null;
        throw new Error(validationErrors || errorBody?.message || "Request failed.");
    }

    if (response.status === 204) {
        return null;
    }

    const text = await response.text();
    return text ? JSON.parse(text) : null;
}

function resetEmployeeForm() {
    employeeForm.reset();
    document.getElementById("employeeId").value = "";
}

function resetTaskForm() {
    taskForm.reset();
    document.getElementById("taskId").value = "";
}

function showToast(message, isError = false) {
    toast.textContent = message;
    toast.style.background = isError ? "#7a1c18" : "#13232d";
    toast.classList.remove("hidden");
    clearTimeout(showToast.timeoutId);
    showToast.timeoutId = setTimeout(() => {
        toast.classList.add("hidden");
    }, 3200);
}

function formatStatus(status) {
    return status
        .toLowerCase()
        .split("_")
        .map((part) => part.charAt(0).toUpperCase() + part.slice(1))
        .join(" ");
}

function escapeHtml(value) {
    return String(value)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#39;");
}

loadEmployees().then(loadTasks);
