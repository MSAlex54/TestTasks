package my.tasks.calculator;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Stack;
import java.util.regex.Pattern;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        // TODO: Implement the logic here
        if (statement == null || statement.isEmpty()) return null;
        Pattern calcPat = Pattern.compile("(^.*[^+()\\-\\.*/0-9].*$)");
        if (!calcPat.matcher(statement).matches()) { //checking for not allowed symbols

            //checking for bracket balance
            Stack<Character> stack = new Stack<>();
            for (int i = 0; i < statement.length(); i++) {
                char symbol = statement.charAt(i);
                if (symbol == '(') {
                    stack.push(symbol);
                } else if (symbol == ')') {
                    if (stack.empty() || stack.pop() != '(')
                        return null;
                }
            }
            if (!stack.empty()) return null;

            //checking for other mistakes in statement
            Pattern operPat = Pattern.compile("[+*/.-]");
            for (int i = 1; i < statement.length() ; i++) {
                String curChar = String.valueOf(statement.charAt(i));
                String privChar = String.valueOf(statement.charAt(i-1));
                if (operPat.matcher(curChar).matches()&&operPat.matcher(privChar).matches()){
                    return null;
                }
            }

        } else {
            return null;
        }

        //evaluation
        LinkedList<Double> numbers = new LinkedList<Double>();
        LinkedList<Character> operators = new LinkedList<Character>();
        for (int i = 0; i < statement.length(); i++) {
            char c = statement.charAt(i);
            if (c==' ')
                continue;
            if (c == '(')
                operators.add('(');
            else if (c == ')') {
                while (operators.getLast() != '(')
                    processOperator(numbers,operators.removeLast());
                operators.removeLast();
            } else if (isOperator(c)) {
                while (!operators.isEmpty() && priority(operators.getLast()) >= priority(c))
                    processOperator(numbers, operators.removeLast());
                operators.add(c);
            } else {
                String operand = "";
                while (i < statement.length() && (Character.isDigit(statement.charAt(i))||statement.charAt(i)=='.'))
                    operand += statement.charAt(i++);
                --i;
                numbers.add(Double.parseDouble(operand));
            }
        }
        while (!operators.isEmpty())
            processOperator(numbers, operators.removeLast());
        Double result = numbers.get(0);
        if (result.isInfinite()) return null;
        DecimalFormat f = new DecimalFormat("##.####");
        return f.format(result).replaceAll(",",".");
    }

    static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
    static int priority(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }
    static void processOperator(LinkedList<Double> numbers, char operator) {
        double r = numbers.removeLast();
        double l = numbers.removeLast();
        switch (operator) {
            case '+':
                numbers.add(l + r);
                break;
            case '-':
                numbers.add(l - r);
                break;
            case '*':
                numbers.add(l * r);
                break;
            case '/':
                numbers.add(l / r);
                break;
        }
    }

}
