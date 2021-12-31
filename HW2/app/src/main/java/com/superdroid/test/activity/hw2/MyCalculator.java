package com.superdroid.test.activity.hw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MyCalculator extends AppCompatActivity {

    TextView result_text;
    Button webview_execute_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result_text = (TextView) findViewById(R.id.calculator_result);
        webview_execute_button = (Button) findViewById(R.id.webview_exe_button);

        // Intent로 전달된 문자열 받기
        Intent intent = getIntent();

        if (intent.getAction() == Intent.ACTION_SEND) {
            String sharedText = intent.getStringExtra("number");
            String result = calculate(sharedText);
            sharedText += ("=" + result);
            result_text.setText(sharedText);
        }
    }

    public void numberClick(View v) {
        String currentText = result_text.getText().toString();
        switch( v.getId() ) {
            case R.id.button_number_0: {
                currentText += "0";
                break;
            }
            case R.id.button_number_1: {
                currentText += "1";
                break;
            }
            case R.id.button_number_2: {
                currentText += "2";
                break;
            }
            case R.id.button_number_3: {
                currentText += "3";
                break;
            }
            case R.id.button_number_4: {
                currentText += "4";
                break;
            }
            case R.id.button_number_5: {
                currentText += "5";
                break;
            }
            case R.id.button_number_6: {
                currentText += "6";
                break;
            }
            case R.id.button_number_7: {
                currentText += "7";
                break;
            }
            case R.id.button_number_8: {
                currentText += "8";
                break;
            }
            case R.id.button_number_9: {
                currentText += "9";
                break;
            }
        }
        result_text.setText(currentText);
    }

    public void symbolClick(View v) {
        String currentText = result_text.getText().toString();
        switch(v.getId()) {
            case R.id.button_plus: {
                currentText += "+";
                break;
            }
            case R.id.button_minus: {
                currentText += "-";
                break;
            }
            case R.id.button_multiply: {
                currentText += "x";
                break;
            }
            case R.id.button_equal: {
                String calc_result = calculate(currentText);
                currentText += "=";
                currentText += calc_result;
                break;
            }
            case R.id.button_slash: {
                currentText += "/";
                break;
            }
        }
        result_text.setText(currentText);
    }

    public String calculate (String str) {

        ArrayList<String> splits = new ArrayList<>();
        Integer mulitple_division_count = 0;
        Integer plus_minus_count = 0;

        String number = new String();

        // 숫자와 기호 분리
        for (int i = 0 ; i < str.length() ; i++) {
            Character c = str.charAt(i);

            if ( Character.isDigit(c) ) {
                number += c.toString();
                if (i == str.length() - 1) splits.add(number.toString());
            }
            else if( c.equals('+') || c.equals('-') ){
                plus_minus_count += 1;
                splits.add(number);
                splits.add(c.toString());
                number = "";
            }
            else if( c.equals('x') || c.equals('/') ){
                mulitple_division_count += 1;
                splits.add(number);
                splits.add(c.toString());
                number = "";
            }
        }

        // 곱셈, 나눗셈 먼저
        for ( int i = 0 ; i < mulitple_division_count ; i++) {
            for ( int j = 0 ; j < splits.size() ; j++ ) {
                String current = splits.get(j);

                if ( current.equals("x") ) {
                    Integer first = Integer.parseInt(splits.get(j-1));
                    Integer second = Integer.parseInt(splits.get(j+1));
                    Integer result = first * second;

                    splits.add(j, result.toString());
                    splits.remove(j-1);
                    splits.remove(j+1);

                }
                else if ( current.equals("/") ) {
                    Integer first = Integer.parseInt(splits.get(j - 1));
                    Integer second = Integer.parseInt(splits.get(j + 1));
                    Integer result = first / second;
                    splits.add(j, result.toString());
                    splits.remove(j - 1);
                    splits.remove(j + 1);
                }
            }
        }

        // 덧셈, 뺄셈
        for ( int i = 0 ; i < plus_minus_count ; i++) {
            for ( int j = 0 ; j < splits.size() ; j++ ) {
                String current = splits.get(j);
                if ( current.equals("+") ) {
                    Integer first = Integer.parseInt(splits.get(j-1));
                    Integer second = Integer.parseInt(splits.get(j+1));
                    Integer result = first + second;
                    splits.add(j, result.toString());
                    splits.remove(j-1);
                    splits.remove(j+1);
                }
                else if ( current.equals("-") ) {
                    Integer first = Integer.parseInt(splits.get(j-1));
                    Integer second = Integer.parseInt(splits.get(j+1));
                    Integer result = first - second;
                    splits.add(j, result.toString());
                    splits.remove(j-1);
                    splits.remove(j+1);
                }
            }
        }

        return splits.get(0);
    }

    public void startWebViewActivity(View v) {
        Intent intent = new Intent(this, MyWebBrowser.class);
        intent.putExtra("activityName", "MyCalculator");
        startActivity(intent);
    }

}