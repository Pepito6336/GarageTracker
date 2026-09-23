import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Палим главния двигател на гаража
        GarageManager manager = new GarageManager();

        System.out.println("=== GarageTracker v2.0 ===");

        while (true) {
            System.out.println("\n--- ГЛАВНО МЕНЮ ---");
            System.out.println("1. Добавяне на нов клиент");
            System.out.println("2. Добавяне на автомобил");
            System.out.println("3. Инсталиране на модификация (Турбо/Чип)");
            System.out.println("4. Премахване на модификация (Турбо/Чип)");
            System.out.println("5. Изход");

            String choice = InputValidator.readStringLimited(scanner, "Изберете опция: ", 1);

            switch (choice) {
                case "1":
                    try {
                        manager.registerClient(scanner);
                    } catch (DuplicateException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "2":
                    try {
                        manager.registerCar(scanner);
                    } catch (DuplicateException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "3":
                    manager.installModification(scanner);
                    break;

                case "4":
                    manager.removeModification(scanner);
                    break;

                case "5":
                    System.out.println("Изход . . .");
                    scanner.close();
                    return;

                default:
                    System.out.println("Невалидна опция.");
            }
        }
    }
}