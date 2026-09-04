//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Car myCar = new Car("VW", "Polo", 2005);

        System.out.println("В сервиза влезе: " + myCar.brand + " " + myCar.model + " от " + myCar.year + "година.");
    }
}