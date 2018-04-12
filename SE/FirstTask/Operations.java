package FirstTask;

import java.util.Stack;

public class Operations {
    static int priority(char op) { // high -> 3, middle -> 2,low -> 1
        if (op == 'm' || op == '^') return 3;
        else if (op == '*' || op == '/' || op == '%') return 2;
        else if (op == '+' || op == '-') return 1;
        else return -1;
    }

    static boolean isOperation(char op) {
        if (op == '*' || op == '/' || op == '+' || op == '-' || op == '^' || op == '%') return true;
        else return false;
    }

    static boolean isUnary(char op) {
        if (op == '-') return true;
        else return false;
    }

    static boolean leftAssoc(char op) {
        if (op == '^') return true;
        return false;
    }

    static boolean isSpace(char elem) {
        if (elem == ' ') return true;
        else return false;
    }

    static void makeOperation(char ops, Stack<Double> polishStr) {
        double result;
        if (ops == 'm') {
            double oper = polishStr.pop();
            switch (ops) {
                case 'm': {
                    polishStr.push(-oper);
                    break;
                }
            }
        } else {
            double oper1 = polishStr.pop();
            double oper2 = polishStr.pop();
            switch (ops) {
                case '*': {
                    result = oper2 * oper1;
                    polishStr.push(result);
                    break;
                }
                case '/': {
                    if (oper1 == 0) {
                        throw new IllegalArgumentException("Divison by zero");
                    }
                    result = oper2 / oper1;
                    polishStr.push(result);
                    break;
                }
                case '+': {
                    result = oper2 + oper1;
                    polishStr.push(result);
                    break;
                }
                case '-': {
                    result = oper2 - oper1;
                    polishStr.push(result);
                    break;
                }
                case '%': {
                    result = oper1 * oper2 / 100;
                    polishStr.push(result);
                    break;
                }
                case '^': {
                    //oper1 stepen oper2 value
                    result = Math.pow(oper2, oper1);
                    polishStr.push(result);
                    break;
                }
            }
        }
    }
}
