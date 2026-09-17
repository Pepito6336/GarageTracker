public class Main {
    public static void main(String[] args) {
        Client myClient = new Client("Teodor Ivanov", "0893873917", "t_ivanov@mail.com", "Varna");

        System.out.println("\n--- СЪЗДАВАНЕ НА КОЛА ---");
        Car myCar = new Car("VW", "Polo", 2005, "B2362HB", 1.4, Transmission.MANUAL, 55);
        myClient.addCar(myCar);

        System.out.println("\n--- ТЕСТ 1: Слагане на Турбо ---");
        myCar.applyUpgrade(new Turbo(false));

        System.out.println("\n--- ТЕСТ 2: Опит за второ Турбо ---");
        myCar.applyUpgrade(new Turbo(false));

        System.out.println("\n--- ТЕСТ 3: Слагане на Чип (върху Турбо) ---");
        myCar.applyUpgrade(new Chip(false));

        System.out.println("\n--- ТЕСТ 4: Махане на Турбо (при наличен чип) ---");
        myCar.removeUpgrade("Турбо");

        System.out.println("\n--- ТЕСТ 5: Опит за махане на вече махнато Турбо ---");
        myCar.removeUpgrade("Турбо");

        System.out.println("\n--- ТЕСТ 6: Втора кола с предварително монтирано Турбо ---");
        Car golf = new Car("VW", "Golf", 2010, "B1234AB", 1.9, Transmission.MANUAL, 150);
        myClient.addCar(golf);
        golf.applyUpgrade(new Turbo(true)); // Не трябва да пипа конете!

        System.out.println("\n--- ДИАГНОСТИКА ПОЛО ---");
        myCar.printDiagnostics(myClient);

        System.out.println("\n--- ДИАГНОСТИКА ГОЛФ ---");
        golf.printDiagnostics(myClient);

        myClient.printClientInfo();
    }
} 