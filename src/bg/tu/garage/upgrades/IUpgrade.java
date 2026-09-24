package bg.tu.garage.upgrades;

import bg.tu.garage.entities.Car;

public interface IUpgrade {
    void install(Car car);
    void remove(Car car);
    String getModName();
} 