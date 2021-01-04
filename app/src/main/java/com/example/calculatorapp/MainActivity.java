package com.example.calculatorapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private EditText newNumber;
    private TextView textView;
    private Double firstNum= null;
    private String pendingOperation ="=";
    private static final String pendingOp ="Pending operation";
    private static final String oprand = "oprand1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn0 = findViewById(R.id.button0);
        Button btn1 = findViewById(R.id.button1);
        Button btn2 = findViewById(R.id.button2);
        Button btn3 = findViewById(R.id.button3);
        Button btn4 = findViewById(R.id.button4);
        Button btn5 = findViewById(R.id.button5);
        Button btn6 = findViewById(R.id.button6);
        Button btn7 = findViewById(R.id.button7);
        Button btn8 = findViewById(R.id.button8);
        Button btn9 = findViewById(R.id.button9);
        Button btnDot = findViewById(R.id.buttonDot);
        Button btnPlus = findViewById(R.id.buttonPlus);
        Button btnMultiply = findViewById(R.id.buttonMultiply);
        Button btnMinus = findViewById(R.id.ButtonMinus);
        Button btnDivide= findViewById(R.id.buttonDivide);
        Button btnEquals= findViewById(R.id.buttonEquals);
        Button btnNeg = findViewById(R.id.buttonNeg);
        Button btnClr = findViewById(R.id.buttonClear);
        Button btnCos = findViewById(R.id.buttonCos);
        Button btnSin = findViewById(R.id.buttonSin);
        editText = findViewById(R.id.editText);
        newNumber = findViewById(R.id.newNumber);
        textView = findViewById(R.id.operation);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String s =b.getText().toString();
                newNumber.append(s);
            }
        };
      btn0.setOnClickListener(listener);
      btn1.setOnClickListener(listener);
      btn2.setOnClickListener(listener);
      btn3.setOnClickListener(listener);
      btn4.setOnClickListener(listener);
      btn5.setOnClickListener(listener);
      btn6.setOnClickListener(listener);
      btn7.setOnClickListener(listener);
      btn8.setOnClickListener(listener);
      btn9.setOnClickListener(listener);
      btnDot.setOnClickListener(listener);

      View.OnClickListener operations = new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Button b = (Button) v;
              String op = b.getText().toString();
              String val = newNumber.getText().toString();
              try {
                  double value = Double.valueOf(val);
                  Log.i(" operation "," "+op);
                  performOperations(value,op);
              }catch (NumberFormatException e){
                newNumber.setText("");
              }

              pendingOperation=op;
              textView.setText(pendingOperation);
          }
      };
      btnDivide.setOnClickListener(operations);
      btnPlus.setOnClickListener(operations);
      btnMultiply.setOnClickListener(operations);
      btnMinus.setOnClickListener(operations);
      btnEquals.setOnClickListener(operations);
      btnSin.setOnClickListener(operations);
      btnCos.setOnClickListener(operations);
      View.OnClickListener negButton = new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            //Button b = (Button)v;
           String s= newNumber.getText().toString();
           if(s.length()==0){
               newNumber.setText("-");
           }else{
               try{
                   Double num =Double.valueOf(s)*-1.0;
                   newNumber.setText(num.toString());
               }catch (NumberFormatException e) {
                   Toast.makeText(MainActivity.this, "Can't negate", Toast.LENGTH_SHORT).show();
                   newNumber.setText("");

               }
           }
          }
      };
      btnNeg.setOnClickListener(negButton);
      btnClr.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              firstNum=null;
              pendingOperation="=";
              textView.setText(pendingOperation);
              newNumber.setText("");
              editText.setText("");
          }
      });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(pendingOp,pendingOperation);
        if(firstNum!=null){
            outState.putDouble(oprand,firstNum);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation=savedInstanceState.getString(pendingOp);
        firstNum=savedInstanceState.getDouble(oprand);
        textView.setText(pendingOperation);
    }

    private void performOperations(double val, String op){
        //Toast.makeText(this, "here1", Toast.LENGTH_SHORT).show();
          if(null==firstNum){
              if(op.equals("COS")){
                  firstNum=Math.cos(val);
                  Toast.makeText(this, "inCOS "+firstNum, Toast.LENGTH_SHORT).show();
              }else if(op.equals("SIN")){
                  firstNum=Math.sin(val);
                  Toast.makeText(this, "inSIN "+firstNum, Toast.LENGTH_SHORT).show();
              }else {
                  firstNum= val;
              }
          }
          else {
              //Toast.makeText(this, "Val: "+ val, Toast.LENGTH_SHORT).show();
              if (pendingOperation.equals("=")) {
                  pendingOperation = op;
              }
              Log.i("pending op",""+pendingOperation);
              switch (pendingOperation) {

                  case "=":
                      firstNum = val;
                      break;
                  case "/":
                      firstNum /= val;
                      break;
                  case "*":
                    firstNum*=val;
                    break;
                  case "+":
                      firstNum += val;
                      break;
                  case "-":
                      firstNum -= val;
                      break;
              }
              if(op.equals("SIN")){
                  firstNum = Math.sin(val);
              }
              else if(op.equals("COS")){
                  firstNum=Math.cos(val);
              }

          }
        editText.setText(firstNum.toString());
        newNumber.setText("");
      }

    }

