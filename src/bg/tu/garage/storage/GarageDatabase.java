package bg.tu.garage.storage;

import bg.tu.garage.entities.Car;
import bg.tu.garage.entities.Client;
import bg.tu.garage.exceptions.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class GarageDatabase {
    private final List<Client> clients;

    public GarageDatabase(List<Client> clients) {
        this.clients = (clients != null) ? clients : new ArrayList<>();
    }

    public List<Client> getClients() {
        return clients;
    }

    public Car findCarByPlate(String plate) throws EntityNotFoundException {
        for (Client c : clients) {
            for (Car car : c.getOwnedCars()) {
                if (car.getLicensePlate().equals(plate)) {
                    return car;
                }
            }
        }
        throw new EntityNotFoundException("Автомобил с регистрационен номер " + plate + " не е открит.");
    }

    public Client findClientByEgn(String egn) throws EntityNotFoundException {
        for (Client c : clients) {
            if (c.getEgn().equals(egn)) {
                return c;
            }
        }
        throw new EntityNotFoundException("Клиент с ЕГН " + egn + " не е открит.");
    }

    public void clearAll() {
        clients.clear();
    }
}