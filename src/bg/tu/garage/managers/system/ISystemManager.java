package bg.tu.garage.managers.system;

import java.util.Scanner;

public interface ISystemManager {
    void saveChanges();
    void clearAllData(Scanner scanner);
    void exitProgram(Scanner scanner);
}