package av4.calculator;

import java.util.Scanner;

public class CalculatorTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Calculator c = new Calculator();

        String line;

        while(true){
            line = scanner.nextLine();

            c.execute(line.charAt(0), Double.parseDouble(line.substring(1)));
            System.out.println("new result = " + c.getResult());

            while(true) {
                System.out.println("Again? (y/n)");
                line = scanner.nextLine();
                if (line.equalsIgnoreCase("n"))
                    break;
                else if(line.equalsIgnoreCase("y"))
                    break;
            }

            if (line.equalsIgnoreCase("n"))
                break;
        }

        System.out.println("final result = " + c.getResult());
    }
}
