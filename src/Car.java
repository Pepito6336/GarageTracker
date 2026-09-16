public class Car {
    String brand;
    String model;
    int year;
    String licensePlate;
    double engineVolume;
    Transmission transmission;
    double horsePower;
    boolean hasTurbo;
    boolean hasChip;

    public Car(String brand, String model, int year, String licensePlate, double engineVolume, Transmission transmission, double horsePower, boolean hasTurbo, boolean hasChip){
        this.brand = brand;
        this. model = model;
        this.year = year;
        this.licensePlate = licensePlate;
        this.engineVolume = engineVolume;
        this.transmission = transmission;
        this.horsePower = horsePower;
        this.hasTurbo = hasTurbo;
        this.hasChip = hasChip;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void addTurbo(){
        if(hasTurbo){
            System.out.println("Колата вече има турбо.");
        }
        else{
            if(hasChip){
                horsePower = horsePower*100/110;
                horsePower = horsePower*140/100;
                horsePower = horsePower*125/100;
            }
            else{
                horsePower = horsePower * 140 / 100;
            }

            hasTurbo = true;
            System.out.println("Турбето е инсталирано.");
        }
    }

    public void removeTurbo(){
        if(!hasTurbo){
            System.out.println("Колата няма турбо за махане.");
        }
        else{
            if(hasChip){
                    horsePower = horsePower*100/125;
                    horsePower = horsePower*100/140;
                    horsePower = horsePower*110/100;
            }
            else{
                horsePower = horsePower*100/140;
            }

            hasTurbo = false;
            System.out.println("Турбето е премахнато.");
        }
    }

    public void addChip(){
        if(hasChip){
            System.out.println("Колата вече е чипосана.");
        }
        else{
            if(hasTurbo){
                horsePower = horsePower*125/100;
            }
            else{
                horsePower = horsePower*110/100;
            }

            hasChip = true;
            System.out.println("Добавен чип.");
        }
    }

    public void removeChip(){
        if(!hasChip){
            System.out.println("Колата не е чипосана.");
        }
        else{
            if(hasTurbo){
                horsePower = horsePower*100/125;
            }
            else{
                horsePower = horsePower*100/110;
            }
            hasChip = false;
            System.out.println("Премахнат чип.");
        }
    }

    public void printCarInfo(){
        System.out.println(brand + ", " + model + ", " + year + "г. рег. номер: " + licensePlate +".");
    }
    public void printDiagnostics(Client client){
        System.out.println("------ Инфо ------");
        System.out.println("Марка: " + brand);
        System.out.println("Модел:  " + model);
        System.out.println("Година:  " + year);
        System.out.println("Рег. номер:  " + licensePlate);
        System.out.println("Двигател:  " + engineVolume);
        System.out.println("Скоростна кутия:  " + transmission);
        System.out.println("Мощност:  " + horsePower + "конски сили");
        System.out.println("Турбо:  " + hasTurbo);
        System.out.println("Чип:  " + hasChip);
        System.out.println("Собственик:  " + client.getFullName());
        System.out.println("------------------");
    }
}
