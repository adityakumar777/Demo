package com.fusion.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    Button   equals,AC,Del;
    TextView textv1;
    EditText edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edittext = findViewById(R.id.editText);
        equals=findViewById(R.id.equals);
        textv1= findViewById(R.id.textv1);
        AC= findViewById(R.id.AC);
        Del=findViewById(R.id.del);


        equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double ans = solveEquation(edittext.getText().toString());
                textv1.setText(ans+"");

            }
        });


        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Not needed in this case
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Not needed in this case
            }

            @Override
            public void afterTextChanged(Editable editable) {

                int sign = 0;
                String input = editable.toString();

                char ch[] = input.toCharArray();

                for (char c :
                        ch) {
                    if (c == '+' || c == '-' || c == '*' || c == '/' ){
                        sign++;
                    }
                }

                int decimalCount = countDecimals(input);

                int decimalLimit = sign+1;

                if (decimalCount > decimalLimit) {
                    // Remove excess decimals
                    String truncatedInput = input.substring(0,input.length()-1);
                    edittext.setText(truncatedInput);
                    edittext.setSelection(truncatedInput.length());
                }
            }

            private int countDecimals(String input) {
                int decimalCount = 0;
                for (int i = 0; i < input.length(); i++) {
                    if (input.charAt(i) == '.') {
                        decimalCount++;
                    }
                }
                return decimalCount;
            }
        });



    }


    public void setChar(View view){

        if (view.getId()==R.id.one)
            edittext.append("1");
        else if (view.getId()==R.id.two)
            edittext.append("2");
        else if (view.getId()==R.id.three)
            edittext.append("3");
        else if (view.getId()==R.id.four)
            edittext.append("4");
        else if (view.getId()==R.id.five)
            edittext.append("5");
        else if (view.getId()==R.id.six)
            edittext.append("6");
        else if (view.getId()==R.id.seven)
            edittext.append("7");
        else if (view.getId()==R.id.eight)
            edittext.append("8");
        else if (view.getId()==R.id.nine)
            edittext.append("9");
        else if (view.getId()==R.id.szero)
            edittext.append("0");

        else if (view.getId()==R.id.decimal)
            edittext.append(".");

        else if (view.getId()==R.id.sum) {
            edittext.append("+");
        }
        else if (view.getId()==R.id.div) {
            edittext.append("/");
        }
        else if (view.getId()==R.id.sub) {
            edittext.append("-");
        }
        else if (view.getId()==R.id.mul) {
            edittext.append("*");
        }

        else if (view.getId()==R.id.AC)
            edittext.setText("");

        else if (view.getId()==R.id.del){
            String s = edittext.getText().toString();
            edittext.setText( s.substring(0,s.length()-1));
        }



    }
    private double solveEquation(String expression) {
        Stack<Double> operandStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (Character.isDigit(ch)) {
                StringBuilder operand = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    operand.append(expression.charAt(i));
                    i++;
                }
                i--;
                operandStack.push(Double.parseDouble(operand.toString()));
            } else if (isOperator(ch)) {
                while (!operatorStack.isEmpty() && hasPrecedence(operatorStack.peek(), ch)) {
                    performOperation(operandStack, operatorStack.pop());
                }
                operatorStack.push(ch);
            } else if (ch == '(') {
                operatorStack.push(ch);
            } else if (ch == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    performOperation(operandStack, operatorStack.pop());
                }
                operatorStack.pop();
            }
        }

        while (!operatorStack.isEmpty()) {
            performOperation(operandStack, operatorStack.pop());
        }

        return operandStack.pop();
    }

    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    private static boolean hasPrecedence(char op1, char op2) {
        return (op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-');
    }

    private static void performOperation(Stack<Double> operandStack, char operator) {
        double operand2 = operandStack.pop();
        double operand1 = operandStack.pop();
        double result = 0;

        switch (operator) {
            case '+':
                result = operand1 + operand2;
                break;
            case '-':
                result = operand1 - operand2;
                break;
            case '*':
                result = operand1 * operand2;
                break;
            case '/':
                if (operand2 != 0) {
                    result = operand1 / operand2;
                } else {
                    throw new ArithmeticException("Division by zero");
                }
                break;
        }

        operandStack.push(result);
    }

}