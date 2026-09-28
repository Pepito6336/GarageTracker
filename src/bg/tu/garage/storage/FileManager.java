package bg.tu.garage.storage;

import bg.tu.garage.entities.Car;
import bg.tu.garage.entities.Client;
import bg.tu.garage.entities.Transmission;
import bg.tu.garage.exceptions.UpgradeException;
import bg.tu.garage.upgrades.types.Chip;
import bg.tu.garage.upgrades.IUpgrade;
import bg.tu.garage.upgrades.types.Turbo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String FILE_PATH = "garage_data.txt";

    public static void saveData(List<Client> clients) {
        System.out.println("Запазване на данните във файл...");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Client c : clients) {
                writer.write("CLIENT;" + c.getFullName() + ";" + c.getEgn() + ";" +
                        c.getPhoneNumber() + ";" + c.geteMail() + ";" + c.getAddress());
                writer.newLine();

                for (Car car : c.getOwnedCars()) {
                    writer.write("CAR;" + car.getBrand() + ";" + car.getModel() + ";" +
                            car.getYear() + ";" + car.getLicensePlate() + ";" +
                            car.getEngineVolume() + ";" + car.getTransmission().name() + ";" +
                            car.getHorsePower());
                    writer.newLine();

                    for (IUpgrade mod : car.getInstalledUpgrades()) {
                        writer.write("MOD;" + mod.getModName());
                        writer.newLine();
                    }
                }
            }
            System.out.println("Данните са запазени успешно!");
        } catch (IOException e) {
            System.out.println("Грешка при запазване на данните: " + e.getMessage());
        }
    }

    public static void clearData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
        } catch (IOException e) {
            System.out.println("Грешка при изчистване на файла: " + e.getMessage());
        }
    }

    public static List<Client> loadData() {
        List<Client> clients = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return clients;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            Client currentClient = null;
            Car currentCar = null;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");

                if (parts[0].equals("CLIENT")) {
                    currentClient = new Client(parts[1], parts[2], parts[3], parts[4], parts[5]);
                    clients.add(currentClient);
                }
                else if (parts[0].equals("CAR") && currentClient != null) {
                    currentCar = new Car(parts[1], parts[2], Integer.parseInt(parts[3]), parts[4],
                            Double.parseDouble(parts[5]), Transmission.valueOf(parts[6]), Double.parseDouble(parts[7]));
                    currentClient.addCar(currentCar);
                }
                else if (parts[0].equals("MOD") && currentCar != null) {
                    try {
                        if (parts[1].equals("Турбо")) currentCar.applyUpgrade(new Turbo(true));
                        else if (parts[1].equals("Чип")) currentCar.applyUpgrade(new Chip(true));
                    } catch (UpgradeException ignored) {}
                }
            }
        } catch (IOException e) {
            System.out.println("Грешка при зареждане на данните: " + e.getMessage());
        }

        return clients;
    }
}