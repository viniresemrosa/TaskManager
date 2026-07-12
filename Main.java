public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager("tasks.json");

        // args[0] é sempre o comando (add, update, delete, list, etc.)
        if (args.length == 0) {
            System.out.println("Error: no command provided.");
            System.out.println("Usage: task-cli <command> [arguments]");
            return;
        }

        String command = args[0];

        try {
            switch (command) {
                case "add":
                    // task-cli add "Buy groceries"
                    if (args.length < 2) {
                        System.out.println("Error: missing task description.");
                        return;
                    }
                    manager.addTask(args[1]);
                    break;

                case "update":
                    // task-cli update 1 "Buy groceries and cook dinner"
                    if (args.length < 3) {
                        System.out.println("Error: usage -> update <id> <new description>");
                        return;
                    }
                    int updateId = Integer.parseInt(args[1]);
                    manager.updateTask(updateId, args[2]);
                    break;

                case "delete":
                    // task-cli delete 1
                    if (args.length < 2) {
                        System.out.println("Error: usage -> delete <id>");
                        return;
                    }
                    int deleteId = Integer.parseInt(args[1]);
                    manager.deleteTask(deleteId);
                    break;

                case "mark-in-progress":
                    // task-cli mark-in-progress 1
                    if (args.length < 2) {
                        System.out.println("Error: usage -> mark-in-progress <id>");
                        return;
                    }
                    int progressId = Integer.parseInt(args[1]);
                    manager.markInProgress(progressId);
                    break;

                case "mark-done":
                    // task-cli mark-done 1
                    if (args.length < 2) {
                        System.out.println("Error: usage -> mark-done <id>");
                        return;
                    }
                    int doneId = Integer.parseInt(args[1]);
                    manager.markDone(doneId);
                    break;

                case "list":
                    // task-cli list
                    // task-cli list done | todo | in-progress
                    if (args.length == 1) {
                        manager.listAll();
                    } else {
                        manager.listByStatus(args[1]);
                    }
                    break;

                default:
                    System.out.println("Error: unknown command '" + command + "'");
                    System.out.println("Available commands: add, update, delete, mark-in-progress, mark-done, list");
            }
        } catch (NumberFormatException e) {
            // Acontece se o usuário digitar um id que não é número (ex: "abc")
            System.out.println("Error: task ID must be a number.");
        }
    }
}