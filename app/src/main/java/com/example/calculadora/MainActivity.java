package com.example.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView display;
    private double currentNumber = 0.0;
    private String pendingOperation = "";
    private boolean useDegrees = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
        Switch switchDegreesRadians = findViewById(R.id.switch_degrees_radians);

        // Configuración del switch para alternar entre grados y radianes
        switchDegreesRadians.setOnCheckedChangeListener((buttonView, isChecked) -> {
            useDegrees = isChecked;
        });

        // Configurar botones numéricos
        configureNumericButtons();

        // Configurar botones de operaciones
        configureOperationButtons();

        // Botón igual
        Button buttonEquals = findViewById(R.id.button_equals);
        buttonEquals.setOnClickListener(v -> performOperation());

        // Botón limpiar
        Button buttonClear = findViewById(R.id.button_clear);
        buttonClear.setOnClickListener(v -> clear());

        // Funciones trigonométricas
        configureTrigonometricButtons();
    }

    private void configureNumericButtons() {
        int[] buttonIds = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9};

        for (int id : buttonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(v -> appendNumber(((Button) v).getText().toString()));
        }
    }

    private void configureOperationButtons() {
        findViewById(R.id.button_add).setOnClickListener(v -> setOperation("+"));
        findViewById(R.id.button_subtract).setOnClickListener(v -> setOperation("-"));
        findViewById(R.id.button_multiply).setOnClickListener(v -> setOperation("*"));
        findViewById(R.id.button_divide).setOnClickListener(v -> setOperation("/"));
    }

    private void configureTrigonometricButtons() {
        findViewById(R.id.button_sin).setOnClickListener(v -> applyTrigonometricFunction("sin"));
        findViewById(R.id.button_cos).setOnClickListener(v -> applyTrigonometricFunction("cos"));
        findViewById(R.id.button_tan).setOnClickListener(v -> applyTrigonometricFunction("tan"));
    }

    private void appendNumber(String number) {
        String currentText = display.getText().toString();
        if (currentText.equals("0")) {
            display.setText(number);
        } else {
            display.setText(currentText + number);
        }
    }

    private void setOperation(String operation) {
        currentNumber = Double.parseDouble(display.getText().toString());
        pendingOperation = operation;
        display.setText("0");
    }

    private void performOperation() {
        double newNumber = Double.parseDouble(display.getText().toString());
        double result;
        switch (pendingOperation) {
            case "+":
                result = currentNumber + newNumber;
                break;
            case "-":
                result = currentNumber - newNumber;
                break;
            case "*":
                result = currentNumber * newNumber;
                break;
            case "/":
                result = currentNumber / newNumber;
                break;
            default:
                result = newNumber;
                break;
        }
        display.setText(String.valueOf(result));
        currentNumber = result;
    }

    private void applyTrigonometricFunction(String function) {
        double number = Double.parseDouble(display.getText().toString());
        double radians = useDegrees ? Math.toRadians(number) : number;
        double result;
        switch (function) {
            case "sin":
                result = Math.sin(radians);
                break;
            case "cos":
                result = Math.cos(radians);
                break;
            case "tan":
                result = Math.tan(radians);
                break;
            default:
                result = 0.0;
                break;
        }
        display.setText(String.valueOf(result));
    }

    private void clear() {
        display.setText("0");
        currentNumber = 0.0;
        pendingOperation = "";
    }
}
