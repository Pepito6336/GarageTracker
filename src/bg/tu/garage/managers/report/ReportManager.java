package bg.tu.garage.managers.report;

import bg.tu.garage.entities.Car;
import bg.tu.garage.entities.Client;
import bg.tu.garage.storage.GarageDatabase;

import java.util.ArrayList;
import java.util.List;

public class ReportManager implements IReportManager {
    private final GarageDatabase db;

    public ReportManager(GarageDatabase db) {
        this.db = db;
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

    @Override
    public void printAllClients() {
        System.out.println("\n--- СПИСЪК С КЛИЕНТИ ---");
        if (db.getClients().isEmpty()) {
            System.out.println("Няма регистрирани клиенти.");
            return;
        }

        List<Client> sortedClients = new ArrayList<>(db.getClients());

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

    @Override
    public void printAllCars() {
        System.out.println("\n--- СПИСЪК С АВТОМОБИЛИ ---");
        List<Car> allCars = new ArrayList<>();
        for (Client c : db.getClients()) {
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
}