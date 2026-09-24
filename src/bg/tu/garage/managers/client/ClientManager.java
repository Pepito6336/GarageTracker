package bg.tu.garage.managers.client;

import bg.tu.garage.entities.Client;
import bg.tu.garage.exceptions.DuplicateException;
import bg.tu.garage.exceptions.EntityNotFoundException;
import bg.tu.garage.storage.GarageDatabase;
import bg.tu.garage.utils.InputValidator;

import java.util.Scanner;

public class ClientManager implements IClientManager {
    private final GarageDatabase db;

    public ClientManager(GarageDatabase db) {
        this.db = db;
    }

    @Override
    public void registerClient(Scanner scanner) throws DuplicateException {
        System.out.println("\n--- ДОБАВЯНЕ НА КЛИЕНТ ---");
        String name = InputValidator.readName(scanner, "Въведете Име и Фамилия: ");
        String inputEgn = InputValidator.readEgn(scanner);

        try {
            db.findClientByEgn(inputEgn);
            throw new DuplicateException("Клиент с това ЕГН вече съществува.");
        } catch (EntityNotFoundException e) {
            String phone = InputValidator.readPhone(scanner, "Въведете телефон: ");
            String email = InputValidator.readEmail(scanner, "Въведете имейл: ");
            String address = InputValidator.readAddress(scanner, "Въведете адрес: ");

            Client newClient = new Client(name, inputEgn, phone, email, address);
            db.getClients().add(newClient);
            System.out.println("Клиентът е добавен.");
        }
    }

    @Override
    public void removeClient(Scanner scanner) {
        System.out.println("\n--- ИЗТРИВАНЕ НА КЛИЕНТ ---");
        if (db.getClients().isEmpty()) {
            System.out.println("Гаражът е празен. Няма клиенти за изтриване.");
            return;
        }

        String searchEgn = InputValidator.readEgn(scanner);
        try {
            Client targetClient = db.findClientByEgn(searchEgn);
            boolean confirm = InputValidator.readConfirmation(scanner, "Изтриването на клиента ще изтрие и колите. Продължете?");

            if (confirm) {
                db.getClients().remove(targetClient);
                System.out.println("Клиентът и всичките му автомобили бяха изтрити.");
            } else {
                System.out.println("Операцията е прекратена.");
            }
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void printClientDetails(Scanner scanner) {
        System.out.println("\n--- ПЪЛНА ИНФОРМАЦИЯ ЗА КЛИЕНТ ---");
        String searchEgn = InputValidator.readEgn(scanner);
        try {
            Client targetClient = db.findClientByEgn(searchEgn);
            targetClient.printClientInfo();
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}