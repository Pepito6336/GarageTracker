import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Client> garageClients = new ArrayList<>();

        System.out.println("=== Добре дошли в GarageTracker ===");

        while (true) {
            System.out.println("\n--- ГЛАВНО МЕНЮ ---");
            System.out.println("1. Добави НОВ КЛИЕНТ");
            System.out.println("2. Добави КОЛА");
            System.out.println("3. ИЗХОД");

            String choice = InputValidator.readStringLimited(scanner, "Изберете опция: ", 1);

            switch (choice) {
                case "1":
                    try {
                        registerClient(scanner, garageClients);
                    } catch (DuplicateException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "2":
                    try {
                        registerCar(scanner, garageClients);
                    } catch (DuplicateException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "3":
                    System.out.println("Гасим двигателя. Довиждане!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Невалидна опция.");
            }
        }
    }

    private static void registerClient(Scanner scanner, List<Client> garageClients) throws DuplicateException {
        System.out.println("\n--- ДОБАВЯНЕ НА КЛИЕНТ ---");
        String name = InputValidator.readName(scanner, "Въведете Име и Фамилия: ");
        String inputEgn = InputValidator.readEgn(scanner);

        for (Client c : garageClients) {
            if (c.getEgn().equals(inputEgn)) {
                throw new DuplicateException("Клиент с това ЕГН вече съществува.");
            }
        }

        String phone = InputValidator.readPhone(scanner, "Въведете телефон: ");
        String email = InputValidator.readEmail(scanner, "Въведете имейл: ");
        String address = InputValidator.readAddress(scanner, "Въведете адрес: ");

        Client newClient = new Client(name, inputEgn, phone, email, address);
        garageClients.add(newClient);
        System.out.println("Клиентът е добавен.");
    }

    private static void registerCar(Scanner scanner, List<Client> garageClients) throws DuplicateException {
        System.out.println("\n--- ДОБАВЯНЕ НА КОЛА ---");
        if (garageClients.isEmpty()) {
            System.out.println("Гаражът е празен. Първо добавете клиент.");
            return;
        }

        String searchEgn = InputValidator.readEgn(scanner);

        Client foundClient = null;
        for (Client c : garageClients) {
            if (c.getEgn().equals(searchEgn)) {
                foundClient = c;
                break;
            }
        }

        if (foundClient == null) {
            System.out.println("Няма клиент с такова ЕГН.");
            return;
        }

        System.out.println("Намерен клиент: " + foundClient.getFullName());
        String plate = InputValidator.readLicensePlate(scanner, "Въведете Регистрационен номер: ");

        for (Car car : foundClient.ownedCars) {
            if (car.licensePlate.equals(plate)) {
                throw new DuplicateException("Тази кола вече е регистрирана към клиента.");
            }
        }

        String brand = InputValidator.readStringLimited(scanner, "Въведете Марка: ", 15);
        String model = InputValidator.readStringLimited(scanner, "Въведете Модел: ", 15);
        int year = InputValidator.readCarYear(scanner, "Въведете Година: ");
        double engineVolume = InputValidator.readEngineVolume(scanner, "Въведете Обем на двигателя: ");
        Transmission trans = InputValidator.readTransmission(scanner, "Изберете скоростна кутия:");
        int horsePower = InputValidator.readHorsePower(scanner, "Въведете Конски сили: ");

        Car newCar = new Car(brand, model, year, plate, engineVolume, trans, horsePower);
        foundClient.addCar(newCar);
        System.out.println("Колата е добавена.");
    }
}