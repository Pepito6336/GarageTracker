package bg.tu.garage.managers.upgrade;

import java.util.Scanner;
public interface IUpgradeManager {
    void installModification(Scanner scanner);
    void removeModification(Scanner scanner);
}