import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Console Calculator ---");
            System.out.println("1. Basic Arithmetic Operations");
            System.out.println("2. Scientific Calculations");
            System.out.println("3. Unit Conversions");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    basicArithmetic(scanner);
                    break;
                case 2:
                    scientificCalculations(scanner);
                    break;
                case 3:
                    unitConversions(scanner);
                    break;
                case 4:
                    System.out.println("Exiting Calculator. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);

        scanner.close();
    }

    public static void basicArithmetic(Scanner scanner) {
        System.out.print("Enter first number: ");
        double num1 = scanner.nextDouble();
        System.out.print("Enter second number: ");
        double num2 = scanner.nextDouble();

        System.out.println("Choose operation: +, -, *, /");
        char operation = scanner.next().charAt(0);

        try {
            double result;
            switch (operation) {
                case '+':
                    result = num1 + num2;
                    System.out.println("Result: " + result);
                    break;
                case '-':
                    result = num1 - num2;
                    System.out.println("Result: " + result);
                    break;
                case '*':
                    result = num1 * num2;
                    System.out.println("Result: " + result);
                    break;
                case '/':
                    if (num2 != 0) {
                        result = num1 / num2;
                        System.out.println("Result: " + result);
                    } else {
                        System.out.println("Error: Division by zero is not allowed.");
                    }
                    break;
                default:
                    System.out.println("Invalid operation.");
            }
        } catch (Exception e) {
            System.out.println("Error in calculation: " + e.getMessage());
        }
    }


    public static void scientificCalculations(Scanner scanner) {
        System.out.println("Choose operation: ");
        System.out.println("1. Square Root");
        System.out.println("2. Exponentiation (Power)");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.print("Enter number for square root: ");
                double num = scanner.nextDouble();
                if (num >= 0) {
                    System.out.println("Square Root: " + Math.sqrt(num));
                } else {
                    System.out.println("Error: Square root of negative number is undefined.");
                }
                break;
            case 2:
                System.out.print("Enter base: ");
                double base = scanner.nextDouble();
                System.out.print("Enter exponent: ");
                double exponent = scanner.nextDouble();
                System.out.println("Result: " + Math.pow(base, exponent));
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

 
    public static void unitConversions(Scanner scanner) {
        System.out.println("Choose conversion type: ");
        System.out.println("1. Temperature (Celsius to Fahrenheit)");
        System.out.println("2. Currency (USD to INR)");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.print("Enter temperature in Celsius: ");
                double celsius = scanner.nextDouble();
                double fahrenheit = (celsius * 9/5) + 32;
                System.out.println("Temperature in Fahrenheit: " + fahrenheit);
                break;
            case 2:
                System.out.print("Enter amount in USD: ");
                double usd = scanner.nextDouble();
                double conversionRate = 74.5; 
                double inr = usd * conversionRate;
                System.out.println("Amount in INR: " + inr);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
}
