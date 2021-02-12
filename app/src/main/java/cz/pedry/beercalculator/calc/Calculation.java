package cz.pedry.beercalculator.calc;


import java.util.ArrayList;

public class Calculation {

    private ArrayList<Addition> additions;

    public Calculation(){
        additions = new ArrayList<>();
    }

    public void clearAdditions(){
        additions.clear();
    }

    public ArrayList<Addition> getAdditions() {
        return additions;
    }

    public void setAdditions(ArrayList<Addition> additions) {
        this.additions = additions;
    }

    public void addAddition(Addition addition){
        additions.add(addition);
    }

    public double getIBU(){
        double s = 0;
        for(int i=0;i<additions.size();i++){
            s += additions.get(i).getIBU();
        }
        return s;
    }

}