package av4.calculator;

public class Calculator {
    double result;

    public Calculator() {
        System.out.println("Calculator is on");
        System.out.println("result = " + result);
    }

    public void execute(char op, double b){
        StringBuilder sb = new StringBuilder();
        sb.append("result ");

        Strategy strategy = null;
        switch(op){
            case '+' -> {
                strategy = new Addition();
                sb.append("+ ");
            }
            case '-' -> {
                strategy = new Subtraction();
                sb.append("- ");
            }
            case '*' -> {
                strategy = new Multiplication();
                sb.append("* ");
            }
            case '/' -> {
                strategy = new Division();
                sb.append("/ ");
            }
        }

        if(strategy == null)
            throw new IllegalArgumentException(String.format("%c is an ilegal argument", op));

        sb.append(b);
        sb.append(" = ");

        result = strategy.execute(result, b);


        sb.append(result);

        System.out.println(sb.toString());
    }

    public double getResult() {
        return result;
    }
}
