package com.example.assignment1_calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    Calculator calculator = new Calculator();
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnAdd, btnSubtract, btnMultiply, btnDevide, btnEquals, btnDecimal, btnClear, btnDelete, btnPosNeg;
    TextView calcOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnAdd = findViewById(R.id.btnAdd);
        btnSubtract = findViewById(R.id.btnSubtract);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnDevide = findViewById(R.id.btnDivide);
        btnEquals = findViewById(R.id.btnEquals);
        btnDecimal = findViewById(R.id.btnDecimal);
        btnClear = findViewById(R.id.btnClear);
        calcOutput = findViewById(R.id.calcOuput);
        btnDelete = findViewById(R.id.btnDelete);
        btnPosNeg = findViewById(R.id.btnPosNeg);

        btn0.setOnClickListener(numListener);
        btn1.setOnClickListener(numListener);
        btn2.setOnClickListener(numListener);
        btn3.setOnClickListener(numListener);
        btn4.setOnClickListener(numListener);
        btn5.setOnClickListener(numListener);
        btn6.setOnClickListener(numListener);
        btn7.setOnClickListener(numListener);
        btn8.setOnClickListener(numListener);
        btn9.setOnClickListener(numListener);
        btnAdd.setOnClickListener(operListener);
        btnSubtract.setOnClickListener(operListener);
        btnDevide.setOnClickListener(operListener);
        btnMultiply.setOnClickListener(operListener);
        btnEquals.setOnClickListener(operListener);
        btnPosNeg.setOnClickListener(posNegListener);
        btnDecimal.setOnClickListener(decimalListener);
        btnClear.setOnClickListener(clearListener);
        btnDelete.setOnClickListener(deleteListener);

    } //end onCreate

// ---------------------------------------------------- Output Display Method ---------------------------------------------------- //
    //accessor, return type, name, params
    private void outputDisplay(String valueOne, String operation, String valueTwo) {
        calcOutput.setText((valueOne + " " + operation + " " + valueTwo).trim());
    }

// ---------------------------------------------------- Number Listener ---------------------------------------------------- //
    View.OnClickListener numListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button thisButton = findViewById(v.getId());
            String btnClicked = thisButton.getText().toString(); // store in variable since we will use it many times

            // if operation is empty, button clicked should be stored in valueOne
            if (calculator.operation.isEmpty()) {
                calculator.valueOne += btnClicked;
            } else { // else, store in valueTwo
                calculator.valueTwo += btnClicked;
            }
            // call display method
            outputDisplay(calculator.valueOne, calculator.operation, calculator.valueTwo);
        } //end onClick
    };

// ---------------------------------------------------- Operation Listener ---------------------------------------------------- //
    View.OnClickListener operListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button thisButton = findViewById(v.getId());
            String result;
            String btnClicked = thisButton.getText().toString(); // store in variable since we will use it many times

            // if "=" is pressed and both valueTwo and operation exist
            if (btnClicked.equals("=") && !calculator.valueTwo.isEmpty() && !calculator.operation.isEmpty() && !calculator.valueTwo.equals(".")) {
                // store calculation in result and clear all the values
                result = calculator.calculate();
                calculator.valueOne = "";
                calculator.operation = "";
                calculator.valueTwo = "";
                // if result is not an error, store result into valueOne
                if (!result.equals(calculator.generalError) && !result.equals(calculator.divideError)) {
                    calculator.valueOne = result;
                } else { // else, display the error message
                    calcOutput.setText(result);
                    return; //to avoid hitting display at the bottom
                }
            } else {
                // if any operation other than "=" is clicked, and valueTwo exists
                if (!btnClicked.equals("=") && !calculator.valueTwo.isEmpty() && !calculator.valueTwo.equals(".")) {
                    // store calculation in result and clear all the values
                    result = calculator.calculate();
                    calculator.valueOne = "";
                    calculator.operation = "";
                    calculator.valueTwo = "";
                    // if result is not an error, store result into valueOne
                    if (!result.equals(calculator.generalError) && !result.equals(calculator.divideError)) {
                        calculator.valueOne = result;
                        // assign the operator clicked to operation
                        calculator.operation = btnClicked;
                    } else { // else, display error message
                        calcOutput.setText(result);
                        return; //to avoid hitting display at the bottom
                    }
                // else if btnClicked is not "=" and valueOne exists, and operation does not exist
                } else if (!btnClicked.equals("=") && !calculator.valueOne.isEmpty() && calculator.operation.isEmpty()) {
                    calculator.operation = btnClicked;
                }
            }
            outputDisplay(calculator.valueOne, calculator.operation, calculator.valueTwo);

        } //end onClick
    };

// ---------------------------------------------------- Positive/Negative Listener ---------------------------------------------------- //
    View.OnClickListener posNegListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // if operation exists
            if (!calculator.operation.isEmpty()) {
                // if valueTwo does not contain "-", add it to beginning of number
                if (!calculator.valueTwo.contains("-")) {
                    calculator.valueTwo = ("-" + calculator.valueTwo);
                } else { // else, take "-" away from beginning of number
                    calculator.valueTwo = calculator.valueTwo.substring(1);
                }
            } else {
                // if valueOne does not contain "-", add it to beginning of number
                if (!calculator.valueOne.contains("-")) {
                    calculator.valueOne = ("-" + calculator.valueOne);
                } else { // else, take "-" away from beginning of number
                    calculator.valueOne = calculator.valueOne.substring(1);
                }
            }
            outputDisplay(calculator.valueOne, calculator.operation, calculator.valueTwo);
        }
    };

// ---------------------------------------------------- Decimal Listener ---------------------------------------------------- //
    View.OnClickListener decimalListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (!calculator.operation.isEmpty()) {
                if (!calculator.valueTwo.contains(".")) {
                    calculator.valueTwo += ".";
                }
            } else {
                if (!calculator.valueOne.contains(".")) {
                    calculator.valueOne += ".";
                }
            }
            outputDisplay(calculator.valueOne, calculator.operation, calculator.valueTwo);
        }
    };

// ---------------------------------------------------- Clear Listener ---------------------------------------------------- //
    View.OnClickListener clearListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // set text to "", and clear all values
            calcOutput.setText("");
            calculator.valueOne = "";
            calculator.operation = "";
            calculator.valueTwo = "";
        }
    };

// ---------------------------------------------------- Delete Listener ---------------------------------------------------- //
    View.OnClickListener deleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // if valueTwo exists, delete the last number from valueTwo
            if (!calculator.valueTwo.isEmpty()) {
                calculator.valueTwo = calculator.valueTwo.substring(0, (calculator.valueTwo.length()-1));
            // else if operation is not empty, clear operation
            } else if (!calculator.operation.isEmpty()) {
                calculator.operation = "";
            // else if, valueTwo exists, delete the last number from valueOne
            } else if (!calculator.valueOne.isEmpty()) {
                calculator.valueOne = calculator.valueOne.substring(0, (calculator.valueOne.length()-1));
            }
            outputDisplay(calculator.valueOne, calculator.operation, calculator.valueTwo);
        }
    };

} //end MainActivity class
