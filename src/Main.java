import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GarageManager manager = new GarageManager();

        System.out.println("=== GarageTracker ===");

        while (true) {
            System.out.println("\n--- ГЛАВНО МЕНЮ ---");
            System.out.println("1. Добавяне на нов клиент");
            System.out.println("2. Добавяне на автомобил");
            System.out.println("3. Премахване на клиент");
            System.out.println("4. Премахване на автомобил");
            System.out.println("5. Пълна информация за клиент");
            System.out.println("6. Пълна информация за кола");
            System.out.println("7. Списък на всички клиенти");
            System.out.println("8. Списък на всички автомобили");
            System.out.println("9. Търсене на Клиенти и Автомобили)");
            System.out.println("10. Инсталиране на модификация (Турбо/Чип)");
            System.out.println("11. Премахване на модификация (Турбо/Чип)");
            System.out.println("12. Изход");

            String choice = InputValidator.readStringLimited(scanner, "Изберете опция: ", 2);

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
                    manager.printClientDetails(scanner);
                    break;
                case "6":
                    manager.printCarDetails(scanner);
                    break;
                case "7":
                    manager.printAllClients();
                    break;
                case "8":
                    manager.printAllCars();
                    break;
                case "9":
                    manager.search(scanner);
                    break;
                case "10":
                    manager.installModification(scanner);
                    break;
                case "11":
                    manager.removeModification(scanner);
                    break;
                case "12":
                    System.out.println("Изход . . .");
                    scanner.close();
                    return;
                default:
                    System.out.println("Невалидна опция.");
            }
        }
    }
}