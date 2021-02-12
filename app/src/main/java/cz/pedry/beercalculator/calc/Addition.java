package cz.pedry.beercalculator.calc;

public class Addition {

    private double weight;
    private double alphaAcid;
    private int boilTime;
    private double volume;
    private double gravity;
    private double IBU;

    public Addition(double weight, double alphaAcid, int boilTime, double volume,double gravity, boolean unitsEU ) {
        this.weight = weight;
        this.alphaAcid = alphaAcid;
        this.boilTime = boilTime;
        this.volume = volume;
        this.gravity = gravity;
        if(unitsEU)
            this.IBU = getIBUeu();
        else
            this.IBU = getIBUus();
    }

    public double getIBUeu(){
        double v1 = 1.65 * Math.pow(0.000125,(((gravity/250) +1)-1)) ;
        double v2 = (1 - Math.pow((Math.E),((-0.04)*boilTime))) / (4.15);
        double v3 = ((alphaAcid/100) * (weight/28.3) * 7490)/(volume/3.78);

        return (double) Math.round(v1*v2*v3*100)/100;
    }
    public double getIBUus(){
        double v1 = 1.65 * Math.pow(0.000125,(Math.abs(gravity-1)));
        double v2 = (1 - Math.pow((Math.E),((-0.04)*boilTime))) / (4.15);
        double v3 = ((alphaAcid/100) * (weight) * 7490)/(volume);

        return (double) Math.round(v1*v2*v3*100)/100;
    }

    public double getIBU() {
        return IBU;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getAlphaAcid() {
        return alphaAcid;
    }

    public void setAlphaAcid(double alphaAcid) {
        this.alphaAcid = alphaAcid;
    }

    public int getBoilTime() {
        return boilTime;
    }

    public void setBoilTime(int boilTime) {
        this.boilTime = boilTime;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }
}
