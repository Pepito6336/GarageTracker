package bg.tu.garage.managers.car;

import bg.tu.garage.exceptions.DuplicateException;

import java.util.Scanner;
public interface ICarManager {
    void registerCar(Scanner scanner) throws DuplicateException;
    void removeCar(Scanner scanner);
    void printCarDetails(Scanner scanner);
}