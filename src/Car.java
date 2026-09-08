public class Car {
    String brand;
    String model;
    int year;
    double engineVolume;
    boolean isManual;
    int horsePower;

    public Car(String brand, String model, int year, double engineVolume, boolean isManual, int horsePower){
        this.brand = brand;
        this. model = model;
        this.year = year;
        this.engineVolume = engineVolume;
        this.isManual = isManual;
        this.horsePower = horsePower;
    }

    public void printDiagnostics(){
        System.out.println("В сервиза влезе: " + brand + " " + model + " от " + year + "г. с" + engineVolume + " двигател(" + horsePower + "к.с.) и ръчна скоростна кутия: " + isManual + ".");
    }
}
