package com.jav1001.vinaysingh.diceroll;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    public static int noRoll = 0;
    public static boolean text=false;
    public static final String diceVal = "diceVal";
    public static final String pastVals = "pastVals";
    public static final String sidesArrayStr = "sidesArrayStr";
    public static boolean rollTwo = false;
    public static ArrayList<String> listItems = new ArrayList<String>();
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedpreferences = getSharedPreferences("values", Context.MODE_PRIVATE);

        String val = sharedpreferences.getString(diceVal,"");
        String pastVal = sharedpreferences.getString(pastVals,"");
        String arrayStr = sharedpreferences.getString(sidesArrayStr,"2@4@6@8@10");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LottieAnimationView animationView = findViewById(R.id.animationView);
        LottieAnimationView animationViewBack = findViewById(R.id.aniback);
        animationViewBack.playAnimation();
        animationView.setRepeatCount(0);
        animationView.pauseAnimation();

        animationView.addAnimatorListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationEnd(Animator animation) {
                animationView.setFrame(0);
            }

            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        Switch switchSides = findViewById(R.id.switchSides);
        Switch switchRoll = findViewById(R.id.switch2roll);
        switchRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchRoll.isChecked()){
                    rollTwo=true;
                }
                else{
                    rollTwo=false;
                }
            }
        });
        EditText editTextSides = findViewById(R.id.editTextSides);
        switchSides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchSides.isChecked()){
                    text=true;
                }
                else {
                    text=false;
                }
            }
        });

        //Spinner
        String[] araySides = arrayStr.split("@");
        for (int i =0;i<araySides.length;i++){
            listItems.add(araySides[i]);
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinner_dice);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        TextView textPastValues = findViewById(R.id.textPastValues);

        textPastValues.setText(pastVal);
        //TextView
        TextView diceText = findViewById(R.id.rolltext);
        diceText.setText(val);
        //Button
        Button rollButton = findViewById(R.id.rollbutton);
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animationView.playAnimation();



                int _random = (int) ((Math.random() * Integer.parseInt(spinner.getSelectedItem().toString()) + 1));
                if (noRoll >= 5) {
                    noRoll = 0;
                    textPastValues.setText("");
                }
                noRoll += 1;

                if(rollTwo){
                    if(text){
                        if(!listItems.contains(""+editTextSides.getText().toString())){
                            listItems.add(0,editTextSides.getText().toString());
                            adapter.notifyDataSetChanged();
                            spinner.setAdapter(adapter);

                        }
                        String sideText = editTextSides.getText().toString();
                        if (sideText!="") {
                            try{
                                _random = (int) ((Math.random() * Integer.parseInt(sideText) + 1));
                                int _random2 = (int) ((Math.random() * Integer.parseInt(sideText) + 1));
                                diceText.setText(_random+", " + _random2);
                                textPastValues.setText(textPastValues.getText().toString()+_random+", "+_random2+", ");
                            }
                            catch (Exception e ){
                                System.out.println(e);
                                _random = 0;
                                editTextSides.setError("Enter Number");
                                editTextSides.requestFocus();
                            }
                        }
                        else {
                            _random = 0;
                            editTextSides.setError("Enter Number");
                            editTextSides.requestFocus();
                        }
                    }
                    else {

                        _random = (int) ((Math.random() * Integer.parseInt(spinner.getSelectedItem().toString()) + 1));
                        int _random2 = (int) ((Math.random() * Integer.parseInt(spinner.getSelectedItem().toString()) + 1));
                        diceText.setText(_random+", " + _random2);
                        textPastValues.setText(textPastValues.getText().toString()+_random+", "+_random2+", ");

                    }
                }
                else
                {
                    if(text){
                        if(!listItems.contains(""+editTextSides.getText().toString())){

                            listItems.add(0,editTextSides.getText().toString());
                            adapter.notifyDataSetChanged();
                            spinner.setAdapter(adapter);

                        }
                        String sideText = editTextSides.getText().toString();
                        if (sideText!="") {
                            try{
                                _random = (int) ((Math.random() * Integer.parseInt(sideText) + 1));
                                diceText.setText(_random+", " );
                                textPastValues.setText(textPastValues.getText().toString()+_random+", ");
                            }
                            catch (Exception e ){
                                System.out.println(e);
                                _random = 0;
                                editTextSides.setError("Enter Number");
                                editTextSides.requestFocus();
                            }
                        }
                        else {
                            _random = 0;
                            editTextSides.setError("Enter Number");
                            editTextSides.requestFocus();
                        }
                    }
                    else {
                        _random = (int) ((Math.random() * Integer.parseInt(spinner.getSelectedItem().toString()) + 1));
                        diceText.setText(""+_random);
                        textPastValues.setText(textPastValues.getText().toString()+_random+", ");
                    }


                }

            }
        });






    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onPause(){
        super.onPause();
        TextView diceText = findViewById(R.id.rolltext);
        TextView pastText = findViewById(R.id.textPastValues);
        sharedpreferences = getSharedPreferences("values", Context.MODE_PRIVATE);

        String arrayToStr = String.join("@", listItems);;
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(sidesArrayStr,arrayToStr);
        editor.putString(pastVals, (String) pastText.getText());
        editor.putString(diceVal, (String) diceText.getText());
        editor.commit();


    }


}