public class Turbo extends CarMod {

    public Turbo(boolean isPreInstalled) {
        super("Турбо", isPreInstalled);
    }

    @Override
    protected void printInstalled() {
        System.out.println("Турбото е добавено.");
    }

    @Override
    protected void printRemoved() {
        System.out.println( "Турбото е премахнато.");
    }

    @Override
    public void install(Car car) {
        if (!isPreInstalled) {
            if (car.hasUpgrade("Чип")) {
                double hp = car.getHorsePower();
                hp = hp * 100 / 110;
                hp = hp * 140 / 100;
                hp = hp * 125 / 100;
                car.setHorsePower(hp);
            }
            printInstalled();
        }
    }

    @Override
    public void remove(Car car) {
        if (car.hasUpgrade("Чип")) {
            double hp = car.getHorsePower();
            hp = hp * 100 / 125;
            hp = hp * 100 / 140;
            hp = hp * 110 / 100;
            car.setHorsePower(hp);
        }
        else {
            car.setHorsePower(car.getHorsePower() * 100 / 140);
        }
        printRemoved();
    }
} 