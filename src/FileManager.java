import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
}