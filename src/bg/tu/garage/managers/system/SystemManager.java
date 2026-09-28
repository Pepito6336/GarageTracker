package bg.tu.garage.managers.system;

import bg.tu.garage.storage.FileManager;
import bg.tu.garage.storage.GarageDatabase;
import bg.tu.garage.utils.InputValidator;

import java.util.Scanner;

public class SystemManager implements ISystemManager {
    private final GarageDatabase db;

    public SystemManager(GarageDatabase db) {
        this.db = db;
    }

    @Override
    public void saveChanges() {
        FileManager.saveData(db.getClients());
    }

    @Override
    public void clearAllData(Scanner scanner) {
        System.out.println("\n--- НУЛИРАНЕ НА СИСТЕМАТА ---");
        if (db.getClients().isEmpty()) {
            System.out.println("Гаражът вече е празен.");
            return;
        }

        boolean confirm = InputValidator.readConfirmation(scanner, "ВНИМАНИЕ: Това ще изтрие ВСИЧКИ клиенти и автомобили! Сигурни ли сте?");

        if (confirm) {
            db.clearAll();
            FileManager.clearData();
            System.out.println("Гаражът е напълно изчистен.");
        } else {
            System.out.println("Операцията е прекратена.");
        }
    }

    @Override
    public void exitProgram(Scanner scanner) {
        boolean saveBeforeExit = InputValidator.readConfirmation(scanner, "Искате ли да запазите промените преди изход?");
        if (saveBeforeExit) {
            FileManager.saveData(db.getClients());
        }
        System.out.println("Изход . . .");
    }
}