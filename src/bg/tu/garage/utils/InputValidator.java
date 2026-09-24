package bg.tu.garage.utils;

import bg.tu.garage.entities.Transmission;

import java.util.Scanner;

public class InputValidator {

    public static String readName(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.length() <= 50 && input.matches("^[\\p{L}\\-]+ [\\p{L}\\-]+$")) {
                return input;
            }
            System.out.println("Невалидни имена.");
        }
    }

    public static String readEgn(Scanner scanner) {
        while (true) {
            System.out.print("Въведете ЕГН: ");
            String input = scanner.nextLine().trim();
            if (input.matches("^\\d{10}$")) {
                return input;
            }
            System.out.println("Невалидно ЕГН.");
        }
    }

    public static String readPhone(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.length() <= 15 && input.matches("^\\+?\\d+$")) {
                return input;
            }
            System.out.println("Невалиден телефон.");
        }
    }

    public static String readEmail(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.length() <= 30 && !input.contains(" ") && input.contains("@")) {
                return input;
            }
            System.out.println("Невалиден имейл.");
        }
    }

    public static String readAddress(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty() && input.length() <= 100) {
                return input;
            }
            System.out.println("Невалиден адрес.");
        }
    }

    public static String readStringLimited(Scanner scanner, String prompt, int maxLength) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty() && input.length() <= maxLength) {
                return input;
            }
            System.out.println("Невалидно въвеждане.");
        }
    }

    public static int readCarYear(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.matches("^\\d{4}$")) {
                try {
                    int year = Integer.parseInt(input);
                    if (year >= 1960 && year <= 2026) {
                        return year;
                    }
                } catch (NumberFormatException ignored) {}
            }
            System.out.println("Невалидна година.");
        }
    }

    public static String readLicensePlate(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);

            String input = scanner.nextLine().replaceAll("\\s+", "").toUpperCase();

            if (!input.isEmpty() && input.length() <= 8) {
                return input;
            }

            System.out.println("Невалиден номер.");
        }
    }

    public static double readEngineVolume(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.matches("^\\d+(\\.\\d)?$")) {
                try {
                    return Double.parseDouble(input);
                } catch (NumberFormatException ignored) {}
            }
            System.out.println("Невалиден обем.");
        }
    }

    public static Transmission readTransmission(Scanner scanner, String prompt) {
        while (true) {
            System.out.println(prompt);
            System.out.println("1. Ръчна");
            System.out.println("2. Автоматична");
            System.out.print("Изберете (1 или 2): ");
            String input = scanner.nextLine().trim();
            if (input.equals("1")) return Transmission.MANUAL;
            if (input.equals("2")) return Transmission.AUTOMATIC;
            else System.out.println("Невалиден избор.");
        }
    }

    public static int readHorsePower(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int hp = Integer.parseInt(input);
                if (hp > 0) return hp;
            } catch (NumberFormatException ignored) {}
            System.out.println("Невалидни конски сили.");
        }
    }

    public static boolean readConfirmation(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt + " (Y/N): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("Y")) {
                return true;
            } else if (input.equals("N")) {
                return false;
            }

            System.out.println("Невалиден отговор. Моля, въведете точно Y или N.");
        }
    }
}