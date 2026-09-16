//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Client myClient = new Client("Teodor Ivanov", "0893873917", "t_ivanov@mail.com", "Varna");
        Car myCar = new Car("VW", "Polo", 2005, "B2362HB", 1.4, Transmission.MANUAL, 55, false, false);
        myClient.addCar(myCar);
        myClient.printClientInfo();
        myCar.printDiagnostics(myClient);
    }
}