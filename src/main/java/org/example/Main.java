package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String FILE_NAME = "task.txt";

    public static void main(String[] args) {
        List<String> tasks = loadTasks();
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("Меню: \n" +
                    "1. Показать задачи \n" +
                    "2. Добавить задачу \n" +
                    "3. Удалить задачу \n" +
                    "4. Редактировать задачу \n" +
                    "0. Выход");

            String choice = input.nextLine();
            int number;
            try {
                number = Integer.parseInt(choice);
            } catch (NumberFormatException e) {
                System.out.println("Неверный выбор!");
                continue;
            }

            try {
                switch (number) {
                    case 0 -> {
                        System.out.println("Выход...");
                        return;
                    }
                    case 1 -> showTasks(tasks);
                    case 2 -> addTask(tasks, input);
                    case 3 -> deleteTask(tasks, input);
                    case 4 -> editTask(tasks, input);
                    default -> System.out.println("Неверный выбор!");
                }
            } catch (IOException e) {
                System.out.println("Ошибка при работе с файлом: " + e.getMessage());
            }
        }
    }

    private static List<String> loadTasks() {
        List<String> tasks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                tasks.add(line);
            }
        } catch (IOException ignored) {

        }
        return tasks;
    }

    private static void saveTasks(List<String> tasks) throws IOException {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            for (String task : tasks) {
                writer.write(task + System.lineSeparator());
            }
        }
    }

    private static void addTask(List<String> tasks, Scanner input) throws IOException {
        System.out.println("Введите задачу:");
        String task = input.nextLine();
        if (!task.trim().isEmpty()) {
            tasks.add(task);
            saveTasks(tasks);
            System.out.println("Задача добавлена.");
        } else {
            System.out.println("Задача не может быть пустой.");
        }
    }

    private static void showTasks(List<String> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Нет задач.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    private static void deleteTask(List<String> tasks, Scanner input)throws IOException {
        if (tasks.isEmpty()) {
            System.out.println("Нет задач для удаления.");
            return;
        }
        showTasks(tasks);
        System.out.println("Введите номер задачи для удаления:");
        String delChoice = input.nextLine();
        try {
            int num = Integer.parseInt(delChoice);
            if (num > 0 && num <= tasks.size()) {
                tasks.remove(num - 1);
                saveTasks(tasks);
                System.out.println("Задача удалена.");
            } else {
                System.out.println("Неверный номер.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный ввод.");
        }
    }

    private static void editTask(List<String> tasks, Scanner input) throws IOException {
        if (tasks.isEmpty()) {
            System.out.println("Нет задач для редактирования.");
            return;
        }
        showTasks(tasks);
        System.out.println("Введите номер задачи для редактирования:");
        String editChoice = input.nextLine();
        try {
            int num = Integer.parseInt(editChoice);
            if (num > 0 && num <= tasks.size()) {
                System.out.println("Введите новый текст задачи:");
                String newTask = input.nextLine();
                if (!newTask.trim().isEmpty()) {
                    tasks.set(num - 1, newTask);
                    saveTasks(tasks);
                    System.out.println("Задача обновлена.");
                } else {
                    System.out.println("Задача не может быть пустой.");
                }
            } else {
                System.out.println("Неверный номер.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный ввод.");
        }
    }
}

