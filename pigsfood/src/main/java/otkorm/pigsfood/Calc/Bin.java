package otkorm.pigsfood.Calc;

import java.util.ArrayList;

/**
 * Created by Otkorm on 18.07.16.
 */
public class Bin {

    public static final int TYPE_SEVEN = 7;
    public static final int TYPE_EIGHT = 8;

    public int number;
    public int value;
    public double volume;
    public int density;
    public int mass;
    public boolean isTerminal;
    public int type;

    public Bin(int number, boolean isTerminal) {
        this.number = number;
        this.isTerminal = isTerminal;
    }

    public void calcVolume() {
        double h, r;
        if (value>=520) {
            volume = 0;
        } else if (value<520 && value>=240) {
            h = (523 - value)*0.01;
            r = h * Math.tan(Math.PI/6);
            volume = (Math.PI/3) *r * r * h;
        } else if (value<240 && value>=77){
            r = 1.6;
            h = (523 - value - 283)*0.01;
            volume = 7.587 + Math.PI * r * r * h;
        } else {
            h = (523 - value - 283 - 163)*0.01;
            r = h * Math.tan(Math.PI/3);
            volume = 20.664 + (Math.PI/3) * r * r * h;
        }
    }

    public void calcMass() {
        mass = (int)(volume*density);
    }

    public static void Calculator(ArrayList<Bin> bins) {

    }
}
