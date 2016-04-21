package otkorm.pigsfood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class acShow extends AppCompatActivity implements View.OnClickListener{

    Calc myCalc = new Calc();
    ListView lvMass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_show);

        if (getIntent().hasExtra("Calc")) {
            myCalc = getIntent().getParcelableExtra("Calc");
        } else {
            SharedPreferences sPref;
            sPref = getPreferences(MODE_PRIVATE);
            for (int i=0; i<=14; i++) {
                    /*0 - значение массы по умолчанию
                    * i - индекс номера бункера*/
                myCalc.setMass(i, sPref.getInt(Integer.toString(i), 0));

            }
        }

        ArrayList<Map<String, Object>> data = new ArrayList<>(15);
        Map<String, Object> m;
        for (int i=0; i<=14; i++) {
            m = new HashMap<>();
            m.put("l_num", "Линия №" + Integer.toString(myCalc.getNumber(i)) + ":");
            m.put("mass", Integer.toString(myCalc.getMass(i)) + " кг");
            data.add(m);
        }
        // массив имен атрибутов, из которых будут читаться данные
        String[] from = {"l_num", "mass"};
        // массив ID View-компонентов, в которые будут вставлять данные
        int[] to = {R.id.lblBinNum, R.id.lblMass};
        // создаем адаптер
        SimpleAdapter sAdp = new SimpleAdapter(this, data, R.layout.ac_show_list_item,
                from, to);
        // определяем список и присваиваем ему адаптер
        lvMass = (ListView) findViewById(R.id.lvMass);
        lvMass.setAdapter(sAdp);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                SharedPreferences sPref;
                sPref = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                for (int i=0; i<=14; i++) {
                    ed.putInt(Integer.toString(i), myCalc.getMass(i));
                }
                ed.apply();

                break;
            case R.id.btnCancel:
                Intent st = new Intent(this, acStart.class);
                startActivity(st);
                finish();
                break;
        }
    }
}
