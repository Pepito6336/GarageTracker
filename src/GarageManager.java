import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GarageManager {
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

        for (Car car : foundClient.getOwnedCars()) {
            if (car.getLicensePlate().equals(plate)) {
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

    public void removeClient(Scanner scanner) {
        System.out.println("\n--- ИЗТРИВАНЕ НА КЛИЕНТ ---");
        if (garageClients.isEmpty()) {
            System.out.println("Гаражът е празен. Няма клиенти за изтриване.");
            return;
        }

        String searchEgn = InputValidator.readEgn(scanner);

        Client targetClient = null;
        for (Client c : garageClients) {
            if (c.getEgn().equals(searchEgn)) {
                targetClient = c;
                break;
            }
        }

        if (targetClient == null) {
            System.out.println("Няма клиент с такова ЕГН.");
            return;
        }

        boolean confirm = InputValidator.readConfirmation(scanner, "Изтриването на клиента ще изтрие и колите, които той притежава от системата. Искате ли да продължите?");

        if (confirm) {
            garageClients.remove(targetClient);
            System.out.println("Клиентът и всичките му автомобили бяха изтрити.");
        } else {
            System.out.println("Операцията е прекратена.");
        }
    }

    public void removeCar(Scanner scanner) {
        System.out.println("\n--- ИЗТРИВАНЕ НА АВТОМОБИЛ ---");

        String plate = InputValidator.readLicensePlate(scanner, "Въведете Рег. номер на колата за изтриване: ");

        Car targetCar = null;
        Client targetClient = null;

        for (Client c : garageClients) {
            for (Car car : c.getOwnedCars()) {
                if (car.getLicensePlate().equals(plate)) {
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

        boolean confirm = InputValidator.readConfirmation(scanner, "Искате ли да изтриете колата?");

        if (confirm) {
            targetClient.getOwnedCars().remove(targetCar);
            System.out.println("Колата беше изтрита успешно.");
        } else {
            System.out.println("Операцията е прекратена.");
        }
    }

    public void printClientDetails(Scanner scanner) {
        System.out.println("\n--- ПЪЛНА ИНФОРМАЦИЯ ЗА КЛИЕНТ ---");
        if (garageClients.isEmpty()) {
            System.out.println("Гаражът е празен. Няма клиенти в системата.");
            return;
        }

        String searchEgn = InputValidator.readEgn(scanner);

        for (Client c : garageClients) {
            if (c.getEgn().equals(searchEgn)) {
                // Обектът сам си знае как да си изпринтира данните и колите
                c.printClientInfo();
                return;
            }
        }

        System.out.println("Няма клиент с такова ЕГН.");
    }

    public void printCarDetails(Scanner scanner) {
        System.out.println("\n--- ПЪЛНА ИНФОРМАЦИЯ ЗА КОЛА ---");
        if (garageClients.isEmpty()) {
            System.out.println("Гаражът е празен.");
            return;
        }

        String plate = InputValidator.readLicensePlate(scanner, "Въведете Рег. номер за търсене: ");

        for (Client c : garageClients) {
            for (Car car : c.getOwnedCars()) {
                if (car.getLicensePlate().equals(plate)) {
                    // Колата изкарва пълната си диагностика, като ѝ подаваме собственика
                    car.printDiagnostics(c);
                    return;
                }
            }
        }

        System.out.println("Кола с такъв регистрационен номер не е открита.");
    }

    public void printAllClients() {
        System.out.println("\n--- СПИСЪК С КЛИЕНТИ ---");
        if (garageClients.isEmpty()) {
            System.out.println("Няма регистрирани клиенти.");
            return;
        }

        List<Client> sortedClients = new ArrayList<>(garageClients);

        for (int i = 0; i < sortedClients.size() - 1; i++) {
            for (int j = 0; j < sortedClients.size() - i - 1; j++) {

                Client client1 = sortedClients.get(j);
                Client client2 = sortedClients.get(j + 1);

                if (client1.getFullName().compareToIgnoreCase(client2.getFullName()) > 0) {
                    sortedClients.set(j, client2);
                    sortedClients.set(j + 1, client1);
                }
            }
        }

        for (Client c : sortedClients) {
            c.printClientBasics();
        }
    }

    public void printAllCars() {
        System.out.println("\n--- СПИСЪК С АВТОМОБИЛИ ---");

        List<Car> allCars = new ArrayList<>();
        for (Client c : garageClients) {
            allCars.addAll(c.getOwnedCars());
        }

        if (allCars.isEmpty()) {
            System.out.println("Няма регистрирани автомобили в системата.");
            return;
        }

        for (int i = 0; i < allCars.size() - 1; i++) {
            for (int j = 0; j < allCars.size() - i - 1; j++) {

                Car car1 = allCars.get(j);
                Car car2 = allCars.get(j + 1);
                boolean shouldSwap = false;

                int brandCheck = car1.getBrand().compareToIgnoreCase(car2.getBrand());
                if (brandCheck > 0) {
                    shouldSwap = true;
                }
                else if (brandCheck == 0) {
                    int modelCheck = car1.getModel().compareToIgnoreCase(car2.getModel());
                    if (modelCheck > 0) {
                        shouldSwap = true;
                    }
                    else if (modelCheck == 0) {
                        if (car1.getYear() > car2.getYear()) {
                            shouldSwap = true;
                        }
                    }
                }

                if (shouldSwap) {
                    allCars.set(j, car2);
                    allCars.set(j + 1, car1);
                }
            }
        }

        for (Car car : allCars) {
            car.printCarInfo();
        }
    }

    public void installModification(Scanner scanner) {
        System.out.println("\n--- ИНСТАЛИРАНЕ НА МОДИФИКАЦИЯ ---");
        String plate = InputValidator.readLicensePlate(scanner, "На коя кола ще инсталирате? (Рег. номер): ");

        Car targetCar = null;
        Client targetClient = null;

        for (Client c : garageClients) {
            for (Car car : c.getOwnedCars()) {
                if (car.getLicensePlate().equals(plate)) {
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
            for (Car car : c.getOwnedCars()) {
                if (car.getLicensePlate().equals(plate)) {
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