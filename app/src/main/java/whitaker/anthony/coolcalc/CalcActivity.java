package whitaker.anthony.coolcalc;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class CalcActivity extends Activity {

    private enum CalcNumber {
        ZERO(R.id.zeroButton, 0),
        ONE(R.id.oneButton, 1),
        TWO(R.id.twoButton, 2),
        THREE(R.id.threeButton, 3),
        FOUR(R.id.fourButton, 4),
        FIVE(R.id.fiveButton, 5),
        SIX(R.id.sixButton, 6),
        SEVEN(R.id.sevenButton, 7),
        EIGHT(R.id.eightButton, 8),
        NINE(R.id.nineButton, 9);

        public int id;
        public int value;

        CalcNumber(int id, int value) {
            this.id = id;
            this.value = value;
        }
    }

    private enum Operation {
        ADD(R.id.addButton),
        SUBTRACT(R.id.subtractButton),
        MULTIPLY(R.id.multiplyButton),
        DIVIDE(R.id.divideButton),
        EQUAL(R.id.equalButton);

        public int id;

        Operation(int id) {
            this.id = id;
        }

    }

    Operation activeOperation = Operation.EQUAL;
    String enteredNumber = "";
    String operand1 = "";
    String operand2 = "";

    TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        resultView = (TextView)findViewById(R.id.resultText);

        for(CalcNumber number : CalcNumber.values()) {
            setupNumberButton(number);
        }

        for(Operation operation: Operation.values()) {
            setupOperationButton(operation);
        }

        Button clearButton = (Button)findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearButtonPressed();
            }
        });
    }

    private void setupNumberButton(final CalcNumber number) {
        Button numButton = (Button)findViewById(number.id);
        Log.d("SETUP", "Button: " + number.name() + " | value: " + number.value);
        numButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberPressed(number.value);
            }
        });
    }

    private void setupOperationButton(final Operation operation) {
        ImageButton opButton = (ImageButton)findViewById(operation.id);
        opButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processOperation(operation);
            }
        });
    }

    private void numberPressed(int number) {
        enteredNumber += Integer.toString(number);
        resultView.setText(enteredNumber);
        Log.d("ACTION", "Button pressed: " + number);
    }

    private void processOperation(Operation operation) {
        if(enteredNumber.isEmpty() && operation != Operation.EQUAL) return;

        if(!enteredNumber.isEmpty()) {
            if (operand1.isEmpty()) {
                operand1 = enteredNumber;
            } else {
                operand2 = enteredNumber;
            }

            enteredNumber = "";
        }

        if(!operand1.isEmpty() && !operand2.isEmpty() && activeOperation != Operation.EQUAL) {
            int result = 0;
            switch (activeOperation) {
                case ADD:
                    result = safeParse(operand1) + safeParse(operand2);
                    break;
                case SUBTRACT:
                    result = safeParse(operand1) - safeParse(operand2);
                    break;
                case MULTIPLY:
                    result = safeParse(operand1) * safeParse(operand2);
                    break;
                case DIVIDE:
                    result = safeParse(operand1) / safeParse(operand2);
                    break;
            }

            operand1 = Integer.toString(result);
            resultView.setText(operand1);
        }

        if(operation != Operation.EQUAL) {
            activeOperation = operation;
        }
    }

    private void clearButtonPressed() {
        operand1 = "";
        operand2 = "";
        activeOperation = Operation.EQUAL;
        enteredNumber = "";
        resultView.setText("0");
    }

    private static int safeParse(String string) {
        int result;
        try {
            result = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            result = 0;
        }
        return result;
    }

}
