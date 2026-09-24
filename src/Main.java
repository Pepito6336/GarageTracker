import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GarageManager manager = new GarageManager();

        System.out.println("=== GarageTracker v2.0 ===");

        while (true) {
            System.out.println("\n--- ГЛАВНО МЕНЮ ---");
            System.out.println("1. Добавяне на нов клиент");
            System.out.println("2. Добавяне на автомобил");
            System.out.println("3. Премахване на клиент");
            System.out.println("4. Премахване на автомобил");
            System.out.println("5. Списък на клиенти");
            System.out.println("6. Списък на автомобили");
            System.out.println("7. Инсталиране на модификация (Турбо/Чип)");
            System.out.println("8. Премахване на модификация (Турбо/Чип)");
            System.out.println("9. Изход");

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
                    manager.removeClient(scanner);
                    break;

                case "4":
                    manager.removeCar(scanner);
                    break;

                case "5":
                    manager.printAllClients();
                    break;

                case "6":
                    manager.printAllCars();
                    break;

                case "7":
                    manager.installModification(scanner);
                    break;

                case "8":
                    manager.removeModification(scanner);
                    break;

                case "9":
                    System.out.println("Изход . . .");
                    scanner.close();
                    return;

                default:
                    System.out.println("Невалидна опция.");
            }
        }
    }
}