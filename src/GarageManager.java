import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GarageManager {
    private final List<Client> garageClients;

    public GarageManager() {
        this.garageClients = new ArrayList<>();
    }

    public List<Client> getGarageClients() {
        return garageClients;
    }

    private Car findCarByPlate(String plate) {
        for (Client c : garageClients) {
            for (Car car : c.getOwnedCars()) {
                if (car.getLicensePlate().equals(plate)) {
                    return car;
                }
            }
        }
        return null;
    }

    private Client findClientByEgn(String egn) {
        for (Client c : garageClients) {
            if (c.getEgn().equals(egn)) {
                return c;
            }
        }
        return null;
    }

    private boolean shouldSwapCars(Car car1, Car car2) {
        int brandCheck = car1.getBrand().compareToIgnoreCase(car2.getBrand());
        if (brandCheck > 0) return true;

        if (brandCheck == 0) {
            int modelCheck = car1.getModel().compareToIgnoreCase(car2.getModel());
            if (modelCheck > 0) return true;

            if (modelCheck == 0) {
                return car1.getYear() > car2.getYear();
            }
        }
        return false;
    }

    public void registerClient(Scanner scanner) throws DuplicateException {
        System.out.println("\n--- ДОБАВЯНЕ НА КЛИЕНТ ---");
        String name = InputValidator.readName(scanner, "Въведете Име и Фамилия: ");
        String inputEgn = InputValidator.readEgn(scanner);

        if (findClientByEgn(inputEgn) != null) {
            throw new DuplicateException("Клиент с това ЕГН вече съществува.");
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
        Client foundClient = findClientByEgn(searchEgn);

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
        Client targetClient = findClientByEgn(searchEgn);

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

        Car targetCar = findCarByPlate(plate);

        if (targetCar == null) {
            System.out.println("Кола с такъв регистрационен номер не е открита.");
            return;
        }

        boolean confirm = InputValidator.readConfirmation(scanner, "Искате ли да изтриете колата?");
        if (confirm) {
            targetCar.getOwner().getOwnedCars().remove(targetCar);
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
        Client targetClient = findClientByEgn(searchEgn);

        if (targetClient != null) {
            targetClient.printClientInfo();
        } else {
            System.out.println("Няма клиент с такова ЕГН.");
        }
    }

    public void printCarDetails(Scanner scanner) {
        System.out.println("\n--- ПЪЛНА ИНФОРМАЦИЯ ЗА КОЛА ---");
        if (garageClients.isEmpty()) {
            System.out.println("Гаражът е празен.");
            return;
        }

        String plate = InputValidator.readLicensePlate(scanner, "Въведете Рег. номер за търсене: ");
        Car targetCar = findCarByPlate(plate);

        if (targetCar != null) {
            targetCar.printDiagnostics(targetCar.getOwner());
        } else {
            System.out.println("Кола с такъв регистрационен номер не е открита.");
        }
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

                // Тук магията вече е изнесена в помощника!
                if (shouldSwapCars(car1, car2)) {
                    allCars.set(j, car2);
                    allCars.set(j + 1, car1);
                }
            }
        }

        for (Car car : allCars) {
            car.printCarInfo();
        }
    }

    public void search(Scanner scanner) {
        System.out.println("\n--- ТЪРСАЧКА ---");
        System.out.println("1. Търсене в Клиенти");
        System.out.println("2. Търсене в Автомобили");
        String choice = InputValidator.readStringLimited(scanner, "Изберете (1 или 2): ", 1);

        System.out.print("Въведете какво търсите: ");
        String term = scanner.nextLine().trim().toLowerCase();

        if (term.isEmpty()) {
            System.out.println("Не въведохте нищо за търсене.");
            return;
        }

        boolean foundAnything = false;

        if (choice.equals("1")) {
            System.out.println("\n--- РЕЗУЛТАТИ: КЛИЕНТИ ---");
            for (Client c : garageClients) {
                String name = c.getFullName().toLowerCase();
                String egn = c.getEgn().toLowerCase();
                String phone = c.getPhoneNumber().toLowerCase();

                if (name.contains(term) || egn.contains(term) || phone.contains(term)) {
                    c.printClientBasics();
                    foundAnything = true;
                }
            }
        } else if (choice.equals("2")) {
            System.out.println("\n--- РЕЗУЛТАТИ: АВТОМОБИЛИ ---");
            for (Client c : garageClients) {
                for (Car car : c.getOwnedCars()) {
                    String brand = car.getBrand().toLowerCase();
                    String model = car.getModel().toLowerCase();
                    String year = String.valueOf(car.getYear());
                    String plate = car.getLicensePlate().toLowerCase();

                    String trans = (car.getTransmission() == Transmission.AUTOMATIC) ? "автоматик" : "ръчна";

                    boolean hasTurbo = term.equals("турбо") && car.hasUpgrade("Турбо");
                    boolean hasChip = term.equals("чип") && car.hasUpgrade("Чип");

                    if (brand.contains(term) || model.contains(term) || year.contains(term) ||
                            plate.contains(term) || trans.contains(term) || hasTurbo || hasChip) {
                        car.printCarInfo();
                        foundAnything = true;
                    }
                }
            }
        } else {
            System.out.println("Невалиден избор.");
            return;
        }

        if (!foundAnything) {
            System.out.println("Няма намерени резултати за: " + term);
        }
    }
    public void installModification(Scanner scanner) {
        System.out.println("\n--- ИНСТАЛИРАНЕ НА МОДИФИКАЦИЯ ---");
        String plate = InputValidator.readLicensePlate(scanner, "На коя кола ще инсталирате? (Рег. номер): ");

        Car targetCar = findCarByPlate(plate);

        if (targetCar == null) {
            System.out.println("Кола с такъв регистрационен номер не е открита.");
            return;
        }

        targetCar.printDiagnostics(targetCar.getOwner());

        System.out.println("\nКакво искате да инсталирате?");
        System.out.println("1. Турбо");
        System.out.println("2. Чип");
        String choice = InputValidator.readStringLimited(scanner, "Изберете (1 или 2): ", 1);

        try {
            if (choice.equals("1")) {
                targetCar.applyUpgrade(new Turbo(false));
            } else if (choice.equals("2")) {
                targetCar.applyUpgrade(new Chip(false));
            } else {
                System.out.println("Невалиден избор. Операцията е прекратена.");
            }
        } catch (UpgradeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeModification(Scanner scanner) {
        System.out.println("\n--- ПРЕМАХВАНЕ НА МОДИФИКАЦИЯ ---");
        String plate = InputValidator.readLicensePlate(scanner, "От коя кола ще премахвате? (Рег. номер): ");

        Car targetCar = findCarByPlate(plate);

        if (targetCar == null) {
            System.out.println("Кола с такъв регистрационен номер не е открита.");
            return;
        }

        targetCar.printDiagnostics(targetCar.getOwner());

        System.out.println("\nКакво искате да премахнете?");
        System.out.println("1. Турбо");
        System.out.println("2. Чип");
        String choice = InputValidator.readStringLimited(scanner, "Изберете (1 или 2): ", 1);

        try {
            if (choice.equals("1")) {
                targetCar.removeUpgrade("Турбо");
            } else if (choice.equals("2")) {
                targetCar.removeUpgrade("Чип");
            } else {
                System.out.println("Невалиден избор. Операцията е прекратена.");
            }
        } catch (UpgradeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void clearAllData(Scanner scanner) {
        System.out.println("\n--- НУЛИРАНЕ НА СИСТЕМАТА ---");
        if (garageClients.isEmpty()) {
            System.out.println("Гаражът вече е празен.");
            return;
        }

        boolean confirm = InputValidator.readConfirmation(scanner, "ВНИМАНИЕ: Това ще изтрие ВСИЧКИ клиенти и автомобили! Сигурни ли сте?");

        if (confirm) {
            garageClients.clear();
            System.out.println("Гаражът е напълно изчистен в паметта.");

            FileManager.saveData(garageClients);
        } else {
            System.out.println("Операцията е прекратена.");
        }
    }
}