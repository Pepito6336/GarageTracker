package bg.tu.garage.entities;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private String fullName;
    private final String egn;
    private String phoneNumber;
    private String eMail;
    private String address;
    private List<Car> ownedCars;

    public Client(String fullName, String egn, String phoneNumber, String eMail, String address) {
        this.fullName = fullName;
        this.egn = egn;
        this.phoneNumber = phoneNumber;
        this.eMail = eMail;
        this.address = address;
        this.ownedCars = new ArrayList<>();
    }

    public String getFullName() { return fullName; }
    public String getEgn() { return egn; }
    public String getPhoneNumber() { return phoneNumber; }
    public String geteMail() { return eMail; }
    public String getAddress() { return address; }
    public List<Car> getOwnedCars() { return ownedCars; }

    public void addCar(Car car) {
        car.setOwner(this);
        ownedCars.add(car);
    }

    public void printClientBasics(){
        System.out.println(fullName + ", егн: " + egn + ", притежавани коли: " + ownedCars.size() + "бр.");
    }

    public void printClientInfo(){
        System.out.println("------ Инфо ------");
        System.out.println("Клиент: " + fullName);
        System.out.println("Телефон: " + phoneNumber);
        System.out.println("И-Мейл: " + eMail);
        System.out.println("Адрес: " + address);
        System.out.println("Притежавани коли: ");

        if (ownedCars.isEmpty()) {
            System.out.println("  -> Няма регистрирани автомобили.");
        } else {
            for (Car car : ownedCars){
                car.printCarInfoWithoutOwner();
            }
        }
    }
}