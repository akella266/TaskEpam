package FirstTask;

import java.util.EmptyStackException;
import java.util.Stack;

public class Calculator {
    public String calculate(String expr) {
        Stack<Character> ops = new Stack<>();
        Stack<Double> polishStr = new Stack<>();
        boolean may_unary = true;
        char currElem;
        try {
            for (int i = 0; i < expr.length(); i++) {
                if (!Operations.isSpace(expr.charAt(i))) {
                    currElem = expr.charAt(i);
                    if (currElem == '(') {
                        ops.push(expr.charAt(i));
                        may_unary = true;
                    } else if (currElem == ')') {
                        while (ops.peek() != '(') {
                            Operations.makeOperation(ops.peek(), polishStr);
                            ops.pop();
                        }
                        ops.pop();
                        may_unary = false;
                    } else if (Operations.isOperation(currElem)) {
                        char curop = expr.charAt(i);
                        if (may_unary && Operations.isUnary(curop)) {
                            if (curop == '-') curop = 'm';
                        }
                        while (!ops.empty() &&
                                (Operations.leftAssoc(currElem) && Operations.priority(ops.peek()) > Operations.priority(currElem)
                                        || !Operations.leftAssoc(currElem) && Operations.priority(ops.peek()) >= Operations.priority(currElem))) {
                            Operations.makeOperation(ops.peek(), polishStr);
                            ops.pop();
                        }
                        ops.push(curop);
                        may_unary = true;
                    } else {
                        StringBuilder operand = new StringBuilder("");
                        while ((i < expr.length()) && (Character.isLetterOrDigit(expr.charAt(i))
                                || expr.charAt(i) == '.' || expr.charAt(i) == ',')) {
                            if (expr.charAt(i) == ',') {
                                operand.append('.');
                                i++;
                            } else
                                operand.append(expr.charAt(i++));
                        }
                        --i;
                        polishStr.push(Double.parseDouble(operand.toString()));
                        may_unary = false;
                    }
                }
            }
            while (!ops.empty()) {
                Operations.makeOperation(ops.peek(), polishStr);
                ops.pop();
            }
        }
        catch(EmptyStackException e){
            return "Not enough brackets";
        }
        catch(NumberFormatException e){
            return "Illegal argument";
        }

        return polishStr.peek().toString();
    }
}


