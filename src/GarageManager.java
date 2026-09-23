import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GarageManager {
    // Главният склад вече живее тук, а не в Main
    private final List<Client> garageClients;

    public GarageManager() {
        this.garageClients = new ArrayList<>();
    }

    public void registerClient(Scanner scanner) throws DuplicateException {
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

    public void registerCar(Scanner scanner) throws DuplicateException {
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

    public void installModification(Scanner scanner) {
        System.out.println("\n--- ИНСТАЛИРАНЕ НА МОДИФИКАЦИЯ ---");
        String plate = InputValidator.readLicensePlate(scanner, "На коя кола ще инсталирате? (Рег. номер): ");

        Car targetCar = null;
        Client targetClient = null;

        for (Client c : garageClients) {
            for (Car car : c.ownedCars) {
                if (car.licensePlate.equals(plate)) {
                    targetCar = car;
                    targetClient = c;
                    break;
                }
            }
            if (targetCar != null) break;
        }

        if (targetCar == null) {
            System.out.println("Кола с такъв регистрационен номер не е открита.");
            return;
        }

        targetCar.printDiagnostics(targetClient);

        System.out.println("\nКакво искате да инсталирате?");
        System.out.println("1. Турбо");
        System.out.println("2. Чип");
        String choice = InputValidator.readStringLimited(scanner, "Изберете (1 или 2): ", 1);

        if (choice.equals("1")) {
            targetCar.applyUpgrade(new Turbo(false));
        } else if (choice.equals("2")) {
            targetCar.applyUpgrade(new Chip(false));
        } else {
            System.out.println("Невалиден избор. Операцията е прекратена.");
        }
    }

    public void removeModification(Scanner scanner) {
        System.out.println("\n--- ПРЕМАХВАНЕ НА МОДИФИКАЦИЯ ---");
        String plate = InputValidator.readLicensePlate(scanner, "От коя кола ще премахвате? (Рег. номер): ");

        Car targetCar = null;
        Client targetClient = null;

        for (Client c : garageClients) {
            for (Car car : c.ownedCars) {
                if (car.licensePlate.equals(plate)) {
                    targetCar = car;
                    targetClient = c;
                    break;
                }
            }
            if (targetCar != null) break;
        }

        if (targetCar == null) {
            System.out.println("Кола с такъв регистрационен номер не е открита.");
            return;
        }

        targetCar.printDiagnostics(targetClient);

        System.out.println("\nКакво искате да премахнете?");
        System.out.println("1. Турбо");
        System.out.println("2. Чип");
        String choice = InputValidator.readStringLimited(scanner, "Изберете (1 или 2): ", 1);

        if (choice.equals("1")) {
            targetCar.removeUpgrade("Турбо");
        } else if (choice.equals("2")) {
            targetCar.removeUpgrade("Чип");
        } else {
            System.out.println("Невалиден избор. Операцията е прекратена.");
        }
    }
}