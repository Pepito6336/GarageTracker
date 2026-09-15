public class Car {
    String brand;
    String model;
    int year;
    String licensePlate;
    double engineVolume;
    boolean isManual;
    double horsePower;
    boolean hasTurbo;
    boolean hasChip;

    public Car(String brand, String model, int year, String licensePlate, double engineVolume, boolean isManual, double horsePower, boolean hasTurbo, boolean hasChip){
        this.brand = brand;
        this. model = model;
        this.year = year;
        this.licensePlate = licensePlate;
        this.engineVolume = engineVolume;
        this.isManual = isManual;
        this.horsePower = horsePower;
        this.hasTurbo = hasTurbo;
        this.hasChip = hasChip;
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

    public void printDiagnostics(){
        System.out.println("В сервиза влезе: " + brand + " " + model + " от " + year + "г., рег. номер " + licensePlate + " с " + engineVolume + " двигател(" + horsePower + "к.с.) и ръчна скоростна кутия: " + isManual + ", има турбо: " + hasTurbo + " и е чипосан: " + hasChip + ".");
    }
}
