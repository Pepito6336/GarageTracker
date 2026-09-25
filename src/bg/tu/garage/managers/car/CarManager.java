package bg.tu.garage.managers.car;

import bg.tu.garage.entities.Car;
import bg.tu.garage.entities.Client;
import bg.tu.garage.entities.Transmission;
import bg.tu.garage.exceptions.DuplicateException;
import bg.tu.garage.exceptions.EntityNotFoundException;
import bg.tu.garage.storage.GarageDatabase;
import bg.tu.garage.utils.InputValidator;

import java.util.Scanner;

public class CarManager implements ICarManager {
    private final GarageDatabase db;

    public CarManager(GarageDatabase db) {
        this.db = db;
    }

    @Override
    public void registerCar(Scanner scanner) throws DuplicateException {
        System.out.println("\n--- ДОБАВЯНЕ НА КОЛА ---\n");
        if (db.getClients().isEmpty()) {
            System.out.println("Гаражът е празен. Първо добавете клиент.");
            return;
        }
        System.out.println("(Въведете 0 за Главно Меню)\n");

        String searchEgn = InputValidator.readEgn(scanner);
        try {
            Client foundClient = db.findClientByEgn(searchEgn);
            System.out.println("Намерен клиент: " + foundClient.getFullName());

            String plate = InputValidator.readLicensePlate(scanner, "Въведете Регистрационен номер: ");

            try {
                db.findCarByPlate(plate);
                throw new DuplicateException("Тази кола вече е регистрирана в системата.");
            } catch (EntityNotFoundException ex) {
                String brand = InputValidator.readStringLimited(scanner, "Въведете Марка: ", 15);
                String model = InputValidator.readStringLimited(scanner, "Въведете Модел: ", 15);
                int year = InputValidator.readCarYear(scanner, "Въведете Година: ");
                double engineVolume = InputValidator.readEngineVolume(scanner, "Въведете Обем на двигателя: ");
                Transmission trans = InputValidator.readTransmission(scanner, "Изберете скоростна кутия:");
                int horsePower = InputValidator.readHorsePower(scanner, "Въведете Конски сили: ");

                Car newCar = new Car(brand, model, year, plate, engineVolume, trans, horsePower);
                foundClient.addCar(newCar);
            }
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeCar(Scanner scanner) {
        System.out.println("\n--- ИЗТРИВАНЕ НА АВТОМОБИЛ ---\n");
        if (!db.hasAnyCars()) {
            System.out.println("В системата няма нито един регистриран автомобил.");
            return;
        }
        System.out.println("(Въведете 0 за Главно Меню)\n");

        String plate = InputValidator.readLicensePlate(scanner, "Въведете Рег. номер на колата за изтриване: ");

        try {
            Car targetCar = db.findCarByPlate(plate);
            boolean confirm = InputValidator.readConfirmation(scanner, "Искате ли да изтриете колата?");

            if (confirm) {
                targetCar.getOwner().getOwnedCars().remove(targetCar);
                System.out.println("Колата беше изтрита успешно.");
            } else {
                System.out.println("Операцията е прекратена.");
            }
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void printCarDetails(Scanner scanner) {
        System.out.println("\n--- ПЪЛНА ИНФОРМАЦИЯ ЗА КОЛА ---\n");
        if (!db.hasAnyCars()) {
            System.out.println("В системата няма нито един регистриран автомобил.");
            return;
        }
        System.out.println("(Въведете 0 за Главно Меню)\n");

        String plate = InputValidator.readLicensePlate(scanner, "Въведете Рег. номер за търсене: ");
        try {
            Car targetCar = db.findCarByPlate(plate);
            targetCar.printDiagnostics(targetCar.getOwner());
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}