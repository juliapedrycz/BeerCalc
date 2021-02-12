package cz.pedry.beercalculator.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cz.pedry.beercalculator.R;
import cz.pedry.beercalculator.calc.Addition;


public class ColumnValueAdapter extends ArrayAdapter<Addition> {

    private LayoutInflater mInflater;
    private ArrayList<Addition> additions;
    private int mViewResourceId;

    public ColumnValueAdapter(Context context, int textViewResourceId, ArrayList<Addition> additions) {
        super(context, textViewResourceId, additions);
        this.additions = additions;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);
        Addition addition = additions.get(position);
        TextView weight = (TextView) convertView.findViewById(R.id.weight);
        TextView acid = (TextView) convertView.findViewById(R.id.acid);
        TextView time = (TextView) convertView.findViewById(R.id.time);
        TextView ibu = (TextView) convertView.findViewById(R.id.ibu);

        if (addition != null) {

            if (weight != null) {
                weight.setText(Double.toString(addition.getWeight()));
            }

            if (acid != null) {
                acid.setText(Double.toString(addition.getAlphaAcid()));
            }

            if (time != null) {
                time.setText(Double.toString(addition.getBoilTime()));
            }

            if (ibu != null) {
                ibu.setText(Double.toString(addition.getIBU()));
            }

        }

        return convertView;
    }
}