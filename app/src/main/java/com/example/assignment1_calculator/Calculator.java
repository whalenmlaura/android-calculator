package com.example.assignment1_calculator;

class Calculator {

    String valueOne = "";
    String valueTwo = "";
    String operation = "";
    String divideError = "CANNOT DIVIDE BY 0";
    String generalError = "ERROR";

    String calculate() {
        if (!valueTwo.isEmpty()) {
            switch (operation) {
                case "+":
                    return String.valueOf(Float.parseFloat(valueOne) + Float.parseFloat(valueTwo));
                case "-":
                    return String.valueOf(Float.parseFloat(valueOne) - Float.parseFloat(valueTwo));
                case "*":
                    return String.valueOf(Float.parseFloat(valueOne) * Float.parseFloat(valueTwo));
                case "/":
                    if (Float.parseFloat(valueTwo) == 0) {
                        return divideError;
                    } else {
                        return String.valueOf(Float.parseFloat(valueOne) / Float.parseFloat(valueTwo));
                    }
                default:
                    return generalError;
            } //end switch
        } else {
            return valueOne + " " + operation + " ";
        }
    } //end calculate method

} //end Calculator Class
