package bg.tu.garage.entities;

import bg.tu.garage.exceptions.UpgradeException;
import bg.tu.garage.upgrades.IUpgrade;

import java.util.ArrayList;
import java.util.List;

public class Car {
    private final String brand;
    private final String model;
    private final int year;
    private final String licensePlate;
    private double engineVolume;
    private Transmission transmission;
    private double horsePower;
    private List<IUpgrade> installedUpgrades;
    private Client owner;

    public Car(String brand, String model, int year, String licensePlate, double engineVolume, Transmission transmission, double horsePower) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.licensePlate = licensePlate;
        this.engineVolume = engineVolume;
        this.transmission = transmission;
        this.horsePower = horsePower;
        this.installedUpgrades = new ArrayList<>();
    }

    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public String getLicensePlate() { return licensePlate; }
    public double getEngineVolume() { return engineVolume; }
    public Transmission getTransmission() { return transmission; }
    public double getHorsePower() { return horsePower; }
    public List<IUpgrade> getInstalledUpgrades() { return installedUpgrades; }
    public Client getOwner() { return owner; }

    public void setHorsePower(double horsePower) { this.horsePower = horsePower; }
    public void setOwner(Client owner) { this.owner = owner; }

    public boolean hasUpgrade(String modName) {
        for (IUpgrade upgrade : installedUpgrades) {
            if (upgrade.getModName().equals(modName)) return true;
        }
        return false;
    }

    public void applyUpgrade(IUpgrade upgrade) throws UpgradeException {
        if (hasUpgrade(upgrade.getModName())) {
            throw new UpgradeException("Колата вече разполага с модификация '" + upgrade.getModName() + "'.");
        }
        upgrade.install(this);
        installedUpgrades.add(upgrade);
    }

    public void removeUpgrade(String modName) throws UpgradeException {
        IUpgrade targetUpgrade = null;
        for (IUpgrade upgrade : installedUpgrades) {
            if (upgrade.getModName().equals(modName)) {
                targetUpgrade = upgrade;
                break;
            }
        }
        if (targetUpgrade == null) {
            throw new UpgradeException("Модификация '" + modName + "' не е открита.");
        }
        targetUpgrade.remove(this);
        installedUpgrades.remove(targetUpgrade);
    }

    public void printCarInfo() {
        System.out.println(brand + ", " + model + ", рег. номер: " + licensePlate + ", Собственик:" + getOwner().getFullName());
    }

    public void printDiagnostics(Client client) {
        System.out.println("------ Инфо ------");
        System.out.println("Марка: " + brand);
        System.out.println("Модел: " + model);
        System.out.println("Година: " + year);
        System.out.println("Рег. номер: " + licensePlate);
        System.out.println("Двигател: " + engineVolume);
        System.out.println("Скоростна кутия: " + transmission);
        System.out.println("Мощност: " + horsePower + " конски сили");
        if (installedUpgrades.isEmpty()) {
            System.out.println("Няма модификации.");
        } else {
            System.out.println("Модификации:");
            for (IUpgrade upgrade : installedUpgrades) {
                System.out.println("- " + upgrade.getModName());
            }
        }
        System.out.println("Собственик: " + client.getFullName());
        System.out.println("------------------");
    }
}