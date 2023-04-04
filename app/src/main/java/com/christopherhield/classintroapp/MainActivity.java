package com.christopherhield.classintroapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.transition.Visibility;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RadioGroup radio;
    TextView tainput,twtinput,tppinput;
    EditText bttinput,nopinput;
    double totalres,bttinputt,res,perplp;
    TextView tvInput;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null)
            Toast.makeText(this, "savedInstanceState is NULL", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "savedInstanceState is NOT NULL", Toast.LENGTH_LONG).show();

        bttinput = findViewById(R.id.bttInput); // bill total with tax field
        twtinput = findViewById(R.id.TwtInput);
        tainput = findViewById(R.id.TaInput); // total with tip field
        nopinput = findViewById(R.id.NopInput); // number of people field
        tppinput = findViewById(R.id.TppInput); //total per person field
        tvInput=findViewById(R.id.tvippp);
       // total amount of tip field
        radio = findViewById(R.id.radioGroup);

        tvInput.setVisibility(View.INVISIBLE);
        bttinput.setVisibility(View.VISIBLE);

        tvInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvInput.setVisibility(View.INVISIBLE);
                bttinput.setVisibility(View.VISIBLE);
            }
        });
    } // Oncreat ends
    // for selecting radio button to select percentage
    public void radioClicked(View v) {
        try {
            String text = bttinput.getText().toString();
            //checking for empty string in bill total with tax field
            if (text.trim().isEmpty()) {
                perplp = 0;
                totalres =0;
                res = 0;
                bttinput.setText("");
                tainput.setText("");
                twtinput.setText("");
                radio.clearCheck();
                nopinput.setText("");
                tppinput.setText("");
                return;
            }
            else {
                    //Radio button selection
                    bttinputt = Double.parseDouble(bttinput.getText().toString());
                    tvInput.setText("$"+bttinput.getText().toString());
                    tvInput.setVisibility(View.VISIBLE);
                    bttinput.setVisibility(View.INVISIBLE);

                    if (v.getId() == R.id.Tradio) {

                        res = (0.12 * bttinputt);
                    } else if (v.getId() == R.id.Fradio) {

                        res = (0.15 * bttinputt);
                    } else if (v.getId() == R.id.Eradio) {

                        res = (0.18 * bttinputt);
                    } else if (v.getId() == R.id.Twradio) {

                        res = (0.20 * bttinputt);
                    }
                    double total=res;

                    tainput.setText(String.format("$%.2f",total )); // Round-up to nearest cent
                    totalres = (bttinputt + res);
                    twtinput.setText(String.format("$%.2f",totalres ));

              // Round-up to nearest cent
            }

        }
        catch (Exception e){
            Toast.makeText(this,"error: "+e,Toast.LENGTH_LONG).show();
        }
    } // RadioClicked method ends

    // to divid total amount with total people
    public void GO (View v){
        String text2 = nopinput.getText().toString();
        if (text2.trim().isEmpty()){
            tppinput.setText("");
            return;
        }

        else {
            Integer totalplp = Integer.parseInt(nopinput.getText().toString());
            if (totalplp == 0) {
               /* double total=Math.floor(totalplp);
                tppinput.setText("$" + total); */
                tppinput.setText("");
            }
            else {
                double total=totalplp;
                double totalress=totalres;
                perplp = totalress / total;
               // double value=perplp;
                tppinput.setText(String.format("$%.2f",perplp ));
            }
        }
    } //Go method ends

    // To Clear all data fields and uncheck radio buttons
    public void Clr (View v){
        tvInput.setText("");
        tvInput.setVisibility(View.INVISIBLE);
        bttinput.setVisibility(View.VISIBLE);
        perplp = 0;
        totalres =0;
        res = 0;
        bttinput.setText("");
        tainput.setText("");
        twtinput.setText("");
        nopinput.setText("");
        tppinput.setText("");
        radio.clearCheck();
    }// Clr Method ends

    //saving state when we rotate device
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("TotalPerPerson", tppinput.getText().toString());
        outState.putString("TotalAmount", tainput.getText().toString());
        outState.putString("TotalWithTip", twtinput.getText().toString());
        outState.putString("TvInput", tvInput.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        tppinput.setText(savedInstanceState.getString("TotalPerPerson"));
        tainput.setText(savedInstanceState.getString("TotalAmount"));
        twtinput.setText(savedInstanceState.getString("TotalWithTip"));
        tvInput.setText(savedInstanceState.getString("TvInput"));

    }
}
