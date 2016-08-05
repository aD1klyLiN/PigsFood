package otkorm.pigsfood;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Otkorm on 02.08.16.
 */
public class EditAdapter extends BaseAdapter implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    static class ViewHolder{
        TextView tvNumber;
        TextView tvValue;
        Button btnTerminal;
        Button btnZero;
        Button btnUp;
        Button btnDown;
        RadioButton rbSeven;
        RadioButton rbEight;
        TextView tvMassValue;
        TextView tvDensValue;
    }

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Bin> objects; //массив с номерами линий


    public EditAdapter(Context ctx, ArrayList<Bin> objects) {
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

    //public

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = lInflater.inflate(R.layout.ac_edit_list_item, parent, false);
            holder.tvNumber = (TextView) convertView.findViewById(R.id.tvNumber);
            holder.tvValue = (TextView) convertView.findViewById(R.id.tvValue);
            holder.btnTerminal = (Button) convertView.findViewById(R.id.btnTerminal);
            holder.btnZero = (Button) convertView.findViewById(R.id.btnZero);
            holder.btnUp = (Button) convertView.findViewById(R.id.btnUp);
            holder.btnDown = (Button) convertView.findViewById(R.id.btnDown);
            holder.rbSeven = (RadioButton) convertView.findViewById(R.id.rbSeven);
            holder.rbEight = (RadioButton) convertView.findViewById(R.id.rbEight);
            holder.tvMassValue = (TextView) convertView.findViewById(R.id.tvMassValue);
            holder.tvDensValue = (TextView) convertView.findViewById(R.id.tvDensValue);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();

        holder.tvNumber.setText(Integer.toString(((Bin) getItem(position)).getNumber()));
        holder.tvValue.setText(Integer.toString(((Bin) getItem(position)).getValueOld()));
        holder.tvMassValue.setText(Integer.toString(((Bin) getItem(position)).getMass()));
        holder.tvDensValue.setText(Integer.toString((int)((Bin)getItem(position)).getDensity()));

        switch (((Bin)getItem(position)).getFoodType()) {
            case '7':
                holder.rbSeven.setChecked(true);
                holder.rbEight.setChecked(false);
                break;
            case '8':
                holder.rbSeven.setChecked(false);
                holder.rbEight.setChecked(true);
                break;
            default:
                holder.rbSeven.setChecked(false);
                holder.rbEight.setChecked(false);
                break;
        }

        holder.btnTerminal.setOnClickListener(this);
        holder.btnTerminal.setTag(position);
        holder.btnZero.setOnClickListener(this);
        holder.btnZero.setTag(position);
        holder.btnUp.setOnClickListener(this);
        holder.btnUp.setTag(position);
        holder.btnDown.setOnClickListener(this);
        holder.btnDown.setTag(position);
        holder.rbSeven.setOnCheckedChangeListener(this);
        holder.rbSeven.setTag(position);
        holder.rbEight.setOnCheckedChangeListener(this);
        holder.rbEight.setTag(position);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        final int position = (Integer) v.getTag();
        switch (v.getId()) {
            case R.id.btnTerminal:
                if (position==6||position==10) {
                    final View form = lInflater.inflate(R.layout.dl_mass, null);
                    AlertDialog.Builder bld = new AlertDialog.Builder(ctx);
                    bld.setTitle("Введите показания терминала")
                            .setView(form)
                            .setCancelable(false)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    EditText edMass = (EditText) form.findViewById(R.id.edMass);
                                    if (edMass.getText().toString().equals("")) {
                                        ((Bin)getItem(position)).setMass(0);
                                        //Toast.makeText
                                        // (acInputOld.this, "Введите значение", Toast.LENGTH_LONG).show();

                                    } else {
                                        ((Bin)getItem(position))
                                                .setMass(Integer.parseInt(edMass.getText().toString()));
                                    }
                                }
                            });
                    AlertDialog ad = bld.create();
                    ad.show();
                    notifyDataSetChanged();
                }
                break;
            case R.id.btnZero:
                ((Bin)getItem(position)).setValueOld(520);
                ((Bin)getItem(position)).calculate();
                notifyDataSetChanged();
                break;
            case R.id.btnUp:
                if (((Bin) getItem(position)).getValueOld()<520) {
                    ((Bin)getItem(position)).setValueOld(((Bin) getItem(position)).getValueOld() + 10);
                    ((Bin)getItem(position)).calculate();
                    notifyDataSetChanged();
                }
                break;
            case R.id.btnDown:
                if (((Bin) getItem(position)).getValueOld()>0) {
                    ((Bin)getItem(position)).setValueOld(((Bin) getItem(position)).getValueOld() - 10);
                    ((Bin)getItem(position)).calculate();
                    notifyDataSetChanged();
                }
                break;
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int position = (int) buttonView.getTag();
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.rbSeven:
                    ((Bin) getItem(position)).setFoodType('7');
                    ((Bin)getItem(position)).calculate();
                    notifyDataSetChanged();
                    break;
                case R.id.rbEight:
                    ((Bin) getItem(position)).setFoodType('8');
                    ((Bin)getItem(position)).calculate();
                    notifyDataSetChanged();
                    break;
            }
        }
    }
}
