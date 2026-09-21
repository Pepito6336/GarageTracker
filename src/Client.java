import java.util.ArrayList;
import java.util.List;

public class Client {
    String fullName;
    String egn;
    String phoneNumber;
    String eMail;
    String address;
    List<Car> ownedCars;

    public Client(String fullName, String egn, String phoneNumber, String eMail, String address) {
        this.fullName = fullName;
        this.egn = egn;
        this.phoneNumber = phoneNumber;
        this.eMail = eMail;
        this.address = address;
        this.ownedCars = new ArrayList<>();
    }

    public String getFullName() {
        return fullName;
    }

    public String getEgn() {
        return egn;
    }

    public void addCar(Car car){
        ownedCars.add(car);
        System.out.println("Колата на " + fullName + " е добавена в списъка.");
    }

    public void printClientInfo(){
        System.out.println("------ Инфо ------");
        System.out.println("Клиент: " + fullName);
        System.out.println("Tелефон: " + phoneNumber);
        System.out.println("И-Мейл: " + eMail);
        System.out.println("Адрес: " + address);
        System.out.println("Притежавани коли: ");
        for (Car car : ownedCars){
            car.printCarInfo();
        }
        System.out.println("------------------");
    }
} 