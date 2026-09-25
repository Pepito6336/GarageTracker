package bg.tu.garage.managers.upgrade;

import bg.tu.garage.entities.Car;
import bg.tu.garage.exceptions.EntityNotFoundException;
import bg.tu.garage.exceptions.UpgradeException;
import bg.tu.garage.storage.GarageDatabase;
import bg.tu.garage.upgrades.types.Chip;
import bg.tu.garage.upgrades.types.Turbo;
import bg.tu.garage.utils.InputValidator;

import java.util.Scanner;

public class UpgradeManager implements IUpgradeManager {
    private final GarageDatabase db;

    public UpgradeManager(GarageDatabase db) {
        this.db = db;
    }

    @Override
    public void installModification(Scanner scanner) {
        System.out.println("\n--- ИНСТАЛИРАНЕ НА МОДИФИКАЦИЯ ---\n");
        if (!db.hasAnyCars()) {
            System.out.println("Няма регистрирани автомобили, на които да сложите модификация.");
            return;
        }
        System.out.println("(Въведете 0 за Главно Меню)\n");

        String plate = InputValidator.readLicensePlate(scanner, "На коя кола ще инсталирате? (Рег. номер): ");

        try {
            Car targetCar = db.findCarByPlate(plate);
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
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeModification(Scanner scanner) {
        System.out.println("\n--- ПРЕМАХВАНЕ НА МОДИФИКАЦИЯ ---\n");
        if (!db.hasAnyCars()) {
            System.out.println("Няма регистрирани автомобили в системата.");
            return;
        }
        System.out.println("(Въведете 0 за Главно Меню)\n");

        String plate = InputValidator.readLicensePlate(scanner, "От коя кола ще премахвате? (Рег. номер): ");

        try {
            Car targetCar = db.findCarByPlate(plate);
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
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}