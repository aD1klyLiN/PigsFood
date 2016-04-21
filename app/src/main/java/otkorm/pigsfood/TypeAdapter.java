package otkorm.pigsfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

/*------------Адаптер для списка по типу корма
 1. Создаёт список
 2. необходимо выставить тип по линиям
 */
public class TypeAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<String> objects; //массив с номерами линий
    char foodType[] = new char[15];

    //конструктор
    public TypeAdapter(Context ctx, ArrayList<String> objects) {
        this.ctx = ctx;
        this.objects = objects;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        /*View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.ac_foodtype_list_item, parent, false);
        }*/
        View view = lInflater.inflate(R.layout.ac_foodtype_list_item, parent, false);
        String num = "Линия №" + getItem(position);
        ((TextView) view.findViewById(R.id.lblBinNum)).setText(num);
        ((RadioButton) view.findViewById(R.id.rbSeven)).setText(R.string.lblSeven);
        ((RadioButton) view.findViewById(R.id.rbEight)).setText(R.string.lblEight);
        RadioButton rb7 = (RadioButton) view.findViewById(R.id.rbSeven);
        RadioButton rb8 = (RadioButton) view.findViewById(R.id.rbEight);
        switch (foodType[position]) {
            case '7':
                rb7.setChecked(true);
                rb8.setChecked(false);
                break;
            case '8':
                rb7.setChecked(false);
                rb8.setChecked(true);
                break;
            default:
                rb7.setChecked(false);
                rb8.setChecked(false);
                break;
        }
        rb7.setOnCheckedChangeListener(myCheckList);
        rb8.setOnCheckedChangeListener(myCheckList);
        rb7.setTag(position);
        rb8.setTag(position);
        return view;
    }

    OnCheckedChangeListener myCheckList = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                switch (buttonView.getId()) {
                    case R.id.rbSeven:
                        foodType[(int) getItemId((Integer) buttonView.getTag())] = '7';
                        break;
                    case R.id.rbEight:
                        foodType[(int) getItemId((Integer) buttonView.getTag())] = '8';
                        break;
                }
            }

        }
    };

    public boolean isChecked() {
        boolean ch = true;
        for (int i=0; i<=14; i++) {
            ch = foodType[i] == '7' || foodType[i] == '8';
        }
        return ch;
    }

    public char[] getFoodType() {
        return foodType;
    }
}
