package cz.pedry.beercalculator.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import cz.pedry.beercalculator.MainActivity;
import cz.pedry.beercalculator.R;
import cz.pedry.beercalculator.adapters.ColumnValueAdapter;
import cz.pedry.beercalculator.calc.Addition;
import cz.pedry.beercalculator.calc.Calculation;

public class FragmentIBU extends Fragment {

    private EditText volume, gravity;
    private Button calculate, btnNew;
    private ImageButton reset;
    private static Calculation calculation;
    private ListView listView;
    TextView time, acid, weight, totalIBU, unitVolume, unitGravity, unitWeight;
    View view;
    Context thiscontext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_ibu, container, false);
         thiscontext = container.getContext();
         setLayout();
         calculation = new Calculation();

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateIBU();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearScreen();

            }
        });
        printList();

         return view;
    }

    public void calculateIBU(){
        if(volume.length()!=0 && gravity.length()!=0 && weight.length()!=0 && acid.length()!=0 && time.length()!=0) {
            if(Double.parseDouble(String.valueOf(acid.getText()))>100){
                Toast.makeText(thiscontext, getString(R.string.messageAcid), Toast.LENGTH_LONG).show();
            }
            else if(Double.parseDouble(String.valueOf(gravity.getText()))<1 && !MainActivity.isUnitsEU()){
                Toast.makeText(thiscontext, getString(R.string.messageOG), Toast.LENGTH_LONG).show();
            }
            else {
                calculation.addAddition(new Addition(Double.parseDouble(String.valueOf(weight.getText())), Double.parseDouble(String.valueOf(acid.getText())), Integer.parseInt(String.valueOf(time.getText())), Double.parseDouble(String.valueOf(volume.getText())), Double.parseDouble(String.valueOf(gravity.getText())), MainActivity.isUnitsEU()));
                volume.setEnabled(false);
                gravity.setEnabled(false);
                weight.setText("");
                acid.setText("");
                time.setText("");
                totalIBU.setText("IBU = " + String.format("%.2f", calculation.getIBU()));
                printList();
            }
        }
    }

    public void clearScreen(){
        volume.setText("");
        gravity.setText("");
        weight.setText("");
        acid.setText("");
        time.setText("");
        totalIBU.setText("");
        volume.setEnabled(true);
        gravity.setEnabled(true);
        calculation.clearAdditions();
        printList();
    }

    public void printList(){
        ColumnValueAdapter adapter =  new ColumnValueAdapter(thiscontext,R.layout.addition_values, calculation.getAdditions());
        listView.setAdapter(adapter);
    }

    private void setLayout() {
        volume = (EditText) view.findViewById(R.id.volume);
        gravity = (EditText) view.findViewById(R.id.gravity);
        reset = (ImageButton) view.findViewById(R.id.btnReset);
        listView = (ListView) view.findViewById(R.id.listView);
        btnNew = (Button) view.findViewById(R.id.btnNew);
        weight = (TextView) view.findViewById(R.id.weight);
        acid = (TextView) view.findViewById(R.id.acid);
        time = (TextView) view.findViewById(R.id.time);
        totalIBU = (TextView) view.findViewById(R.id.totalIBU);
        unitVolume = (TextView) view.findViewById(R.id.unitVolume);
        unitGravity = (TextView) view.findViewById(R.id.unitGravity);
        unitWeight = (TextView) view.findViewById(R.id.unitWeight);
        if(MainActivity.isUnitsEU()){
            unitVolume.setText("l");
            unitGravity.setText("BLG");
            unitWeight.setText("g");
        }
        else {
            unitVolume.setText("Gal");
            unitGravity.setText("OG");
            unitWeight.setText("oz");
        }
    }
}