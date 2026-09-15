//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Car myCar = new Car("VW", "Polo", 2005, "B2362HB", 1.4, true, 55, false, false);
        myCar.printDiagnostics();
        myCar.addTurbo();
        myCar.printDiagnostics();
        myCar.addTurbo();
        myCar.printDiagnostics();
        myCar.removeTurbo();
        myCar.printDiagnostics();
        myCar.removeTurbo();
        myCar.printDiagnostics();
        myCar.addChip();
        myCar.printDiagnostics();
        myCar.addChip();
        myCar.printDiagnostics();
        myCar.addTurbo();
        myCar.printDiagnostics();
        myCar.removeTurbo();
        myCar.printDiagnostics();
        myCar.addTurbo();
        myCar.removeChip();
        myCar.printDiagnostics();
        myCar.removeChip();
        myCar.printDiagnostics();
        myCar.addChip();
        myCar.removeTurbo();
        myCar.printDiagnostics();
        myCar.removeChip();
        myCar.printDiagnostics();
    }
}