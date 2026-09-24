package bg.tu.garage.managers.client;

import bg.tu.garage.exceptions.DuplicateException;

import java.util.Scanner;
public interface IClientManager {
    void registerClient(Scanner scanner) throws DuplicateException;
    void removeClient(Scanner scanner);
    void printClientDetails(Scanner scanner);
}