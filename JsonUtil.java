import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil{

    public static String taskToJson(Task task) {
        String json=
                    "{\"id\":" + task.getId()
                    +",\"description\":\"" + task.getDescription()+"\""
                    +",\"status\":\"" + task.getStatus() + "\""
                    +",\"createdAt\":\"" + task.getCreatedAt() + "\""
                    +",\"updatedAt\":\"" + task.getUpdatedAt()+ "\""+"}";
        return json;
    }

    public static String listToJson(List<Task> tasks) {
        String json = "[";
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            if (i == 0) {
                json = json + taskToJson(t);
            } else {
                json = json + "," + taskToJson(t);
            }
        }
        json = json + "]";
        return json;
    }

    public static void saveTasks(List<Task> tasks, String filePath) {
        Path path = Paths.get(filePath);
        String json = listToJson(tasks);
        try {
            Files.writeString(path, json);
        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    private static int extractId(String json) {
        String chave = "\"id\":";
        int inicio = json.indexOf(chave) + chave.length();
        int fim = json.indexOf(",", inicio);
        String valorTexto = json.substring(inicio, fim);
        return Integer.parseInt(valorTexto);
    }

    private static String extractValue(String json, String campo) {
        String chave = "\"" + campo + "\":\"";
        int inicio = json.indexOf(chave) + chave.length();
        int fim = json.indexOf("\"", inicio);
        return json.substring(inicio, fim);
    }

    public static Task jsonToTask(String json) {
        int id = extractId(json);
        String description = extractValue(json, "description");
        String status = extractValue(json, "status");
        String createdAt = extractValue(json, "createdAt");
        String updatedAt = extractValue(json, "updatedAt");
        return new Task(id, description, status, createdAt, updatedAt);
    }

    public static List<Task> jsonToList(String json) {
        List<Task> tasks = new ArrayList<>();
        int pos = 0;
        while (pos < json.length()) {
            int inicio = json.indexOf("{", pos);
            if (inicio == -1) break;
            int fim = json.indexOf("}", inicio);
            String taskJson = json.substring(inicio, fim + 1);
            tasks.add(jsonToTask(taskJson));
            pos = fim + 1;
        }
        return tasks;
    }

    public static List<Task> loadTasks(String filePath) {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }
        try {
            String json = Files.readString(path);
            return jsonToList(json);
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}





