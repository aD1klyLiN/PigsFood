package otkorm.pigsfood;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class acInputOld extends AppCompatActivity implements View.OnClickListener{

    public Calc myCalc = new Calc();
    public int i;
    TextView lblBinNum, lblOldValue;
    public View form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_inputold);

        myCalc = getIntent().getParcelableExtra("Calc");
        i = getIntent().getIntExtra("i", 1);

        lblBinNum = (TextView) findViewById(R.id.lblBinNum);
        lblOldValue = (TextView) findViewById(R.id.lblOldValue);
        lblBinNum.setText("Линия №" + Integer.toString(i));

        if (i==7||i==11) {
            form = this.getLayoutInflater().inflate(R.layout.dl_mass, null);
            AlertDialog.Builder bld = new AlertDialog.Builder(acInputOld.this);
            bld.setTitle("Введите показания терминала")
                    .setView(form)
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText edMass = (EditText) form.findViewById(R.id.edMass);
                    if (edMass.getText().toString().equals("")) {
                        myCalc.setMass(i - 1, 0);
                        //Toast.makeText(acInputOld.this, "Введите значение", Toast.LENGTH_LONG).show();

                    } else {
                        myCalc.setMass(i - 1, Integer.parseInt(edMass.getText().toString()));
                    }
                }
            });
            AlertDialog ad = bld.create();
            ad.show();
        }
    }

    @Override
    public void onClick(View v) {
        int val, id = v.getId();
        String t_val;
        switch (id) {
            case R.id.btnOldUp:
                t_val = lblOldValue.getText().toString();
                val = Integer.parseInt(t_val.substring(0, t_val.length() - 3));
                val = val + 10;
                if (val>523)  val=520;
                t_val = Integer.toString(val) + "\nсм";
                lblOldValue.setText(t_val);
                break;
            case R.id.btnOldDn:
                t_val = lblOldValue.getText().toString();
                val = Integer.parseInt(t_val.substring(0, t_val.length() - 3));
                val = val - 10;
                if (val<10)  val=0;
                t_val = Integer.toString(val) + "\nсм";
                lblOldValue.setText(t_val);
                break;
            case R.id.btnDone:
                t_val = lblOldValue.getText().toString();
                val = Integer.parseInt(t_val.substring(0, t_val.length() - 3));
                myCalc.setValue(i-1, val);
                if (i<=14) {
                    i++;

                    form = this.getLayoutInflater().inflate(R.layout.dl_curr, null);
                    TextView tvMass = (TextView) form.findViewById(R.id.tvMass);
                    tvMass.setText(Integer.toString(myCalc.calcCurrMass(myCalc.getValueOld(i - 2))));
                    AlertDialog.Builder bld = new AlertDialog.Builder(acInputOld.this);
                    bld.setTitle("Примерная масса корма")
                            .setView(form)
                            //.setMessage(Integer.toString(myCalc.calcCurrMass(myCalc.getValueOld(i - 2))))
                            .setCancelable(false)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent stIn = new Intent(acInputOld.this, acInputOld.class);
                                    stIn.putExtra("Calc", myCalc);
                                    stIn.putExtra("i", i);
                                    startActivity(stIn);
                                    finish();
                                }
                            });
                    AlertDialog ad = bld.create();
                    ad.show();

                } else {
                    myCalc.calculate(myCalc.OLD);
                    Intent stSh = new Intent(this,acShow.class);
                    stSh.putExtra("Calc", myCalc);
                    startActivity(stSh);
                    finish();
                }
                break;
            case R.id.btnCancel:

                break;
        }
    }
}
