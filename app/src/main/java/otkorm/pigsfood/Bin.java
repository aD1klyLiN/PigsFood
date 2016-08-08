package otkorm.pigsfood;

/**
 * Класс для описания бункера
 */
public class Bin {

    private int number; // номер линии
    private int valueOld; //уровень корма в бункере
    private char foodType; //тип корма
    private double volume; //расчётный объём
    private double density; //предварительная плотность
    private int mass; //предварительная масса

    private boolean isTerminal; //флаг наличия терминала
    //private boolean isEmpty;

    /**
     * @param number - номер линии
     * @param foodType - тип корма
     * @param density - предварительная плотность
     */
    public Bin(int number, char foodType, double density) {
        this.number = number;
        this.valueOld = 520;
        this.foodType = foodType;
        this.volume = 0;
        this.density = density;
        this.mass = 0;
        //this.isEmpty = true;
        this.isTerminal = number == 7 || number == 11;
    }

    public int getNumber() {
        return number;
    }

    /*public void setNumber(int number) {
        this.number = number;
    }*/

    public int getValueOld() {
        return valueOld;
    }

    public void setValueOld(int valueOld) {
        this.valueOld = valueOld;
    }

    public char getFoodType() {
        return foodType;
    }

    public void setFoodType(char foodType) {
        this.foodType = foodType;
    }

    /*public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }*/

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public double getDensity() {
        return density;
    }

    /*public void setDensity(double density) {
        this.density = density;
    }

    public boolean isTerminal() {
        return isTerminal;
    }

    public void setTerminal(boolean isTerminal) {
        this.isTerminal = isTerminal;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }*/

    /**
     * расчитывает объём и предварительную массу
     */
    public void calculate() {
        double h, r;
        if (valueOld>=520) {
            volume = 0;
        } else if (valueOld<520 && valueOld>=240) {
            h = (523 - valueOld)*0.01;
            r = h * Math.tan(Math.PI/6);
            volume = (Math.PI/3) *r * r * h;
        } else if (valueOld<240 && valueOld>=77){
            r = 1.6;
            h = (523 - valueOld - 283)*0.01;
            volume = 7.587 + Math.PI * r * r * h;
        } else {
            h = (523 - valueOld - 283 - 163)*0.01;
            r = h * Math.tan(Math.PI/3);
            volume = 20.664 + (Math.PI/3) * r * r * h;
        }
        if (!isTerminal) {
            mass = (int)(volume*density);
        }
    }

}
