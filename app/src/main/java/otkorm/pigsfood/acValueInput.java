package otkorm.pigsfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class acValueInput extends AppCompatActivity implements View.OnClickListener{

    Calc myCalc = new Calc();
    int i;
    TextView lblBinNum, lblCentValue, lblWallValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_valueinput);

        myCalc = getIntent().getParcelableExtra("Calc");
        i = getIntent().getIntExtra("i", 1);

        lblBinNum = (TextView) findViewById(R.id.lblBinNum);
        lblCentValue = (TextView) findViewById(R.id.lblCentValue);
        lblWallValue = (TextView) findViewById(R.id.lblWallValue);
        lblBinNum.setText(R.string.lblLine + Integer.toString(i));
    }

    @Override
    public void onClick(View v) {
        int val, val1, val2, id = v.getId();
        String t_val, t_val1, t_val2;
        switch (id) {
            case R.id.btnCentUp:
                t_val = lblCentValue.getText().toString();
                val = Integer.parseInt(t_val.substring(0, t_val.length() - 3));
                val = val + 10;
                if (val>523)  val=520;
                t_val = Integer.toString(val) + "\nсм";
                lblCentValue.setText(t_val);
                break;
            case R.id.btnCentDn:
                t_val = lblCentValue.getText().toString();
                val = Integer.parseInt(t_val.substring(0, t_val.length() - 3));
                val = val - 10;
                if (val<10)  val=0;
                t_val = Integer.toString(val) + "\nсм";
                lblCentValue.setText(t_val);
                break;
            case R.id.btnWallUp:
                t_val = lblWallValue.getText().toString();
                val = Integer.parseInt(t_val.substring(0, t_val.length() - 3));
                val = val + 10;
                if (val<10)  val=0;
                t_val = Integer.toString(val) + "\nсм";
                lblWallValue.setText(t_val);
                break;
            case R.id.btnWallDn:
                t_val = lblWallValue.getText().toString();
                val = Integer.parseInt(t_val.substring(0, t_val.length() - 3));
                val = val - 10;
                if (val<10)  val=0;
                t_val = Integer.toString(val) + "\nсм";
                lblWallValue.setText(t_val);
                break;
            case R.id.btnDone:
                t_val1 = lblCentValue.getText().toString();
                val1 = Integer.parseInt(t_val1.substring(0, t_val1.length() - 3));
                t_val2 = lblWallValue.getText().toString();
                val2 = Integer.parseInt(t_val2.substring(0, t_val2.length() - 3));
                myCalc.setValue(i-1, val1, val2);
                if (i<=14) {
                    i++;
                    Intent stIn = new Intent(this,acValueInput.class);
                    stIn.putExtra("Calc", myCalc);
                    stIn.putExtra("i", i);
                    startActivity(stIn);
                    finish();
                } else {
                    myCalc.calculate(myCalc.std);
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
