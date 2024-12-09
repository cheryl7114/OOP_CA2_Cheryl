package org.example;

import java.util.Scanner;
import java.util.Stack;

/**
 *  Name: Cheryl Kong
 *  Class Group: SD2B
 */
public class Question9 {

    /*
        Reads in an equation from the user
     */
    public static void main(String[] args) {
        String equation;
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter equation");
        equation = in.nextLine().trim();
        System.out.println("Answer to " + equation + " is " + evaluateExpression(equation));
    }

    public static int evaluateExpression(String equation) {
        Stack<Integer> numbers = new Stack<>();
        Stack<Character> characters = new Stack<>();

        for (int i=0;i<equation.length();i++) {
            char ch = equation.charAt(i);

            // skip spaces
            if (Character.isWhitespace(ch)) {
                continue;
            }

            if (Character.isDigit(ch)) {
                int num = 0;
                while (i<equation.length() && Character.isDigit(equation.charAt(i))) {
                    // ensure that full number is read (e.g. 450)
                    // move the previous digit to the left and add the next digit to the end of the equation (4*10 -> 40 + 5)
                    num = num * 10 + Character.getNumericValue(equation.charAt(i));
                    i++;
                }
                numbers.push(num);
                i--;
            } else if (ch == '(') {
                characters.push(ch);
            } else if (ch == ')') {
                while (characters.peek() != '(') {
                    evaluateTop(numbers, characters);
                }
                // pop '('
                characters.pop();
            } else if ("+-*/".indexOf(ch) != -1) { // if the character is an operator
                while (!characters.isEmpty() && precedence(characters.peek()) >= precedence(ch)) {
                    evaluateTop(numbers, characters);
                }
                characters.push(ch);
            } else {
                throw new IllegalArgumentException("Invalid character in expression: " + ch);
            }
        }

        // evaluate remaining operators in the stack
        while (!characters.isEmpty()) {
            evaluateTop(numbers, characters);
        }

        // final result is in the numbers stack
        return numbers.pop();
    }

    // determine order of execution
    public static int precedence(char operator) {
        return switch (operator) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> -1; // for -1 or invalid
        };
    }

    // evaluate top of stack
    public static void evaluateTop(Stack<Integer> numbers, Stack<Character> operators) {
        int b = numbers.pop();
        int a = numbers.pop();
        char op = operators.pop();

        int result = switch (op) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> {
                if (b == 0) {
                    throw new ArithmeticException("Division by zero not allowed!");
                }
                yield a / b;
            }
            default -> throw new IllegalArgumentException("Invalid operator: " + op);
        };

        // push result back to numbers stack for further execution (if any)
        numbers.push(result);
    }
}
