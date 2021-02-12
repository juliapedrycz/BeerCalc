package cz.pedry.beercalculator.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import cz.pedry.beercalculator.MainActivity;
import cz.pedry.beercalculator.R;

public class FragmentAlcohol extends Fragment {

    View view;
    private EditText startBLG, endBLG;
    private TextView alcohol, unitFinal, unitInitial;
    Context thiscontext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_alcohol, container, false);
        thiscontext = container.getContext();
        setLayout();
        startBLG.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        calculate();
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                }
        );
        endBLG.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        calculate();
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                }
        );
        return view;
    }

    private void calculate() {
        if(startBLG.length()!=0 && endBLG.length()!=0) {
            Double s = Double.parseDouble(String.valueOf(startBLG.getText()));
            Double e = Double.parseDouble(String.valueOf(endBLG.getText()));
            if(e>s) {
                Toast.makeText(thiscontext, getString(R.string.messageAlcohol), Toast.LENGTH_LONG).show();
            }
            else if(e>100 || s>100){
                Toast.makeText(thiscontext, getString(R.string.messageGravity), Toast.LENGTH_LONG).show();
            }
            else if((Double.parseDouble(String.valueOf(startBLG.getText()))<1) && !MainActivity.isUnitsEU()){
                Toast.makeText(thiscontext, getString(R.string.messageOG), Toast.LENGTH_LONG).show();
            }
            else if((Double.parseDouble(String.valueOf(endBLG.getText()))<1) && !MainActivity.isUnitsEU()){
                Toast.makeText(thiscontext, getString(R.string.messageFG), Toast.LENGTH_LONG).show();
            }
            else {
                if(!MainActivity.isUnitsEU()) {
                    s = (s - 1) * 250;
                    e = (e - 1) * 250;
                }
                Double alc = (s - e) / 1.938;
                alcohol.setText(
                        String.format("%.2f", alc)
                );
            }
        }
    }

    private void setLayout() {
        startBLG = (EditText) view.findViewById(R.id.startBLG);
        endBLG = (EditText) view.findViewById(R.id.endBLG);
        alcohol = (TextView) view.findViewById(R.id.alcohol);
        unitFinal = (TextView) view.findViewById(R.id.unitFinal);
        unitInitial = (TextView) view.findViewById(R.id.unitInitial);
        if(MainActivity.isUnitsEU()){
            unitFinal.setText(getString(R.string.finalGravity) +" BLG");
            unitInitial.setText(getString(R.string.initialGravity) +" BLG");
        }
        else {
            unitFinal.setText("FG");
            unitInitial.setText("OG");
        }
    }
}