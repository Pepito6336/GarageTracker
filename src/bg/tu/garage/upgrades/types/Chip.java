package bg.tu.garage.upgrades.types;

import bg.tu.garage.entities.Car;
import bg.tu.garage.upgrades.CarMod;

public class Chip extends CarMod {

    public Chip(boolean isPreInstalled) {
        super("Чип", isPreInstalled);
    }

    @Override
    protected void printRemoved() {
        System.out.println("Чипът е премахнат.");
    }

    @Override
    protected void printInstalled() {
        System.out.println( "Чипът е добавен.");
    }

    @Override
    public void install(Car car) {
        if (!isPreInstalled) {
            if (car.hasUpgrade("Турбо")) {
                car.setHorsePower(car.getHorsePower() * 125 / 100);
            }
            else {
                car.setHorsePower(car.getHorsePower() * 110 / 100);
            }
            printInstalled();
        }
    }

    @Override
    public void remove(Car car) {
        if (car.hasUpgrade("Турбо")) {
            car.setHorsePower(car.getHorsePower() * 100 / 125);
        }
        else {
            car.setHorsePower(car.getHorsePower() * 100 / 110);
        }
        printRemoved();
    }
}