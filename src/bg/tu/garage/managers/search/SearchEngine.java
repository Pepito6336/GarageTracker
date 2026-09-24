package bg.tu.garage.managers.search;

import bg.tu.garage.entities.Car;
import bg.tu.garage.entities.Client;
import bg.tu.garage.entities.Transmission;
import bg.tu.garage.storage.GarageDatabase;
import bg.tu.garage.utils.InputValidator;

import java.util.Scanner;

public class SearchEngine implements ISearchEngine {
    private final GarageDatabase db;

    public SearchEngine(GarageDatabase db) {
        this.db = db;
    }

    @Override
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
            for (Client c : db.getClients()) {
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
            for (Client c : db.getClients()) {
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
}