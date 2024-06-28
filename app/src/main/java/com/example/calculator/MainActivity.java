package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView result_tv, solution_tv;
    MaterialButton btn_c, btn_open_bracket, btn_close_bracket;
    MaterialButton btn_divide, btn_multiply, btn_plus, btn_minus, btn_equals;
    MaterialButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    MaterialButton btn_ac, btn_dot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        result_tv = findViewById(R.id.result_tv);
        solution_tv = findViewById(R.id.solution_tv);
        assignId(btn_c, R.id.btn_c);
        assignId(btn_open_bracket, R.id.btn_open_bracket);
        assignId(btn_close_bracket, R.id.btn_close_bracket);
        assignId(btn_divide, R.id.btn_divide);
        assignId(btn_multiply, R.id.btn_multiply);
        assignId(btn_plus, R.id.btn_plus);
        assignId(btn_minus, R.id.btn_minus);
        assignId(btn_equals, R.id.btn_equals);
        assignId(btn1, R.id.btn_1);
        assignId(btn2, R.id.btn_2);
        assignId(btn3, R.id.btn_3);
        assignId(btn4, R.id.btn_4);
        assignId(btn5, R.id.btn_5);
        assignId(btn6, R.id.btn_6);
        assignId(btn7, R.id.btn_7);
        assignId(btn8, R.id.btn_8);
        assignId(btn9, R.id.btn_9);
        assignId(btn_ac, R.id.btn_ac);
        assignId(btn_dot, R.id.btn_dot);
    }

    void assignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MaterialButton btn = (MaterialButton) v;
        String btntext = btn.getText().toString();
        String dataToCalculate = solution_tv.getText().toString();

        if(btntext.equals("AC")) {
            solution_tv.setText("");
            result_tv.setText("0");
            return;
        }
        if(btntext.equals("=")) {
            solution_tv.setText(result_tv.getText());
            return;
        }
        if(btntext.equals("C")) {
            if(!dataToCalculate.isEmpty()) {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length()-1);
            }
        }
        else {
            dataToCalculate = dataToCalculate + btntext;
        }
        solution_tv.setText(dataToCalculate);
        String finalResult = getResult(dataToCalculate);
        if(!finalResult.equals("Err")) {
            result_tv.setText(finalResult);
        }
    }

    String getResult(String data) {
       try {
           Context ct = Context.enter();
           ct.setOptimizationLevel(-1);
           Scriptable scrab = ct.initSafeStandardObjects();
           String finalResult = ct.evaluateString(scrab, data, "Javascript", 1, null).toString();
           if(finalResult.endsWith(".0")) {
               finalResult = finalResult.replace(".0", "");
           }
           return finalResult;
       } catch (Exception e) {
           return "Err";
       }
    }
}
