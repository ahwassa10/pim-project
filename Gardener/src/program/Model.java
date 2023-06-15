package program;

public class Model {
    public long calculate(long operand1,
                          long operand2,
                          String operator) {
        switch (operator) {
        case "+":
            return operand1 + operand2;
        case "-":
            return operand1 - operand2;
        case "*":
            return operand1 * operand2;
        case "/":
            return operand1 / operand2;
        }
        
        throw new IllegalArgumentException("Unknown operator");
    }
}
