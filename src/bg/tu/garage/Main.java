package bg.tu.garage;

import bg.tu.garage.exceptions.DuplicateException;
import bg.tu.garage.managers.car.CarManager;
import bg.tu.garage.managers.car.ICarManager;
import bg.tu.garage.managers.client.ClientManager;
import bg.tu.garage.managers.client.IClientManager;
import bg.tu.garage.managers.report.IReportManager;
import bg.tu.garage.managers.report.ReportManager;
import bg.tu.garage.managers.search.ISearchEngine;
import bg.tu.garage.managers.search.SearchEngine;
import bg.tu.garage.managers.system.ISystemManager;
import bg.tu.garage.managers.system.SystemManager;
import bg.tu.garage.managers.upgrade.IUpgradeManager;
import bg.tu.garage.managers.upgrade.UpgradeManager;
import bg.tu.garage.storage.FileManager;
import bg.tu.garage.storage.GarageDatabase;
import bg.tu.garage.utils.InputValidator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        GarageDatabase db = new GarageDatabase(FileManager.loadData());

        IClientManager clientManager = new ClientManager(db);
        ICarManager carManager = new CarManager(db);
        IReportManager reportManager = new ReportManager(db);
        ISearchEngine searchEngine = new SearchEngine(db);
        IUpgradeManager upgradeManager = new UpgradeManager(db);
        ISystemManager systemManager = new SystemManager(db); // Новият шеф на системата

        System.out.println("=== GarageTracker ===");
        if (!db.getClients().isEmpty()) {
            System.out.println("Успешно зареден гараж с " + db.getClients().size() + " клиенти.");
        }

        while (true) {
            System.out.println("\n--- ГЛАВНО МЕНЮ ---");
            System.out.println("1. Добавяне на нов клиент");
            System.out.println("2. Добавяне на автомобил");
            System.out.println("3. Премахване на клиент");
            System.out.println("4. Премахване на автомобил");
            System.out.println("5. Пълна информация за клиент");
            System.out.println("6. Пълна информация за кола");
            System.out.println("7. Списък на всички клиенти");
            System.out.println("8. Списък на всички автомобили");
            System.out.println("9. Търсене на Клиенти и Автомобили");
            System.out.println("10. Инсталиране на модификация (Турбо/Чип)");
            System.out.println("11. Премахване на модификация (Турбо/Чип)");
            System.out.println("12. Запазване на промените във файл (Quick Save)");
            System.out.println("13. ИЗТРИВАНЕ НА ВСИЧКО (Нулиране)");
            System.out.println("14. Изход");

            String choice = InputValidator.readStringLimited(scanner, "Изберете опция: ", 2);

            switch (choice) {
                case "1":
                    try { clientManager.registerClient(scanner); }
                    catch (DuplicateException e) { System.out.println(e.getMessage()); }
                    break;
                case "2":
                    try { carManager.registerCar(scanner); }
                    catch (DuplicateException e) { System.out.println(e.getMessage()); }
                    break;
                case "3":
                    clientManager.removeClient(scanner);
                    break;
                case "4":
                    carManager.removeCar(scanner);
                    break;
                case "5":
                    clientManager.printClientDetails(scanner);
                    break;
                case "6":
                    carManager.printCarDetails(scanner);
                    break;
                case "7":
                    reportManager.printAllClients();
                    break;
                case "8":
                    reportManager.printAllCars();
                    break;
                case "9":
                    searchEngine.search(scanner);
                    break;
                case "10":
                    upgradeManager.installModification(scanner);
                    break;
                case "11":
                    upgradeManager.removeModification(scanner);
                    break;
                case "12":
                    systemManager.saveChanges();
                    break;
                case "13":
                    systemManager.clearAllData(scanner);
                    break;
                case "14":
                    systemManager.exitProgram(scanner);
                    scanner.close();
                    return;
                default:
                    System.out.println("Невалидна опция.");
            }
        }
    }
}