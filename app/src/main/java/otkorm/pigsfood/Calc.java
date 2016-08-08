package otkorm.pigsfood;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/*---------Операции с кормами-----------
1. Ввод измерений
2. Расчёт массы

 */
public class Calc implements Parcelable{

    public final int OLD = 0; //флаг расчёта по уровню центра
    public final int STD = 1; //флаг расчёта по уровням центра и боковой стенки

    private int number[] = new int[15]; //номер линии
    private int valueCenter[] = new int[15]; //измерение по центру
    private int valueWall[] = new int[15]; //измерение по стенке
    private int valueOld[] = new int[15]; //измерение одиночное
    private char foodType[] = new char[15]; //тип корма
    private double volume[] = new double[15]; //объём
    private double density[] = new double[15]; //плотность
    private int mass[] = new int[15]; //масса
    private int calcType; //тип расчёта
    private int defaultDens = 576; //предварительная плотность

    //конструктор
    public Calc() {
        for (int i=0; i<=14; i++){
            number[i] = i+1;
            valueCenter[i] = 0;
            valueWall[i] = 0;
            valueOld[i] = 0;
            foodType[i] = '7';
            volume[i] = 0;
            density[i] = 0;
            mass[i] = 0;
        }
        calcType = OLD;
    }

    /*public void setCalcType(int t) {
        calcType = t;
    }*/

    public int getCalcType() {
        return calcType;
    }

    public int getValueOld(int i) {
        return valueOld[i];
    }

    //запрос номера линии
    public int getNumber(int i) {
        return number[i];
    }

    //запрос типа корма
    /*public char getFoodType(int i) {
        return foodType[i];
    }*/

    //установка типа корма
    public void setFoodType(char[] foodType) {
        this.foodType = foodType;
    }

    //заполнение измерений для STD
    public void setValue(int i, int vCent, int vWall) {
        valueCenter[i] = vCent;
        valueWall[i] = vWall;
    }

    //заполнение измерений для OLD
    public void setValue(int i, int vOld) {
        valueOld[i] = vOld;
    }

    //ввод показаний весового терминала
    public void setMass(int i, int m) {
        mass[i] = m;
    }

    //расчёт. методику расчёта см. ПЗ
    public void calculate(int type) {

        //переменные для рассчёта
        double h, l, r, dens7 = 0, dens8 = 0;

        //расчёт объёма
        if (type== STD) {

            for (int i = 0; i <= 14; i++) {

                if (valueWall[i] >= 523) {
                    volume[i] = 0;
                } else if (valueWall[i] < 523 && valueWall[i] >= 288) {
                    r = (valueWall[i] * Math.cos(Math.asin(523 * Math.sin(Math.PI / 6) / valueWall[i]) + Math.PI / 6)) / 100;
                    h = (523 - valueWall[i] * Math.sin(Math.asin(523 * Math.sin(Math.PI / 6) / valueWall[i]) + Math.PI / 6)) / 100;
                    l = (523 - h - valueCenter[i]) / 100;
                    volume[i] = Math.PI * r * r * h / 3 + Math.PI * (l / Math.tan(Math.PI / 6)) * (l / Math.tan(Math.PI / 6)) * l / 3;
                } else if (valueWall[i] < 288 && valueWall[i] >= 178) {
                    r = 1.6;
                    h = (523 - valueWall[i] * Math.sin(Math.acos(160 / valueWall[i]))) / 100;
                    l = (523 - h - valueCenter[i]) / 100;
                    volume[i] = 7.587 + Math.PI * r * r * (h - 2.83) + Math.PI * (l / Math.tan(Math.PI / 6)) * (l / Math.tan(Math.PI / 6)) * l / 3;
                } else {
                    l = (77 - valueCenter[i]) / 100;
                    volume[i] = 20.664 + Math.PI * (l / Math.tan(Math.PI / 6)) * (l / Math.tan(Math.PI / 6)) * l / 3;
                }
            }
        } else {
            for (int i=0; i<=14; i++) {
                volume[i] = calcVolumeOld(valueOld[i]);
                /*if (valueOld[i]>=520) {
                    volume[i] = 0;
                } else if (valueOld[i]<520 && valueOld[i]>=240) {
                    h = (523 - valueOld[i])*0.01;
                    r = h * Math.tan(Math.PI/6);
                    volume[i] = (Math.PI/3) *r * r * h;
                } else if (valueOld[i]<240 && valueOld[i]>=77){
                    r = 1.6;
                    h = (523 - valueOld[i] - 283)*0.01;
                    volume[i] = 7.587 + Math.PI * r * r * h;
                } else {
                    h = (523 - valueOld[i] - 283 - 163)*0.01;
                    r = h * Math.tan(Math.PI/3);
                    volume[i] = 20.664 + (Math.PI/3) * r * r * h;
                }*/
            }
        }

        //определение плотности в бункерах с терминалами
        density[6] = mass[6]/volume[6];
        density[10] = mass[10]/volume[10];

        //расчёт плотности по каждому виду корма
        if (foodType[6]=='7') {
            if (foodType[10]=='7') {
                dens7 = (density[6] + density[10])/2;
                dens8 = dens7;
            } else if (foodType[10]=='8') {
                dens7 = density[6];
                dens8 = density[10];
            }
        } else if (foodType[6]=='8') {
            if (foodType[10]=='7') {
                dens7 = density[10];
                dens8 = density[6];
            } else if (foodType[10]=='8') {
                dens8 = (density[6] + density[10])/2;
                dens7 = dens8;
            }
        }

        if (Double.isNaN(dens7)||Double.isNaN(dens8)) {
            if (Double.isNaN(dens7)&&Double.isNaN(dens8)) {
                dens7 = defaultDens;
                dens8 = defaultDens;
            }else if (Double.isNaN(dens7)) {
                dens7 = dens8;
            } else {
                dens8 = dens7;
            }
        }

        //расчёт массы
        for (int i=0; i<=14; i++) {

            //задать плотность для каждого бункера
            if (foodType[i]=='7') {
                density[i] = dens7;
            } else if (foodType[i]=='8') {
                density[i] = dens8;
            }

            //исключить из расчёта массы бункера с терминалами
            if (i==6||i==10) {
                continue;
            }

            //итоговый расчёт массы
            mass[i] = (int)(volume[i]*density[i]);
        }

    }

    //запрос значения массы
    public int getMass (int i) {
        return mass[i];
    }

    /**
     * @param value - значение уровня по центру
     * @return - предварительная масса
     */
    public int calcCurrMass (int value) {
        int mass;
        mass = (int)(calcVolumeOld(value)*defaultDens);
        return mass;
    }

    /**
     * @param valueOld - значение уровня по центру
     * @return - рассчётный объём
     */
    public double calcVolumeOld (int valueOld) {
        double volume, h, r;
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
        return volume;
    }


    /** создаёт список из массива данных
     * @return - список бункеров Bin
     */
    public ArrayList<Bin> getBins() {
        ArrayList<Bin> bins = new ArrayList<>();
        for (int i=0;i<=14;i++) {
            Bin bin = new Bin(number[i], foodType[i], defaultDens);
            bins.add(bin);
        }
        return bins;
    }

    /** записывает данные из списка в массив
     * @param bins - список бункеров Bin
     */
    public void setBins(ArrayList<Bin> bins) {
        int i = 0;
        for (Bin bin:bins) {
            valueOld[i] = bin.getValueOld();
            if (bin.getNumber()==7 || bin.getNumber()==11) {
                mass[i] = bin.getMass();
            }
            i++;
        }
    }


//-------------------------ПАРСЕЛИЗАЦИЯ-------------------------------

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(number);
        dest.writeIntArray(valueWall);
        dest.writeIntArray(valueCenter);
        dest.writeIntArray(valueOld);
        dest.writeCharArray(foodType);
        dest.writeDoubleArray(volume);
        dest.writeDoubleArray(density);
        dest.writeIntArray(mass);
        dest.writeInt(calcType);
    }

    public static final Creator<Calc> CREATOR = new Creator<Calc>() {
        @Override
        public Calc createFromParcel(Parcel in) {
            return new Calc(in);
        }

        @Override
        public Calc[] newArray(int size) {
            return new Calc[size];
        }
    };

    protected Calc(Parcel in) {
        in.readIntArray(number);
        in.readIntArray(valueWall);
        in.readIntArray(valueCenter);
        in.readIntArray(valueOld);
        in.readCharArray(foodType);
        in.readDoubleArray(volume);
        in.readDoubleArray(density);
        in.readIntArray(mass);
        calcType = in.readInt();
    }

//---------------------КОНЕЦ ПАРСЕЛИЗАЦИИ--------------------------

}
