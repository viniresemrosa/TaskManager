import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<Task> tasks;
    private String filePath;

    public TaskManager(String filePath) {
        this.filePath = filePath;
        this.tasks = JsonUtil.loadTasks(filePath);
    }

    // Calcula o próximo id disponível (maior id existente + 1, nunca reutiliza ids)
    private int getNextId() {
        if (tasks.isEmpty()) {
            return 1;
        }
        int maiorId = 0;
        for (Task t : tasks) {
            if (t.getId() > maiorId) {
                maiorId = t.getId();
            }
        }
        return maiorId + 1;
    }

    // Método auxiliar reutilizado por update, delete, mark-in-progress, mark-done
    // Percorre a lista procurando a task com o id informado.
    // Retorna null se não encontrar (usado pelos métodos públicos para tratar erro).
    private Task findTaskById(int id) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    public void addTask(String description) {
        int id = getNextId();
        String agora = LocalDateTime.now().toString();

        Task novaTask = new Task(id, description, "todo", agora, agora);
        tasks.add(novaTask);

        JsonUtil.saveTasks(tasks, filePath);
        System.out.println("Task added successfully (ID: " + id + ")");
    }

    public void updateTask(int id, String novaDescricao) {
        Task task = findTaskById(id);

        if (task == null) {
            System.out.println("Error: Task with ID " + id + " not found.");
            return;
        }

        task.setDescription(novaDescricao);
        task.setUpdatedAt(LocalDateTime.now().toString());

        JsonUtil.saveTasks(tasks, filePath);
        System.out.println("Task updated successfully (ID: " + id + ")");
    }

    public void deleteTask(int id) {
        Task task = findTaskById(id);

        if (task == null) {
            System.out.println("Error: Task with ID " + id + " not found.");
            return;
        }

        tasks.remove(task);

        JsonUtil.saveTasks(tasks, filePath);
        System.out.println("Task deleted successfully (ID: " + id + ")");
    }

    public void markInProgress(int id) {
        changeStatus(id, "in-progress");
    }

    public void markDone(int id) {
        changeStatus(id, "done");
    }

    // Método auxiliar interno para evitar repetir a lógica de mark-in-progress/mark-done
    private void changeStatus(int id, String novoStatus) {
        Task task = findTaskById(id);

        if (task == null) {
            System.out.println("Error: Task with ID " + id + " not found.");
            return;
        }

        task.setStatus(novoStatus);
        task.setUpdatedAt(LocalDateTime.now().toString());

        JsonUtil.saveTasks(tasks, filePath);
        System.out.println("Task marked as " + novoStatus + " (ID: " + id + ")");
    }

    public void listAll() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        for (Task t : tasks) {
            printTask(t);
        }
    }

    // Reutilizado por list done / list todo / list in-progress
    public void listByStatus(String status) {
        List<Task> filtradas = new ArrayList<>();

        for (Task t : tasks) {
            if (t.getStatus().equals(status)) {
                filtradas.add(t);
            }
        }

        if (filtradas.isEmpty()) {
            System.out.println("No tasks found with status: " + status);
            return;
        }

        for (Task t : filtradas) {
            printTask(t);
        }
    }

    // Formata a impressão de uma task no console
    private void printTask(Task t) {
        System.out.println("[" + t.getId() + "] (" + t.getStatus() + ") " + t.getDescription()
                + " | created: " + t.getCreatedAt() + " | updated: " + t.getUpdatedAt());
    }
}